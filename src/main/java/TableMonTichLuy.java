// à thì để băm môn tích lũy 
public class TableMonTichLuy {
    private String[] danhSach;   // cái bảng băm này
    private int sucChua;         // độ dài mảng

    // constructor này 
    public TableMonTichLuy(String[] cacMonTichLuy) {
        int n = (cacMonTichLuy == null) ? 0 : cacMonTichLuy.length; // n là coi có nhiêu môn cần lưu
        this.sucChua = SoNguyenTo((int) Math.ceil(n * 1.43)); // này để tính nên tạo mảng có độ dài là nhiêu này (math.ceil để làm tròn số)
        this.danhSach = new String[sucChua];  // tạo mảng 
        if (cacMonTichLuy != null) {  
            for (String mon : cacMonTichLuy) {   //này thêm vào mảng thôi đã băm rồi
                if (mon != null && !mon.isEmpty()) {
                    them(mon);
                }
            }
        }
    }

    private static int SoNguyenTo(int n) { // này để tìm số nguyên tố này
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

    // Hàm băm
    private int hash(String key) {      
        long h = 0;
        int p = 31;
        for (int i = 0; i < key.length(); i++) {  // này băm bằng trộn h thôi 
            h = h * p + key.charAt(i);
        }
        return (int) (Math.abs(h) % sucChua);
    }

    // thêm môn tích lũy vào bảng
    public void them(String giaTri) {
        int idx = hash(giaTri); // này tính index này 
        for (int i = 0; i < sucChua; i++) {  //duyệt tuyến tính hết cả mảng
            int vt = (idx + i) % sucChua;    // này là kiểu index từ 5 thì tính là 5 , 6 , 7 , .... , 0 , 1 ,2 
            if (danhSach[vt] == null) {             // trống thì đặt
                danhSach[vt] = giaTri;
                return;
            }
            if (danhSach[vt].equals(giaTri)) return; //có rồi không thêm nữa 
        }
       System.out.println("Bang mon tich luy da day!");
    }

    // này để kiểm tra môn tích lũy có tồn tại trong bảng hay không , ik chang cái thêm nhưng mà không thêm chỉ trả về true or false
    public boolean tonTai(String giaTri) {
        int idx = hash(giaTri);
        for (int i = 0; i < sucChua; i++) {
            int vt = (idx + i) % sucChua;
            if (danhSach[vt] == null) return false;  // gặp ô trống là chắc chắn không có
            if (danhSach[vt].equals(giaTri)) return true;
        }
        return false;
    }

    // Getter trả về mảng để ghi file
    // public String[] getDanhSachMonTichLuy() {
    //     int dem = 0;
    //     for (String mon : danhSach) {
    //         if (mon != null) dem++;
    //     }
    //     String[] kq = new String[dem];
    //     int k = 0;
    //     for (String mon : danhSach) {
    //         if (mon != null) kq[k++] = mon;
    //     }
    //     return kq;
    // } cái này á là để bảo vệ dữ liệu nó `không trả về mảng môn tích lũy mà trả về mảng sao chép , thì nếu cái mảng sao chép bị sửa thì cái mảng cũ không sao 
    // public String[] getDanhSachMonTichLuy() {
    //     return danhSach.clone(); // trả về mảng sao chép
    // } viết vầy cũng được 
       public String[]  getDanhSachMonTichLuy() {
        return danhSach; // trả về mảng gốc
       }
       // tối quá buòn ngủ quá không nghĩ được nên xài cái nào nên để vầy mai chọn .
}