/*Mô tả ngắn:
-Đây là mảng băm môn học dựa trên mã học phần
-Khởi tạo bằng hàm TableMonHoc(int size) với size là số môn học cho trước
-Thêm phần tử vào mảng bằng hàm put(MonHoc mh) với mh là môn học cần thêm
-Hàm get(String key) với key là mã môn học,là hàm truy xuất môn học, trong mảng sẽ trả về kiểu MonHoc đã được định nghĩa ở class MonHoc
!!! ĐÂY LÀ MẢNG ĐỂ LƯU DỮ LIỆU MÔN HỌC TRONG QUÁ TRÌNH CHẠY,NÊN KO CÓ HÀM XOÁ PHẦN TỬ,KÍCH THƯỚC CŨNG SẼ CỐ ĐỊNH KHI CHẠY!!!
*/

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TableMonHoc {
    private int size;//số lượng môn học
    private int cap;//sức chứa của mảng băm
    private MonHoc[] danhSachMonHoc;
//===============================================CONSTRUCTOR========================================================
    @JsonCreator
    public TableMonHoc(@JsonProperty("danhSachMonHoc") MonHoc[] danhSachMonHoc) {
        this.size = danhSachMonHoc != null ? danhSachMonHoc.length : 0;
        this.cap = SoNguyenTo((int) Math.ceil(size * 1.43));
        this.danhSachMonHoc = new MonHoc[cap];
        if (danhSachMonHoc != null) {
            for (MonHoc mh : danhSachMonHoc) {
                this.them(mh); 
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
    private int hash(String key){
        long h = 0;
        int p = 31;
        for (int i = 0; i < key.length(); i++) {
            h = h * p + key.charAt(i);
        }
        return (int)(Math.abs(h) % cap);
    }
    //thêm môn vào mảng băm
    public void them(MonHoc mh) {
        String key = mh.getmaHocPhan();//băm theo mã môn học
        int index = hash(key);
        int i = 0;
        while (danhSachMonHoc[(index + i) % cap] != null) {
            // Nếu trùng MSSV thì cập nhật
            if (danhSachMonHoc[(index + i) % cap].getmaHocPhan().equals(key)) {
                danhSachMonHoc[(index + i) % cap] = mh;
                return;
            }
            i++;
        }
        danhSachMonHoc[(index + i) % cap] = mh;
    }
    //tìm môn học trong mảng
    public MonHoc get(String key) {
        int index = hash(key);
        int i = 0;
        while (danhSachMonHoc[(index + i) % cap] != null) {
            if (danhSachMonHoc[(index + i) % cap].getmaHocPhan().equals(key)) {
                return danhSachMonHoc[(index + i) % cap];
            }
            i++;
            if (i == cap) break;
        }
        return null;    
    }
//===============================================GETTER========================================================
    public MonHoc[] getDanhSachMonHoc() {
        MonHoc[] result = new MonHoc[size];
        int idx = 0;
        for (MonHoc mh : danhSachMonHoc) {
            if (mh != null) {
                result[idx++] = mh;
            }
        }
        return result;
    }
}
