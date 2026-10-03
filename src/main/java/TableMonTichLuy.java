
public class TableMonTichLuy {

    private static final String DA_XOA = "\u0000__DA_XOA__";

    private String[] table;
    private int tableSize;
    private int soLuong; 
    private static final double NGUONG_TAI = 0.75; // load factor de resize

    public TableMonTichLuy() {
        this(16);
    }

    public TableMonTichLuy(int size) {
        this.tableSize = size;
        this.table = new String[tableSize];
        this.soLuong = 0;
    }

    private int hash(String key) {
        long h = 0;
        int p = 31;
        for (int i = 0; i < key.length(); i++) {
            h = h * p + key.charAt(i);
        }
        return (int) (Math.abs(h) % tableSize);
    }


    public void push(String giaTri) {
        if (coTonTai(giaTri)) return; // không thêm trùng
        if ((double) (soLuong + 1) / tableSize > NGUONG_TAI) {
            resize(tableSize * 2);
        }
        int idx = hash(giaTri);
        int buocNhay = 0;
        // ô trùng thì do sang ô khác
        while (table[idx] != null && !table[idx].equals(DA_XOA)) {
            idx = (idx + 1) % tableSize;
            buocNhay++;
            if (buocNhay >= tableSize) {
                resize(tableSize * 2);
                idx = hash(giaTri);
                buocNhay = 0;
            }
        }
        table[idx] = giaTri;
        soLuong++;
    }

    public boolean coTonTai(String giaTri) {
        int idx = hash(giaTri);
        int buocNhay = 0;

        while (table[idx] != null && buocNhay < tableSize) {
            if (!table[idx].equals(DA_XOA) && table[idx].equals(giaTri)) {
                return true;
            }
            idx = (idx + 1) % tableSize;
            buocNhay++;
        }
        return false;
    }

    public boolean xoa(String giaTri) {
        int idx = hash(giaTri);
        int buocNhay = 0;
        while (table[idx] != null && buocNhay < tableSize) {
            if (!table[idx].equals(DA_XOA) && table[idx].equals(giaTri)) {
                table[idx] = DA_XOA;
                soLuong--;
                return true;
            }
            idx = (idx + 1) % tableSize;
            buocNhay++;
        }
        return false;
    }

    public int size() {
        return soLuong;
    }


    public String[] toArray() {
        String[] ketQua = new String[soLuong];
        int k = 0;
        for (String o : table) {
            if (o != null && !o.equals(DA_XOA)) {
                ketQua[k++] = o;
            }
        }
        return ketQua;
    }

    private void resize(int newSize) {
        String[] oldTable = table;
        this.tableSize = newSize;
        this.table = new String[tableSize];
        this.soLuong = 0;
        for (String o : oldTable) {
            if (o != null && !o.equals(DA_XOA)) {
                push(o);
            }
        }
    }
}
