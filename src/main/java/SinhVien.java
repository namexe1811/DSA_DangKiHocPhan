import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SinhVien {

    private static final int TIN_CHI_TOI_DA = 28;

    private String ten;
    private int mssv;
    private String[][] thoiKhoaBieu;
    private TableMonDaDangKi monDaDangKi;   // lưu MÃ HỌC PHẦN của các môn đã đăng kí
    private int soTinDangKi;
    private String[] monDaTruot;
    private TableMonTichLuy monTichLuy;    // lưu MÃ HỌC PHẦN các môn đã học và pass
    private String matKhau;
//===============================================CONSTRUCTOR========================================================
    @JsonCreator 
    public SinhVien(@JsonProperty("ten") String ten,
                @JsonProperty("mssv") int mssv,
                @JsonProperty("thoiKhoaBieu") String[][] thoiKhoaBieu,
                @JsonProperty("monDaDangKi") String[] monDaDangKi,
                @JsonProperty("soTinDangKi") int soTinDangKi,
                @JsonProperty("matKhau") String matKhau,
                @JsonProperty("monDaTruot") String[] monDaTruot,
                @JsonProperty("monTichLuy") String[] monTichLuy) {
        this.ten = ten;
        this.mssv = mssv;
        this.matKhau = matKhau;
        this.thoiKhoaBieu = thoiKhoaBieu;
        this.monDaDangKi = new TableMonDaDangKi(monDaDangKi);
        this.monDaTruot = monDaTruot;
        this.monTichLuy = new TableMonTichLuy(monTichLuy);
        this.soTinDangKi = soTinDangKi;
    }
//===============================================HÀM==================================================================




    // ================== HÀM PHỤ (private) ==================

    // tìm lớp trong môn theo mã lớp (bỏ qua ô null của mảng)
    // private LopHoc timLop(MonHoc mon, String maLopHoc) {
    //     LopHoc[] ds = mon.getCacLopHoc();
    //     if (ds == null) return null;
    //     for (LopHoc lop : ds) {
    //         if (lop != null && lop.getmaLopHoc().equals(maLopHoc)) return lop;
    //     }
    //     return null;
    // }

    private boolean trong(String s) {
        return  s.isEmpty();
    }

    // kiem tra xem co mondatruot nay trong mondatruot hay khong .
    private boolean tonTaiMonDaTruot(String maMonHoc) {
        for (int i = 0; i < monDaTruot.length; i++) {
            if (monDaTruot[i].equals(maMonHoc)) return true;
        }
        return false;
    }

    // lớp mới có trùng giờ không: chỉ cần thấy 1 ô trong thời khoá biểu đã có lớp là trùng
    // quy ước: thu = số hàng (đợt 2 bắt đầu từ 7), tiết 1..12 -> cột 0..11
    private boolean trungLichHoc(LopHoc lop) {
        ThoiGian[] dsTg = lop.getthoiGianHoc();
        for (ThoiGian tg : dsTg) {
            for (int t = tg.gettietDau(); t <= tg.gettietCuoi(); t++) {
                if (!trong(thoiKhoaBieu[tg.getthu()][t])) return true;
            }
        }
        return false;
    }

    private void xepLichCuaLop(LopHoc lop) {
        ThoiGian[] dsTg = lop.getthoiGianHoc();
        for (ThoiGian tg : dsTg) {
            for (int t = tg.gettietDau(); t <= tg.gettietCuoi(); t++) {
                thoiKhoaBieu[tg.getthu()][t] = lop.getmaLopHoc();
            }
        }
    }

    private void xoaLichCuaLop(LopHoc lop) {
        ThoiGian[] dsTg = lop.getthoiGianHoc();
        for (ThoiGian tg : dsTg) {
            for (int t = tg.gettietDau(); t <= tg.gettietCuoi(); t++) {
                thoiKhoaBieu[tg.getthu()][t] = "";
            }
        }
    }

    // Kiểm tra mọi điều kiện đăng kí. Trả về null nếu hợp lệ, ngược lại trả về lý do.
    public String kiemTraDangKi(MonHoc mon, LopHoc lop) {
        // 1. đã đăng kí môn này chưa
        if (monDaDangKi.tonTai(mon.getmaHocPhan())) {
            return mssv + " Da dang ky mon " + mon.getmaHocPhan() + " roi.";
        }
        // 2. môn tiên quyết: phải học và pass (có trong monTichLuy)
        String[] tienQuyet = mon.getmonTienQuyet();
        if (tienQuyet != null) {
            for (String ma : tienQuyet) {
                if (!monTichLuy.tonTai(ma)) {
                    return mssv + " Chua dat mon tien quyet: " + ma;
                }
            }
        }
        // 3. môn học trước: chỉ cần đã học (tích luỹ hoặc đã trượt)
        String[] hocTruoc = mon.getmonHocTruoc();
        if (hocTruoc != null) {
            for (String ma : hocTruoc) {
                if (!monTichLuy.tonTai(ma) && !tonTaiMonDaTruot(ma)) {
                    return mssv + " Chua hoc mon hoc truoc: " + ma;
                }
            }
        }
        // 4. giới hạn tín chỉ
        if (soTinDangKi + mon.getsoTinChi() > TIN_CHI_TOI_DA) {
            return mssv + " Vuot qua " + TIN_CHI_TOI_DA + " tin chi (hien co " + soTinDangKi
                    + ", mon nay " + mon.getsoTinChi() + " tin).";
        }
        // 5. lớp đã đầy chưa
        if (lop.isFull()) {
            return "Lop " + lop.getmaLopHoc() + " da day.";
        }
        // 6. trùng thời khoá biểu
        if (trungLichHoc(lop)) {
            return "Lop " + lop.getmaLopHoc() + " bi trung lich voi lop da dang ky.";
        }
        return null;
    }

    // này là để đăng kí lớp, trả về true nếu thành công, false nếu thất bại (có in ra lý do)
    private boolean thucHienDangKi(MonHoc mon, LopHoc lop) {
        if (!lop.themSinhVien(mssv)) return false;
        monDaDangKi.them(mon.getmaHocPhan());
        soTinDangKi += mon.getsoTinChi();
        xepLichCuaLop(lop);
        return true;
    }

    // này là để hủy lớp, trả về true nếu thành công, false nếu thất bại (có in ra lý do)
    private void thucHienHuy(MonHoc mon, LopHoc lop) {
        lop.xoaSinhVien(mssv);
        monDaDangKi.xoa(mon.getmaHocPhan());
        soTinDangKi -= mon.getsoTinChi();
        xoaLichCuaLop(lop);
    }

    // này để đăng kí lớp, huỷ lớp, đổi lớp
    public boolean dangKiLop(MonHoc mon, String maLopHoc) {
        LopHoc lop = mon.get(maLopHoc);
        if (lop == null) {
            System.out.println("Khong tim thay lop " + maLopHoc + " trong mon " + mon.getmaHocPhan());
            return false;
        }
        String loi = kiemTraDangKi(mon, lop);
        if (loi != null) {
            System.out.println("Khong the dang ky: " + loi);
            return false;
        }
        if (!thucHienDangKi(mon, lop)) {
            System.out.println("Khong the dang ky lop " + maLopHoc);
            return false;
        }
        System.out.println(mssv + " Dang ky thanh cong lop: " + maLopHoc);
        return true;
    }

   public boolean huyLop(MonHoc mon, String maLopHoc) {
        LopHoc lop = mon.get(maLopHoc);
        if (lop == null || !lop.tonTai(mssv) || !monDaDangKi.tonTai(mon.getmaHocPhan())) {
            System.out.println(mssv + " chua dang ky lop nay.");
            return false;
        }
        thucHienHuy(mon, lop);
        System.out.println(mssv + " Da huy lop: " + maLopHoc);
        return true;
    }

    // này để đổi lớp, đổi không được thì để im lớp cũ
    public boolean doiLop(MonHoc mon, String maLopHocCu, String maLopHocMoi) {
        LopHoc lopCu = mon.get(maLopHocCu);
        if (lopCu == null || !lopCu.tonTai(mssv) || !monDaDangKi.tonTai(mon.getmaHocPhan())) {
            System.out.println(mssv + " Khong tim thay lop cu de doi lop " + maLopHocCu + " trong danh sach da dang ky.");
            return false;
        }
        LopHoc lopMoi = mon.get(maLopHocMoi);
        if (lopMoi == null) {
            System.out.println("Khong tim thay lop moi " + maLopHocMoi + " trong mon " + mon.getmaHocPhan());
            return false;
        }
        if (lopCu == lopMoi) {
            System.out.println("Lop moi trung voi lop cu.");
            return false;
        }
 
        thucHienHuy(mon, lopCu); // tạm rút khỏi lớp cũ để kiểm tra lớp mới
        String loi = kiemTraDangKi(mon, lopMoi);
        if (loi != null || !thucHienDangKi(mon, lopMoi)) {
            thucHienDangKi(mon, lopCu); // khôi phục lớp cũ
            System.out.println(mssv + " Khong the doi lop: " + (loi != null ? loi : "lop moi khong nhan them."));
            return false;
        }
        System.out.println(mssv + " Da hoan doi lop " + maLopHocCu + " -> " + maLopHocMoi);
        return true;
    }
 

    // này để in tkb
    public void xemLichHoc() {
        String[] tenThu = {"Thu 2", "Thu 3", "Thu 4", "Thu 5", "Thu 6", "Thu 7", "Chu nhat"};
        System.out.println("=== THOI KHOA BIEU CUA " + ten + " (MSSV: " + mssv + ") ===");
        for (int dot = 0; dot < 2; dot++) {
            System.out.println("-- Dot dang ky " + (dot + 1) + " --");
            for (int ngay = 0; ngay < 7; ngay++) {
                int hang = dot * 7 + ngay;
                for (int tiet = 0; tiet < 12; tiet++) {
                    if (!trong(thoiKhoaBieu[hang][tiet])) {
                        System.out.println("  " + tenThu[ngay] + ", tiet " + (tiet + 1)
                                + ": " + thoiKhoaBieu[hang][tiet]);
                    }
                }
            }
        }
    }


    @Override
    public String toString() {
        return String.format("SinhVien{ten='%s', mssv=%d, soTinDangKi=%d, monDaTruot.length=%d}",
                ten, mssv, soTinDangKi, monDaTruot != null ? monDaTruot.length : 0);
    }
//===============================================GETTER========================================================
    public String getten() {
        return ten;
    }
    public int getmssv() {
        return mssv;
    }
    public String[][] getthoiKhoaBieu() {
        return thoiKhoaBieu;
    }
    public String[] getmonDaDangKi() {
        return monDaDangKi.getdanhSachMonDaDangKi();
    }
    public int getsoTinDangKi() {
        return soTinDangKi;
    }
    public String[] getmonDaTruot() {
        return monDaTruot;
    }
    public String[] getmonTichLuy() {
        return monTichLuy.getdanhSachMonTichLuy();
    }
    public String getMatKhau() {
        return matKhau;
    }
}
