//table tạm để đọc file
public class TableSinhVienData {
    private int soSinhVien;
    private SinhVien[] danhSachSinhVien;

    public TableSinhVienData(int soSinhVien , SinhVien[] danhSachSinhVien){
        this.soSinhVien=soSinhVien;
        this danhSachSinhVien=danhSachSinhVien;
    }
    public int getsoSinhVien(){return soSinhVien;}
    public SinhVien[] getdanhSachSinhVien(){return danhSachSinhVien;}
}