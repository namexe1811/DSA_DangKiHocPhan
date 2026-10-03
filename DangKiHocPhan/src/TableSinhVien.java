//Nam Trần đã ở đây
/*Mô tả ngắn:
-Đây là mảng băm học sinh dựa trên mã số sinh viên
-Khởi tạo bằng hàm TableSinhVien(int size) với size là số sinh viên cho trước
-Thêm phần tử vào mảng bằng hàm put(SinhVien sv) với sv là sinh viên cần thêm
-Hàm get(String key) với key là mã số sinh viên,là hàm truy xuất sinh viên trong mảng, sẽ trả về kiểu SinhVien đã được định nghĩa ở class SinhVien
!!! ĐÂY LÀ MẢNG ĐỂ LƯU DỮ LIỆU MÔN HỌC VÀ SINH VIÊN TRONG QUÁ TRÌNH CHẠY,NÊN KO CÓ HÀM XOÁ PHẦN TỬ,KÍCH THƯỚC CŨNG SẼ CỐ ĐỊNH KHI CHẠY!!!
*/
public class TableSinhVien {
    private int size;//số lượng sinh viên
    private SinhVien[] danhSachSinhVien;//danh sách sinh viên
    public TableSinhVien(int size, SinhVien[] danhSachSinhVien){
        this.size=size;
        this.danhSachSinhVien = new SinhVien[size];
        for(SinhVien sv : danhSachSinhVien){
            this.them(sv);
        }
    }
    public int getsize(){
        return size;
    }
    //hàm băm
    private int hash(int mssv) {
        return mssv % size;
    }
    //thêm 1 học sinh vào mảng băm
    public void them(SinhVien sv) {
        int key = sv.getMSSV();//băm theo mã sinh viên
        int index = hash(key);
        int i = 0;
        while (danhSachSinhVien[(index + i) % size] != null) {
            // Nếu trùng MSSV thì cập nhật
            if (danhSachSinhVien[(index + i) % size].getMSSV().equals(key)) {
                danhSachSinhVien[(index + i) % size] = sv;
                return;
            }
            i++;
            if (i == size) {
                throw new RuntimeException("Bảng đầy!");//để cho chắc thôi,ko xảy ra đâu((=,vì trong file json đã có số học sinh ,mảng có kích thước bằng đúng số học sinh
            }
        }
        danhSachSinhVien[(index + i) % size] = sv;
    }
    //tìm vị trí học sinh trong mảng
    public SinhVien get(int key) {
        int index = hash(key);
        int i = 0;
        while (danhSachSinhVien[(index + i) % size] != null) {
            if (danhSachSinhVien[(index + i) % size].getMSSV().equals(key)) {
                return danhSachSinhVien[(index + i) % size];
            }
            i++;
            if (i == size) break;
        }
        return null;    
    }
}
