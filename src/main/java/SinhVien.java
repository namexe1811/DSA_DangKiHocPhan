package main.java;

public class SinhVien { 

    private static final int TIN_CHI_TOI_DA = 28;

    private String ten;
    private int mssv;
    private String nganh;
    private String[][] thoiKhoaBieu;
    private TableMonDaDangKi monDaDangKi;   // lưu MÃ HỌC PHẦN của các môn đã đăng kí
    private int soTinDangKi;
    private MangDong monDaTruot;
    private TableMonDaDangKi monTichLuy;    // lưu MÃ HỌC PHẦN các môn đã học và pass
    private String matKhau;

    public SinhVien(String ten, int mssv, String nganh, String matKhau) {
        this.ten = ten;
        this.mssv = mssv;
        this.nganh = nganh;
        this.matKhau = matKhau;
        this.thoiKhoaBieu = new String[14][12];
        this.monDaDangKi = new TableMonDaDangKi();
        this.monDaTruot = new MangDong(3);
        this.monTichLuy = new TableMonDaDangKi();
        this.soTinDangKi = 0;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getMSSV() {
        return mssv;
    }

    public void setMSSV(int mssv) {
        this.mssv = mssv;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getNganh() {
        return nganh;
    }

    public int getSoTinDangKi() {
        return soTinDangKi;
    }

    // ================== HÀM PHỤ (private) ==================

    // tìm lớp trong môn theo mã lớp (bỏ qua ô null của mảng)
    private LopHoc timLop(MonHoc mon, String maLopHoc) {
        LopHoc[] ds = mon.getCacLopHoc();
        if (ds == null) return null;
        for (LopHoc lop : ds) {
            if (lop != null && lop.getmaLopHoc().equals(maLopHoc)) return lop;
        }
        return null;
    }

    private boolean trong(String s) {
        return s == null || s.isEmpty();
    }

    // lớp mới có trùng giờ không: chỉ cần thấy 1 ô trong thời khoá biểu đã có lớp là trùng
    // quy ước: thu = số hàng (đợt 2 bắt đầu từ 7), tiết 1..12 -> cột 0..11
    private boolean trungLichHoc(LopHoc lop) {
        ThoiGian[] dsTg = lop.getthoiGianHoc();
        for (ThoiGian tg : dsTg) {
            for (int t = tg.getTietDau(); t <= tg.getTietCuoi(); t++) {
                if (!trong(thoiKhoaBieu[tg.getThu()][t - 1])) return true;
            }
        }
        return false;
    }

    private void xepLichCuaLop(LopHoc lop) {
        ThoiGian[] dsTg = lop.getthoiGianHoc();
        for (ThoiGian tg : dsTg) {
            for (int t = tg.getTietDau(); t <= tg.getTietCuoi(); t++) {
                thoiKhoaBieu[tg.getThu()][t - 1] = lop.getmaLopHoc();
            }
        }
    }

    private void xoaLichCuaLop(LopHoc lop) {
        ThoiGian[] dsTg = lop.getthoiGianHoc();
        for (ThoiGian tg : dsTg) {
            for (int t = tg.getTietDau(); t <= tg.getTietCuoi(); t++) {
                thoiKhoaBieu[tg.getThu()][t - 1] = null;
            }
        }
    }

    // Kiểm tra mọi điều kiện đăng kí. Trả về null nếu hợp lệ, ngược lại trả về lý do.
    private String kiemTraDangKi(MonHoc mon, LopHoc lop) {
        // 1. đã đăng kí môn này chưa
        if (monDaDangKi.coTonTai(mon.getMaHocPhan())) {
            return "Da dang ky mon " + mon.getMaHocPhan() + " roi.";
        }
        // 2. môn tiên quyết: phải học và pass (có trong monTichLuy)
        String[] tienQuyet = mon.getMonTienQuyet();
        if (tienQuyet != null) {
            for (String ma : tienQuyet) {
                if (!monTichLuy.coTonTai(ma)) {
                    return "Chua dat mon tien quyet: " + ma;
                }
            }
        }
        // 3. môn học trước: chỉ cần đã học (tích luỹ hoặc đã trượt)
        String[] hocTruoc = mon.getMonHocTruoc();
        if (hocTruoc != null) {
            for (String ma : hocTruoc) {
                if (!monTichLuy.coTonTai(ma) && !monDaTruot.coTonTai(ma)) {
                    return "Chua hoc mon hoc truoc: " + ma;
                }
            }
        }
        // 4. giới hạn tín chỉ
        if (soTinDangKi + mon.getSoTinChi() > TIN_CHI_TOI_DA) {
            return "Vuot qua " + TIN_CHI_TOI_DA + " tin chi (hien co " + soTinDangKi
                    + ", mon nay " + mon.getSoTinChi() + " tin).";
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
    // này là để đăng kí lớp , trả về true nếu thành công, false nếu thất bại (có in ra lý do)
    private boolean thucHienDangKi(MonHoc mon, LopHoc lop) {
        if (!lop.themSinhVien(mssv)) return false;
        monDaDangKi.push(mon.getMaHocPhan());
        soTinDangKi += mon.getSoTinChi();
        xepLichCuaLop(lop);
        return true;
    }

    // này là để hủy lớp , trả về true nếu thành công, false nếu thất bại (có in ra lý do)
    private void thucHienHuy(MonHoc mon, LopHoc lop) {
        lop.xoaSinhVien(mssv);
        monDaDangKi.xoa(mon.getMaHocPhan());
        soTinDangKi -= mon.getSoTinChi();
        xoaLichCuaLop(lop);
    }

    // này để đăng kí lớp, huỷ lớp, đổi lớp 
    public boolean dangKiLop(String maHocPhan, String maLopHoc, TableMonHoc tableMonHoc) {
        MonHoc mon = tableMonHoc.get(maHocPhan);
        if (mon == null) {
            System.out.println("Khong tim thay mon " + maHocPhan);
            return false;
        }
        LopHoc lop = timLop(mon, maLopHoc);
        if (lop == null) {
            System.out.println("Khong tim thay lop " + maLopHoc + " trong mon " + maHocPhan);
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
        System.out.println("Dang ky thanh cong lop: " + maLopHoc);
        return true;
    }

    public boolean huyLop(String maHocPhan, String maLopHoc, TableMonHoc tableMonHoc) {
        MonHoc mon = tableMonHoc.get(maHocPhan);
        if (mon == null) {
            System.out.println("Khong tim thay mon " + maHocPhan);
            return false;
        }
        LopHoc lop = timLop(mon, maLopHoc);
        if (lop == null || !lop.coSinhVien(mssv) || !monDaDangKi.coTonTai(maHocPhan)) {
            System.out.println("Sinh vien chua dang ky lop nay.");
            return false;
        }
        thucHienHuy(mon, lop);
        System.out.println("Da huy lop: " + maLopHoc);
        return true;
    }
    //này để đổi lớp , đổi không được thì để im lớp cũ 
    public boolean doiLop(String maHocPhan, String maLopHocCu, String maLopHocMoi, TableMonHoc tableMonHoc) {
        MonHoc mon = tableMonHoc.get(maHocPhan);
        if (mon == null) {
            System.out.println("Khong tim thay mon " + maHocPhan);
            return false;
        }
        LopHoc lopCu = timLop(mon, maLopHocCu);
        if (lopCu == null || !lopCu.coSinhVien(mssv) || !monDaDangKi.coTonTai(maHocPhan)) {
            System.out.println("Khong tim thay lop cu " + maLopHocCu + " trong danh sach da dang ky.");
            return false;
        }
        LopHoc lopMoi = timLop(mon, maLopHocMoi);
        if (lopMoi == null) {
            System.out.println("Khong tim thay lop moi " + maLopHocMoi + " trong mon " + maHocPhan);
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
            System.out.println("Khong the doi lop: " + (loi != null ? loi : "lop moi khong nhan them."));
            return false;
        }
        System.out.println("Da hoan doi lop " + maLopHocCu + " -> " + maLopHocMoi);
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

    // public void xepLichHoc(int dot, int thu, int tiet, String maLopHoc) {
    //     int hang = (dot - 1) * 7 + thu;
    //     thoiKhoaBieu[hang][tiet] = maLopHoc;
    // }

    // một số thao tác với môn đã trượt và môn tích luỹ (đã học và pass)
    public boolean themMonDaTruot(String maMonHoc) {
        if (monDaTruot.coTonTai(maMonHoc)) return false;
        monDaTruot.them(maMonHoc);
        return true;
    }

    public boolean xoaMonDaTruot(String maMonHoc) {
        return monDaTruot.xoa(maMonHoc);
    }

    public boolean daTruotMon(String maMonHoc) {
        return monDaTruot.coTonTai(maMonHoc);
    }

    public String[] danhSachMonDaTruot() {
        return monDaTruot.toArray();
    }

    public void themMonTichLuy(String maMonHoc) {
        monTichLuy.push(maMonHoc);
    }

    public boolean daTichLuyMon(String maMonHoc) {
        return monTichLuy.coTonTai(maMonHoc);
    }

    @Override
    public String toString() {
        return String.format("SinhVien{ten='%s', mssv=%d, nganh='%s', soTinDangKi=%d, soMonDaTruot=%d}",
                ten, mssv, nganh, soTinDangKi, monDaTruot.size());
    }
}