public class LopHocData {
    String maLopHoc;
    String tenGiaoVien;
    String maHocPhan;
    ThoiGian[] thoiGianHoc;
    int siSoToiDa;
    int[] sinhVienDaDangKi;

    public LopHoc toLopHoc() {
        return new LopHoc(maLopHoc, tenGiaoVien, maHocPhan,
                thoiGianHoc, siSoToiDa, sinhVienDaDangKi);
    }
}