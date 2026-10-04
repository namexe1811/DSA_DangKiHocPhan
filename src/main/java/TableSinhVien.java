//Nam Trần đã ở đây
/*Mô tả ngắn:
-Đây là mảng băm học sinh dựa trên mã số sinh viên
-Khởi tạo bằng hàm TableSinhVien(int size) với size là số sinh viên cho trước
-Thêm phần tử vào mảng bằng hàm put(SinhVien sv) với sv là sinh viên cần thêm
-Hàm get(String key) với key là mã số sinh viên,là hàm truy xuất sinh viên trong mảng, sẽ trả về kiểu SinhVien đã được định nghĩa ở class SinhVien
!!! ĐÂY LÀ MẢNG ĐỂ LƯU DỮ LIỆU MÔN HỌC VÀ SINH VIÊN TRONG QUÁ TRÌNH CHẠY,NÊN KO CÓ HÀM XOÁ PHẦN TỬ,KÍCH THƯỚC CŨNG SẼ CỐ ĐỊNH KHI CHẠY!!!
*/

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TableSinhVien {
    private int cap;//sức chứa của mảng băm
    private int size;//số lượng sinh viên
    private SinhVien[] danhSachSinhVien;//danh sách sinh viên
//===============================================CONSTRUCTOR========================================================
    @JsonCreator
    public TableSinhVien(@JsonProperty("danhSachSinhVien") SinhVien[] sinhVienDaDangKi) {
        this.size = sinhVienDaDangKi != null ? sinhVienDaDangKi.length : 0;
        this.cap = SoNguyenTo((int) Math.ceil(size * 1.43));
        this.danhSachSinhVien = new SinhVien[SoNguyenTo(cap)];
        if (sinhVienDaDangKi != null) {
            for (SinhVien sv : sinhVienDaDangKi) {
                this.them(sv);
            }
        }
    }
//===============================================HÀM==================================================================
    //hàm tìm số nguyên tố lớn hơn hoặc bằng n
    private static int SoNguyenTo(int n) {
        if (n < 2) n = 2;
        while (true) {
            boolean ok = true;
            for (int i = 2; (long) i * i <= n; i++) {
                if (n % i == 0) { ok = false; break; }
            }
            if (ok) return n;
            n++;
        }
    }
    //hàm băm
    private int hash(int mssv) {
        return mssv % cap;
    }
    //thêm 1 học sinh vào mảng băm
    public void them(SinhVien sv) {
        int key = sv.getmssv();//băm theo mã sinh viên
        int index = hash(key);
        int i = 0;
        while (danhSachSinhVien[(index + i) % cap] != null) {
            // Nếu trùng MSSV thì cập nhật
            if (danhSachSinhVien[(index + i) % cap].getmssv()==key) {
                danhSachSinhVien[(index + i) % cap] = sv;
                return;
            }
            i++;
        }
        danhSachSinhVien[(index + i) % cap] = sv;
    }
    //tìm vị trí học sinh trong mảng
    public SinhVien get(int key) {
        int index = hash(key);
        int i = 0;
        while (danhSachSinhVien[(index + i) % cap] != null) {
            if (danhSachSinhVien[(index + i) % cap].getmssv()==key) {
                return danhSachSinhVien[(index + i) % cap];
            }
            i++;
            if (i == cap) break;
        }
        return null;    
    }
//===============================================GETTER========================================================
    public SinhVien[] getdanhSachSinhVien() {
        SinhVien[] result = new SinhVien[size];
        int idx = 0;
        for (SinhVien sv : danhSachSinhVien) {
            if (sv != null) {
                result[idx++] = sv;
                if (idx == size) break; // đủ số lượng sinh viên thì dừng
            }
        }
        return result;
    }
}
