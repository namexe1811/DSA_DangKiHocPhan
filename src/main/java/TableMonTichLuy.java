// à thì để băm môn tích lũy 
public class TableMonTichLuy {
    private String[] danhSach;   // cái bảng băm này
    private int cap;//độ dài mảng băm
    private int size;//số lượng môn tích lũy đã lưu

//===============================================CONSTRUCTOR========================================================
    public TableMonTichLuy(String[] cacMonTichLuy) {
        this.size = (cacMonTichLuy == null) ? 0 : cacMonTichLuy.length; // n là coi có nhiêu môn cần lưu
        this.cap = SoNguyenTo((int) Math.ceil(size * 1.43)); // này để tính nên tạo mảng có độ dài là nhiêu này (math.ceil để làm tròn số)
        this.danhSach = new String[cap];  // tạo mảng 
        if (cacMonTichLuy != null) {  
            for (String mon : cacMonTichLuy) {   //này thêm vào mảng thôi đã băm rồi
                if (mon != null && !mon.isEmpty()) {
                    them(mon);
                }
            }
        }
    }
//===============================================HÀM==================================================================
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
        return (int) (Math.abs(h) % cap);
    }

    // thêm môn tích lũy vào bảng
    public void them(String giaTri) {
        int idx = hash(giaTri); // này tính index này 
        for (int i = 0; i <cap; i++) {  //duyệt tuyến tính hết cả mảng
            int vt = (idx + i) % cap;    // này là kiểu index từ 5 thì tính là 5 , 6 , 7 , .... , 0 , 1 ,2 
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
        for (int i = 0; i < cap; i++) {
            int vt = (idx + i) % cap;
            if (danhSach[vt] == null) return false;  // gặp ô trống là chắc chắn không có
            if (danhSach[vt].equals(giaTri)) return true;
        }
        return false;
    }
//===============================================GETTER========================================================
    public String[] getdanhSachMonTichLuy() {
        String[] result = new String[size];
        int idx = 0;
        for (String mon : danhSach) {
            if (mon != null) {
                result[idx++] = mon;
            }
        }
        return result;
    }
}