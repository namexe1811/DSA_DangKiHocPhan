public class LopHocData {
    private String maLopHoc;
    private String tenGiaoVien;
    private String maHocPhan;
    private ThoiGian[] thoiGianHoc;
    private int siSoToiDa;
    private int[] sinhVienDaDangKi;

    // Constructor đầy đủ
    public LopHocData(String maLopHoc, String tenGiaoVien, String maHocPhan,
                      ThoiGian[] thoiGianHoc, int siSoToiDa, int[] sinhVienDaDangKi) {
        this.maLopHoc = maLopHoc;
        this.tenGiaoVien = tenGiaoVien;
        this.maHocPhan = maHocPhan;
        this.thoiGianHoc = thoiGianHoc;
        this.siSoToiDa = siSoToiDa;
        this.sinhVienDaDangKi = sinhVienDaDangKi;
    }

    // Getter
    public String getMaLopHoc() {
        return maLopHoc;
    }

    public String getTenGiaoVien() {
        return tenGiaoVien;
    }

    public String getMaHocPhan() {
        return maHocPhan;
    }

    public ThoiGian[] getThoiGianHoc() {
        return thoiGianHoc;
    }

    public int getSiSoToiDa() {
        return siSoToiDa;
    }

    public int[] getSinhVienDaDangKi() {
        return sinhVienDaDangKi;
    }
}
