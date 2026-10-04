/*
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MonHoc {
    private int soTinChi;
    private String tenMonHoc;
    private String maHocPhan;
    ///
    private LopHoc[] cacLopHoc; 
    private String[] monHocTruoc; 
    private String[] monTienQuyet;
    /// 
    @JsonCreator
    public MonHoc(@JsonProperty("soTinChi") int soTinChi,
                  @JsonProperty("tenMonHoc") String tenMonHoc,
                  @JsonProperty("maHocPhan") String maHocPhan,
                  @JsonProperty("monHocTruoc") String[] monHocTruoc,
                  @JsonProperty("monTienQuyet") String[] monTienQuyet,
                  @JsonProperty("cacLopHoc") LopHoc[] cacLopHoc) {
        this.soTinChi = soTinChi;
        this.tenMonHoc = tenMonHoc;
        this.maHocPhan = maHocPhan;
        
        this.monHocTruoc = monHocTruoc;
        this.monTienQuyet = monTienQuyet;
        
        // Khởi tạo Bảng băm
        if (cacLopHoc != null) { // Kiểm tra xem mảng cacLopHoc có bị rỗng không
            this.cacLopHoc = toHashTable(cacLopHoc);
        } else {
            this.cacLopHoc = new LopHoc[7];
        }
    }
/*
    Tại sao chọn số nguyên tố ? 
    - Tính phân tán: Nếu kích thước mảng là một hợp số (ví dụ: 10, 100), các khóa có quy luật toán học (chẳng hạn như các số chẵn, 
            hoặc các số tận cùng bằng 0) khi chia lấy dư sẽ liên tục hội tụ vào cùng một vài khe cố định. Số nguyên tố (như 31, 100003) 
                không chia hết cho bất kỳ số nào ngoài 1 và chính nó.Khi dùng nó làm mẫu số trong phép chia lấy dư (hash_code % prime), 
                    nó phá vỡ mọi quy luật nhịp điệu của dữ liệu đầu vào, ép các chỉ số (index) phải rải đều ngẫu nhiên ra toàn bộ các khe trống.
    - 
*/
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

    public int hash(String key) { // Chuyển đổi chuỗi -> valid index
        long h = 0;
        int p = 31;
        for (int i = 0; i < key.length(); i++) {
            h = h * p + key.charAt(i);
        }
        return (int)(Math.abs(h) % this.cacLopHoc.length);
    }

    public LopHoc[] toHashTable(LopHoc[] array) {
        int cap = SoNguyenTo(array.length > 0 ? array.length : 0);
        LopHoc[] table = new LopHoc[cap];
        for (LopHoc lop : array) {
            if (lop != null) { // Kiểm tra phần tử bên trong có bị rỗng không
                String key = lop.getmaLopHoc();
                long h = 0;
                int p = 31;
                for (int i = 0; i < key.length(); i++) {
                    h = h * p + key.charAt(i);
                }
                int idx = (int)(Math.abs(h) % table.length);
                int startIdx = idx;
                while (table[idx] != null) {
                    idx = (idx + 1) % table.length;
                    if (idx == startIdx) break;
                }
                table[idx] = lop;
            }
        }
        return table;
    }

    public boolean them(LopHoc lopHocMoi) { //thêm lớp học vào mảng, nếu vị trí đã có người chiếm sẽ tự động trượt sang phải để tìm ô trống kế tiếp (linear probign)
        if (lopHocMoi == null) return false;
        String key = lopHocMoi.getmaLopHoc();
        int idx = hash(key);
        int startIdx = idx;
        
        while (this.cacLopHoc[idx] != null) {
            if (this.cacLopHoc[idx].getmaLopHoc().equals(key)) return false;
            idx = (idx + 1) % this.cacLopHoc.length;
            if (idx == startIdx) {
                System.out.println("Lỗi: Không thể thêm lớp. Mảng cacLopHoc của môn " + this.tenMonHoc + " đã đầy.");
                return false;
            }
        }
        this.cacLopHoc[idx] = lopHocMoi;
        return true;
    }
    
    public LopHoc get(String key) {
        if (key == null || this.cacLopHoc == null) return null;
        int idx = hash(key);
        int startIdx = idx;
        
        while (this.cacLopHoc[idx] != null) {
            if (this.cacLopHoc[idx].getmaLopHoc().equals(key)) {
                return this.cacLopHoc[idx];
            }
            idx = (idx + 1) % this.cacLopHoc.length;
            if (idx == startIdx) break;
        }
        return null;
    }

    public boolean tonTai(String key) { //Kiểm tra môn có tổn tại mã này hay không 
        return get(key) != null;
    }

    public boolean xoa(String key) {
        if (key == null || this.cacLopHoc == null) return false;
        int idx = hash(key);
        int startIdx = idx;
        
        while (this.cacLopHoc[idx] != null) {
            if (this.cacLopHoc[idx].getmaLopHoc().equals(key)) {
                this.cacLopHoc[idx] = null;
                return true;
            }
            idx = (idx + 1) % this.cacLopHoc.length;
            if (idx == startIdx) break;
        }
        return false;
    }

    public String getTenMonHoc() {return this.tenMonHoc;}
    public String getMaHocPhan() {return this.maHocPhan;}
    public int getSoTinChi() {return this.soTinChi;}
    public String[] getMonTienQuyet() {return this.monTienQuyet;}
    public String[] getMonHocTruoc() {return this.monHocTruoc;}
    public LopHoc[] getCacLopHoc() {
    int size = 0;
    for (int i = 0; i < cacLopHoc.length; i++) {
        if (cacLopHoc[i] != null) size++;
    }
    LopHoc[] kq = new LopHoc[size];
    int k = 0;
    for ( int i = 0; i < cacLopHoc.length; i++) {
        if ( cacLopHoc[i]  != null) {
            kq[k++] = cacLopHoc[i];
        }
    }
    return kq;
    }
}
