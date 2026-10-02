/*
 * Mô tả ngắn:
 * - Class đại diện cho một môn học trong hệ thống.
 * - Các mảng dữ liệu được khai báo tĩnh theo thiết kế. Kích thước mảng sẽ được
 *   khởi tạo khi đọc dữ liệu từ file JSON.
 */
public class MonHoc {
    private int soTinChi;
    private String tenMonHoc;
    private String maHocPhan;
    
    private String[] nganh; 
    private LopHoc[] cacLopHoc; 
    private String[] monHocTruoc; 
    private String[] monTienQuyet; 
    

    private int soLuongLopHienTai = 0;


    public MonHoc(int soTinChi, String tenMonHoc, String maHocPhan, 
                  String[] nganh, String[] monHocTruoc, String[] monTienQuyet, int soLuongLopToiDa) {
        this.soTinChi = soTinChi;
        this.tenMonHoc = tenMonHoc;
        this.maHocPhan = maHocPhan;
        this.nganh = nganh;
        this.monHocTruoc = monHocTruoc;
        this.monTienQuyet = monTienQuyet;
        
        this.cacLopHoc = new LopHoc[soLuongLopToiDa];
    }

    public String getTenMonHoc() {
        return this.tenMonHoc;
    }
    public String getMaHocPhan() {
        return this.maHocPhan;
    }
    public int getSoTinChi() {
        return this.soTinChi;
    }
    public String[] getNganh() {
        return this.nganh;
    }
    public String[] getMonTienQuyet() {
        return this.monTienQuyet;
    }
    public String[] getMonHocTruoc() {
        return this.monHocTruoc;
    }
    public LopHoc[] getCacLopHoc() {
        return this.cacLopHoc;
    }


    /*
      Hàm thêm lớp học vào mảng cacLopHoc.
      Lưu ý: Trong sơ đồ ghi tham số là (String maLopHoc), nhưng vì thuộc tính là mảng LopHoc[] 
      nên truyền thẳng object LopHoc vào để dễ quản lý.
      Chi phí thời gian: O(1) (Thêm vào cuối).
     */
    public void themLop(LopHoc lopHocMoi) {
        if (this.soLuongLopHienTai < this.cacLopHoc.length) {
            this.cacLopHoc[this.soLuongLopHienTai] = lopHocMoi;
            this.soLuongLopHienTai++;
        } else {
            System.out.println("Lỗi: Không thể thêm lớp. Mảng cacLopHoc của môn " + this.tenMonHoc + " đã đầy.");
        }
    }
    
    public boolean laMonDaiCuong() {
        if (this.nganh != null && this.nganh.length > 0) {
            return this.nganh[0].equals("ALL");
        }
        return false;
    }
}
