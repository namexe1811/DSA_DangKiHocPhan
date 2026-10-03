package main.java;
/*Mô tả ngắn:
-Đây là mảng băm môn học dựa trên mã học phần
-Khởi tạo bằng hàm TableMonHoc(int size) với size là số môn học cho trước
-Thêm phần tử vào mảng bằng hàm put(MonHoc mh) với mh là môn học cần thêm
-Hàm get(String key) với key là mã môn học,là hàm truy xuất môn học, trong mảng sẽ trả về kiểu MonHoc đã được định nghĩa ở class MonHoc
!!! ĐÂY LÀ MẢNG ĐỂ LƯU DỮ LIỆU MÔN HỌC TRONG QUÁ TRÌNH CHẠY,NÊN KO CÓ HÀM XOÁ PHẦN TỬ,KÍCH THƯỚC CŨNG SẼ CỐ ĐỊNH KHI CHẠY!!!
*/

public class TableMonHoc {
    private int size;//số lượng môn học
    private MonHoc[] danhSachMonHoc;
    public TableMonHoc(int size , MonHoc[] danhSachMonHoc){
        this.size=size;
        this.danhSachMonHoc = new MonHoc[size];
        for(MonHoc mh : danhSachMonHoc){
            this.them(mh);
        }
        
    }
    public int getsize(){
        return size;
    }
    //hàm băm
    private int hash(String key){
        long h = 0;
        int p = 31;
        for (int i = 0; i < key.length(); i++) {
            h = h * p + key.charAt(i);
        }
        return (int)(Math.abs(h) % size);
    }
    //thêm môn vào mảng băm
    public void them(MonHoc mh) {
        String key = mh.getMaHocPhan();//băm theo mã môn học
        int index = hash(key);
        int i = 0;
        while (danhSachMonHoc[(index + i) % size] != null) {
            // Nếu trùng MSSV thì cập nhật
            if (danhSachMonHoc[(index + i) % size].getMaHocPhan().equals(key)) {
                danhSachMonHoc[(index + i) % size] = mh;
                return;
            }
            i++;
            if (i == size) {
                throw new RuntimeException("Bảng đầy!");//để cho chắc thôi,ko xảy ra đâu((=,vì trong file json đã có số học sinh ,mảng có kích thước bằng đúng số học sinh
            }
        }
        danhSachMonHoc[(index + i) % size] = mh;
    }
    //tìm môn học trong mảng
    public MonHoc get(String key) {
        int index = hash(key);
        int i = 0;
        while (danhSachMonHoc[(index + i) % size] != null) {
            if (danhSachMonHoc[(index + i) % size].getMaHocPhan().equals(key)) {
                return danhSachMonHoc[(index + i) % size];
            }
            i++;
            if (i == size) break;
        }
        return null;    
    }
}
