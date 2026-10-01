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
    public TableSinhVien(int size){
        this.size=size;
        this.danhSachSinhVien = new SinhVien[size];
    }
    public int getsize(){
        return size;
    }
    //hàm băm
    private int hash(String key) {
        //phần mã hash của số
        int numPart = 0;
        String digits = key.replaceAll("\\D", "");//bỏ kí tự ko phải số
        if (!digits.isEmpty()) {
            numPart = Integer.parseInt(digits);
        }
        //phần chữ
        int charPart = 0;
        for (char c : key.toCharArray()) {
            if (Character.isLetter(c)) {
                charPart += c; // cộng mã ASCII của chữ
            }
        }

        return (numPart + charPart) % size;
    }
    //thêm 1 học sinh vào mảng băm
    public void put(SinhVien sv) {
        String key = sv.getMSSV();//băm theo mã sinh viên
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
    public SinhVien get(String key) {
        int h = hash(key);
        int i = 0;
        while (danhSachSinhVien[(h + i) % size] != null) {
            if (danhSachSinhVien[(h + i) % size].getMSSV().equals(key)) {
                return danhSachSinhVien[(h + i) % size];
            }
            i++;
            if (i == size) break;
        }
        return null;    
    }
}
