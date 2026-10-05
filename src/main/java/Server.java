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

    public static void main(String[] args) throws Exception {
        monHoc = M.readValue(F_MON, TableMonHoc.class);
        sinhVien = M.readValue(F_SV, TableSinhVien.class);
        HttpServer s = HttpServer.create(new InetSocketAddress(8080), 0);
        s.createContext("/", Server::handle); // executor mặc định 1 luồng -> các thao tác chạy tuần tự
        s.start();
        System.out.println("Server chay tai http://localhost:8080");
    }

    static void handle(HttpExchange ex) {
        try {
            route(ex);
        } catch (Exception e) {
            try { send(ex, 500, err("Loi may chu: " + e)); } catch (IOException ignored) {}
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

        if (path.equals("/api/state")) { send(ex, 200, state(sv)); return; }

        MonHoc mh = monHoc.get(str(b.get("maHocPhan")));
        if (mh == null) { send(ex, 404, err("Khong tim thay mon hoc")); return; }

        boolean ok;
        String loi = null;
        switch (path) {
            case "/api/dang-ki": {
                LopHoc l = mh.get(str(b.get("maLopHoc")));
                if (l == null) { send(ex, 404, err("Khong tim thay lop")); return; }
                loi = sv.kiemTraDangKi(mh, l); // cần public (xem hướng dẫn)
                ok = loi == null && sv.dangKiLop(mh, l.getmaLopHoc());
                break;
            }
            case "/api/huy":
                ok = sv.huyLop(mh, str(b.get("maLopHoc")));
                break;
            case "/api/doi":
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
        save();
        send(ex, 200, state(sv));
    }

    static void login(HttpExchange ex, Map<String, Object> b) throws IOException {
        SinhVien sv = null;
        try { sv = sinhVien.get(Integer.parseInt(str(b.get("mssv")).trim())); }
        catch (NumberFormatException ignored) {}
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