public class TableMonDaDangKi {

    private static class Node {
        String key;
        Node next;

        Node(String key) {
            this.key = key;
        }
    }

    private Node[] table;
    private int tableSize;
    private int soLuong;

    public TableMonDaDangKi() {
        this(16);
    }

    public TableMonDaDangKi(int size) {
        this.tableSize = size;
        this.table = new Node[tableSize];
        this.soLuong = 0;
    }

    // Hàm băm: chuyển tên môn thành vị trí trong bảng
    private int hash(String key) {
        long h = 0;
        int p = 31;

        for (int i = 0; i < key.length(); i++) {
            h = h * p + key.charAt(i);
        }

        return (int) (Math.abs(h) % tableSize);
    }

    // Thêm môn
    public void push(String giaTri) {
        if (coTonTai(giaTri)) {
            return;
        }

        int idx = hash(giaTri);

        Node newNode = new Node(giaTri);
        newNode.next = table[idx];
        table[idx] = newNode;

        soLuong++;
    }

    // Kiểm tra môn đã tồn tại chưa
    public boolean coTonTai(String giaTri) {
        int idx = hash(giaTri);

        Node cur = table[idx];

        while (cur != null) {
            if (cur.key.equals(giaTri)) {
                return true;
            }

            cur = cur.next;
        }

        return false;
    }

    // Xóa môn
    public boolean xoa(String giaTri) {
        int idx = hash(giaTri);

        Node cur = table[idx];
        Node prev = null;

        while (cur != null) {
            if (cur.key.equals(giaTri)) {

                if (prev == null) {
                    table[idx] = cur.next;
                } else {
                    prev.next = cur.next;
                }

                soLuong--;
                return true;
            }

            prev = cur;
            cur = cur.next;
        }

        return false;
    }

    // Số lượng môn
    public int size() {
        return soLuong;
    }

    // Chuyển toàn bộ bảng băm thành mảng
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
}