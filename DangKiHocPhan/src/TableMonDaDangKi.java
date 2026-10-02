public class TableMonDaDangKi {

    private static class Node {
        String key;
        Node next;
        Node(String key) { this.key = key; }
    }

    private Node[] table;
    private int tableSize;
    private int soLuong;
    private static final double NGUONG_TAI = 0.75;

    public BangBam() {
        this(16);
    }

    public BangBam(int size) {
        this.tableSize = size;
        this.table = new Node[tableSize];
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


    public void them(String giaTri) {
        if (coTonTai(giaTri)) return; // khong them trung
        if ((double) (soLuong + 1) / tableSize > NGUONG_TAI) {
            resize(tableSize * 2);
        }
        int idx = hash(giaTri);
        Node newNode = new Node(giaTri);
        newNode.next = table[idx];
        table[idx] = newNode;
        soLuong++;
    }


    public boolean coTonTai(String giaTri) {
        int idx = hash(giaTri);
        Node cur = table[idx];
        while (cur != null) {
            if (cur.key.equals(giaTri)) return true;
            cur = cur.next;
        }
        return false;
    }


    public boolean xoa(String giaTri) {
        int idx = hash(giaTri);
        Node cur = table[idx];
        Node prev = null;
        while (cur != null) {
            if (cur.key.equals(giaTri)) {
                if (prev == null) table[idx] = cur.next;
                else prev.next = cur.next;
                soLuong--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    public int size() {
        return soLuong;
    }


    public String[] toArray() {
        String[] ketQua = new String[soLuong];
        int k = 0;
        for (Node dau : table) {
            Node cur = dau;
            while (cur != null) {
                ketQua[k++] = cur.key;
                cur = cur.next;
            }
        }
        return ketQua;
    }

    private void resize(int newSize) {
        Node[] oldTable = table;
        this.tableSize = newSize;
        this.table = new Node[tableSize];
        this.soLuong = 0;
        for (Node dau : oldTable) {
            Node cur = dau;
            while (cur != null) {
                them(cur.key);
                cur = cur.next;
            }
        }
    }
}
