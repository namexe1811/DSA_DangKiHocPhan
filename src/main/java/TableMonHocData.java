//table tạm để đọc file
public class TableMonHocData {
    private int soMonHoc;//số lượng môn học
    private MonHoc[] danhSachMonHoc;

    public TableMonHocData(int soMonHoc , MonHoc[] danhSachMonHoc){
        this.soMonHoc=soMonHoc;
        this.danhSachMonHoc=danhSachMonHoc;
    }
    
    public getsoMonHoc(){return soMonHoc;}
    public getdanhSachMonHoc(){return danhSachMonHoc;}
}
