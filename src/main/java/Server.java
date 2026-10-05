import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.*;

/** Chạy class này (thay vì App) rồi mở http://localhost:8080 */
public class Server {
    static final int TIN_CHI_TOI_DA = 28; // khớp SinhVien.TIN_CHI_TOI_DA
    static final ObjectMapper M = new ObjectMapper();
    static final File F_MON = new File("src/main/resources/DuLieuMonHoc.json");
    static final File F_SV = new File("src/main/resources/DuLieuSinhVien.json");
    static final File PAGE = new File("src/main/resources/dang-ky-hoc-phan.html");
    static final Map<String, Integer> tokens = new HashMap<>(); // token -> mssv
    static TableMonHoc monHoc;
    static TableSinhVien sinhVien;

    // ===== ĐO THỜI GIAN & RAM =====
    static long ramDung() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory(); // byte
    }

    static String mb(long bytes) {
        return String.format("%.2fMB", bytes / 1024.0 / 1024.0);
    }

    static String ms(long nanoStart) {
        return String.format("%.2fms", (System.nanoTime() - nanoStart) / 1_000_000.0);
    }

    /** Đổi đường dẫn API thành tên thao tác tiếng Việt không dấu. */
    static String tenThaoTac(String path) {
        switch (path) {
            case "/api/login":   return "dang nhap";
            case "/api/state":   return "tai du lieu mon hoc";
            case "/api/dang-ki": return "dang ki mon";
            case "/api/huy":     return "huy mon";
            case "/api/doi":     return "doi lop";
            default:             return "thao tac " + path;
        }
    }

    static String ketQua(int code) {
        switch (code) {
            case 200: return "thanh cong";
            case 401: return "that bai (chua dang nhap / sai mat khau)";
            case 404: return "that bai (khong tim thay)";
            case 409: return "that bai (khong du dieu kien)";
            default:  return "loi may chu (ma " + code + ")";
        }
    }

    public static void main(String[] args) throws Exception {
        System.gc(); // dọn rác trước để số RAM ổn định hơn
        long ramTruoc = ramDung();
        long t0 = System.nanoTime();

        monHoc = M.readValue(F_MON, TableMonHoc.class);
        sinhVien = M.readValue(F_SV, TableSinhVien.class);
        HttpServer s = HttpServer.create(new InetSocketAddress(8080), 0);
        s.createContext("/", Server::handle); // executor mặc định 1 luồng -> các thao tác chạy tuần tự
        s.start();

        long ramSau = ramDung();
        System.out.println("Server chay tai http://localhost:8080");
        System.out.println("khoi tao server mat: " + ms(t0));
        System.out.println("RAM khoi tao server: truoc " + mb(ramTruoc)
                + " | sau " + mb(ramSau)
                + " | tang them " + mb(ramSau - ramTruoc));
        System.out.println("----------------------------------------------");
    }

    static void handle(HttpExchange ex) {
        String path = ex.getRequestURI().getPath();
        boolean laApi = path.startsWith("/api/");
        long ramTruoc = ramDung();
        long t0 = System.nanoTime();
        try {
            route(ex);
        } catch (Exception e) {
            try { send(ex, 500, err("Loi may chu: " + e)); } catch (IOException ignored) {}
        } finally {
            if (laApi) { // chỉ đo các thao tác API, bỏ qua việc tải trang HTML
                long ramSau = ramDung();
                Object chiTiet = ex.getAttribute("chiTiet");
                Object tgSave = ex.getAttribute("tgSave");
                StringBuilder sb = new StringBuilder();
                sb.append(tenThaoTac(path)).append(" mat: ").append(ms(t0));
                if (tgSave != null) sb.append(" (trong do ghi file: ").append(tgSave).append(")");
                sb.append("\n    RAM: truoc ").append(mb(ramTruoc))
                  .append(" | sau ").append(mb(ramSau))
                  .append(" | chenh lech ").append(mb(ramSau - ramTruoc));
                sb.append("\n    ket qua: ").append(ketQua(ex.getResponseCode()));
                if (chiTiet != null) sb.append(" | ").append(chiTiet);
                System.out.println(sb);
                System.out.println("----------------------------------------------");
            }
        }
    }

    static void route(HttpExchange ex) throws Exception {
        String path = ex.getRequestURI().getPath();
        if (!path.startsWith("/api/")) { page(ex); return; }

        Map<String, Object> b = new HashMap<>();
        byte[] raw = readAll(ex.getRequestBody());
        if (raw.length > 0) b = M.readValue(raw, new TypeReference<Map<String, Object>>() {});

        if (path.equals("/api/login")) { login(ex, b); return; }

        String auth = ex.getRequestHeaders().getFirst("Authorization");
        Integer id = auth == null ? null : tokens.get(auth.replace("Bearer ", ""));
        SinhVien sv = id == null ? null : sinhVien.get(id);
        if (sv == null) { send(ex, 401, err("Chua dang nhap")); return; }
        ex.setAttribute("chiTiet", "sinh vien " + sv.getmssv());

        if (path.equals("/api/state")) { send(ex, 200, state(sv)); return; }

        MonHoc mh = monHoc.get(str(b.get("maHocPhan")));
        if (mh == null) { send(ex, 404, err("Khong tim thay mon hoc")); return; }
        ex.setAttribute("chiTiet", "sinh vien " + sv.getmssv() + ", mon " + mh.getmaHocPhan());

        boolean ok;
        String loi = null;
        switch (path) {
            case "/api/dang-ki": {
                LopHoc l = mh.get(str(b.get("maLopHoc")));
                if (l == null) { send(ex, 404, err("Khong tim thay lop")); return; }
                ex.setAttribute("chiTiet", "sinh vien " + sv.getmssv() + ", mon " + mh.getmaHocPhan()
                        + ", lop " + l.getmaLopHoc());
                loi = sv.kiemTraDangKi(mh, l);
                ok = loi == null && sv.dangKiLop(mh, l.getmaLopHoc());
                break;
            }
            case "/api/huy":
                ex.setAttribute("chiTiet", "sinh vien " + sv.getmssv() + ", mon " + mh.getmaHocPhan()
                        + ", lop " + str(b.get("maLopHoc")));
                ok = sv.huyLop(mh, str(b.get("maLopHoc")));
                break;
            case "/api/doi":
                ex.setAttribute("chiTiet", "sinh vien " + sv.getmssv() + ", mon " + mh.getmaHocPhan()
                        + ", lop " + str(b.get("maLopCu")) + " -> " + str(b.get("maLopMoi")));
                ok = sv.doiLop(mh, str(b.get("maLopCu")), str(b.get("maLopMoi")));
                break;
            default:
                send(ex, 404, err("Khong co API nay"));
                return;
        }
        if (!ok) {
            send(ex, 409, err(loi != null ? loi
                    : "Khong thuc hien duoc (lop khong hop le, da day hoac bi trung lich)."));
            return;
        }
        long ts = System.nanoTime();
        save();
        ex.setAttribute("tgSave", ms(ts));
        send(ex, 200, state(sv));
    }

    static void login(HttpExchange ex, Map<String, Object> b) throws IOException {
        SinhVien sv = null;
        try { sv = sinhVien.get(Integer.parseInt(str(b.get("mssv")).trim())); }
        catch (NumberFormatException ignored) {}
        ex.setAttribute("chiTiet", "mssv nhap vao: " + str(b.get("mssv")).trim());
        if (sv == null || !sv.getMatKhau().equals(str(b.get("matKhau")))) {
            send(ex, 401, err("Sai ma so sinh vien hoac mat khau"));
            return;
        }
        String t = UUID.randomUUID().toString();
        tokens.put(t, sv.getmssv());
        send(ex, 200, map("token", t));
    }

    /** Toàn bộ dữ liệu frontend cần: thông tin SV, TKB, môn + lớp kèm lý do không đăng ký được. */
    static Map<String, Object> state(SinhVien sv) {
        Map<String, Object> s = new LinkedHashMap<>();
        s.put("sv", map("ten", sv.getten(), "mssv", sv.getmssv(),
                "soTinDangKi", sv.getsoTinDangKi(), "toiDa", TIN_CHI_TOI_DA,
                "thoiKhoaBieu", sv.getthoiKhoaBieu()));
        List<Object> ds = new ArrayList<>();
        for (MonHoc mh : monHoc.getDanhSachMonHoc()) {
            List<Object> lops = new ArrayList<>();
            for (LopHoc l : mh.getcacLopHoc()) {
                boolean dk = l.tonTai(sv.getmssv());
                Map<String, Object> o = new LinkedHashMap<>();
                o.put("maLopHoc", l.getmaLopHoc());
                o.put("tenGiaoVien", l.gettenGiaoVien());
                o.put("siSoToiDa", l.getsiSoToiDa());
                o.put("soLuongDaDangKi", l.getsoLuongDaDangKi());
                o.put("thoiGianHoc", l.getthoiGianHoc());
                o.put("daDangKy", dk);
                o.put("loi", dk ? null : sv.kiemTraDangKi(mh, l));
                lops.add(o);
            }
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("maHocPhan", mh.getmaHocPhan());
            m.put("tenMonHoc", mh.gettenMonHoc());
            m.put("soTinChi", mh.getsoTinChi());
            m.put("monHocTruoc", mh.getmonHocTruoc());
            m.put("monTienQuyet", mh.getmonTienQuyet());
            m.put("cacLopHoc", lops);
            ds.add(m);
        }
        s.put("monHoc", ds);
        return s;
    }

    static void save() throws IOException {
        DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
        pp.indentArraysWith(new DefaultIndenter("  ", System.lineSeparator()));
        M.writer(pp).writeValue(F_MON, monHoc);
        M.writer(pp).writeValue(F_SV, sinhVien);
    }

    static String str(Object o) { return o == null ? "" : o.toString(); }
    static Map<String, Object> err(String m) { return map("message", m); }

    // thay cho Map.of (Java 9+)
    static Map<String, Object> map(Object... kv) {
        Map<String, Object> m = new LinkedHashMap<>();
        for (int i = 0; i < kv.length; i += 2) m.put((String) kv[i], kv[i + 1]);
        return m;
    }

    // thay cho InputStream.readAllBytes (Java 9+)
    static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int n;
        while ((n = in.read(buf)) != -1) out.write(buf, 0, n);
        return out.toByteArray();
    }

    static void page(HttpExchange ex) throws IOException {
        byte[] b = Files.readAllBytes(PAGE.toPath());
        ex.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        ex.sendResponseHeaders(200, b.length);
        ex.getResponseBody().write(b);
        ex.close();
    }

    static void send(HttpExchange ex, int code, Object o) throws IOException {
        byte[] b = M.writeValueAsBytes(o);
        ex.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        ex.sendResponseHeaders(code, b.length);
        ex.getResponseBody().write(b);
        ex.close();
    }
}