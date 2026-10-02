
public class SinhVien {

    private String ten;
    private int mssv;
    private String nganh;
    private String[][] thoiKhoaBieu;
    private BangBam monDaDangKi;
    private int soTinDangKi;
    private MangDong monDaTruot;
    private BangBam monTichLuy;
    private String matKhau;

    public SinhVien(String ten, int mssv, String nganh, String matKhau) {
        this.ten = ten;
        this.mssv = mssv;
        this.nganh = nganh;
        this.matKhau = matKhau;
        this.thoiKhoaBieu = new String[14][12];
        this.monDaDangKi = new BangBam();
        this.monDaTruot = new MangDong(3);
        this.monTichLuy = new BangBam();
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



    public void dangKiLop(String maLopHoc) {
        if (monDaDangKi.size() >= 28) {
            System.out.println("Khong the dang ky them: da dat toi da 28 mon.");
            return;
        }
        monDaDangKi.them(maLopHoc);
        soTinDangKi++;
        System.out.println("Dang ky thanh cong lop: " + maLopHoc);
    }


    public void huyLop(String maLopHoc) {
        boolean daXoa = monDaDangKi.xoa(maLopHoc);
        if (daXoa) {
            soTinDangKi--;
            System.out.println("Da huy lop: " + maLopHoc);
        } else {
            System.out.println("Sinh vien chua dang ky lop nay.");
        }
    }


    public void hoiLop(String maLopHocCu, String maLopHocMoi) {
        if (!monDaDangKi.coTonTai(maLopHocCu)) {
            System.out.println("Khong tim thay lop cu " + maLopHocCu + " trong danh sach da dang ky.");
            return;
        }
        huyLop(maLopHocCu);
        dangKiLop(maLopHocMoi);
        System.out.println("Da hoan doi lop " + maLopHocCu + " -> " + maLopHocMoi);
    }


    public void xemLichHoc() {
        String[] tenThu = {"Thu 2", "Thu 3", "Thu 4", "Thu 5", "Thu 6", "Thu 7", "Chu nhat"};
        System.out.println("=== THOI KHOA BIEU CUA " + ten + " (MSSV: " + mssv + ") ===");
        for (int dot = 0; dot < 2; dot++) {
            System.out.println("-- Dot dang ky " + (dot + 1) + " --");
            for (int ngay = 0; ngay < 7; ngay++) {
                int hang = dot * 7 + ngay;
                for (int tiet = 0; tiet < 12; tiet++) {
                    if (thoiKhoaBieu[hang][tiet] != null) {
                        System.out.println("  " + tenThu[ngay] + ", tiet " + (tiet + 1)
                                + ": " + thoiKhoaBieu[hang][tiet]);
                    }
                }
            }
        }
    }


    public void xepLichHoc(int dot, int thu, int tiet, String maLopHoc) {
        int hang = (dot - 1) * 7 + thu;
        thoiKhoaBieu[hang][tiet] = maLopHoc;
    }




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
        monTichLuy.them(maMonHoc);
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