import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TableMonTichLuy {
    private static final int SUC_CHUA_TOI_THIEU = 20; 
    private int size;
    private String[] danhSachMonTichLuy;//danh sach ma mon da tich luy

    @JsonCreator
    public TableMonTichLuy(@JsonProperty("danhSachMonTichLuy") String[] danhSachMonTichLuy) {
        int soLuongBanDau = (danhSachMonTichLuy == null) ? 0 : danhSachMonTichLuy.length;
        this.size = SoNguyenTo(Math.max(soLuongBanDau, SUC_CHUA_TOI_THIEU));
        this.danhSachMonTichLuy = new String[size];
        if (danhSachMonTichLuy != null) {
            for (String ma : danhSachMonTichLuy) {
                if (ma != null) this.push(ma);
            }
        }
    }

    public int getsize() {
        return size;
    }

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

    private int hash(String key) {
        long h = 0;
        int p = 31;
        for (int i = 0; i < key.length(); i++) {
            h = h * p + key.charAt(i);
        }
        return (int) (Math.abs(h) % size);
    }

    //them 1 ma mon vao mang bam
    public void push(String maMonHoc) {
        int index = hash(maMonHoc);//bam theo ma mon hoc
        int i = 0;
        while (danhSachMonTichLuy[(index + i) % size] != null) {
            if (danhSachMonTichLuy[(index + i) % size].equals(maMonHoc)) {
                return;
            }
            i++;
            if (i == size) {
                throw new RuntimeException("Bang day!");
            }
        }
        danhSachMonTichLuy[(index + i) % size] = maMonHoc;
    }

    //tim 1 ma mon trong mang, tra ve true neu co, false neu khong
    public boolean coTonTai(String key) {
        int index = hash(key);
        int i = 0;
        while (danhSachMonTichLuy[(index + i) % size] != null) {
            if (danhSachMonTichLuy[(index + i) % size].equals(key)) {
                return true;
            }
            i++;
            if (i == size) break;
        }
        return false;
    }
}
