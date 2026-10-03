import java.util.ArrayList;
import java.util.List;

public class TableMonDaDangKi {

    private static class Node {
        String key;
        Node next;
        Node(String key) { this.key = key; }
    }

    private Node[] danhSachMonDaDangKi;   
    private int soLuong;          // số phần tử hiện tại
    //khởi tạo hashtable từ 1 mảng
    public TableMonDaDangKi(String[] cacMonDaDangKi) {
        this.danhSachMonDaDangKi = new Node[16];
        this.soLuong = 0;
        for (String mon : cacMonDaDangKi) {
            if (mon != null && !mon.isEmpty()) {
                them(mon);//mỗi lần thêm thì soLuong tăng lên(trong hàm them())
            }
        }
    }

    // Hàm băm
    private int hash(String key) {
        long h = 0;
        int p = 31;
        for (int i = 0; i < key.length(); i++) {
            h = h * p + key.charAt(i);
        }
        return (int) (Math.abs(h) % danhSachMonDaDangKi.length);//danhSachMonDaDangKi.length là 16
    }

    // Thêm môn
    public void them(String giaTri) {
        int idx = hash(giaTri);
        Node cur = danhSachMonDaDangKi[idx];
        while (cur != null) {
            if (cur.key.equals(giaTri)) return; // đã tồn tại
            cur = cur.next;
        }
        Node newNode = new Node(giaTri);
        newNode.next = danhSachMonDaDangKi[idx];
        danhSachMonDaDangKi[idx] = newNode;
        soLuong++;
    }

    // Xóa môn
    public boolean xoa(String giaTri) {
        int idx = hash(giaTri);
        Node cur = danhSachMonDaDangKi[idx], prev = null;
        while (cur != null) {
            if (cur.key.equals(giaTri)) {
                if (prev == null) danhSachMonDaDangKi[idx] = cur.next;
                else prev.next = cur.next;
                soLuong--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    // Kiểm tra tồn tại
    public boolean tonTai(String giaTri) {
        int idx = hash(giaTri);
        Node cur = danhSachMonDaDangKi[idx];
        while (cur != null) {
            if (cur.key.equals(giaTri)) return true;
            cur = cur.next;
        }
        return false;
    }

    // Số lượng môn
    public int size() { return soLuong; }

    // Getter trả về mảng để ghi file
    public String[] getDanhSachMonDaDangKi() {
        String[] kq = new String[soLuong];
        int k = 0;
        for (Node head : danhSachMonDaDangKi) {
            Node cur = head;
            while (cur != null) {
                kq[k++] = cur.key;
                cur = cur.next;
            }
        }
        return kq;
    }
}

