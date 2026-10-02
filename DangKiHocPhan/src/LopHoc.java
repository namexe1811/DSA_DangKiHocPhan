
public class LopHoc {
    private static class Node {
        int mssv;
        Node next;
        Node(int mssv, Node next) {
            this.mssv = mssv;
            this.next = next;
        }
    }

    private String maLopHoc;
    private String tenGiaoVien;
    private String maHocPhan;
    private ThoiGian[] thoiGianHoc;
    private int siSoToiDa;
    private int soLuongDaDangKi;
    private Node[] danhSachSinhVien;//bảng băm chứa MSSV

    public LopHoc(String maLopHoc, String tenGiaoVien, String maHocPhan,
                  ThoiGian[] thoiGianHoc, int siSoToiDa) {
        this.maLopHoc = maLopHoc;
        this.tenGiaoVien = tenGiaoVien;
        this.maHocPhan = maHocPhan;
        this.thoiGianHoc = thoiGianHoc;
        this.siSoToiDa = siSoToiDa;
        this.soLuongDaDangKi = 0;
        this.danhSachSinhVien = new Node[SoNguyenTo(siSoToiDa)];//GHI THÊM HÀM ĐỌC SINH VIÊN TỪ MẢNG VÀO HASHTABLE
    }

    public String getmaLopHoc() { return maLopHoc; }
    public String gettenGiaoVien() { return tenGiaoVien; }
    public String getmaHocPhan() { return maHocPhan; }
    public ThoiGian[] getthoiGianHoc() { return thoiGianHoc; }
    public int getsiSoToiDa() { return siSoToiDa; }
    public int getsoLuongDangKi() { return soLuongDaDangKi; }

    public boolean isFull() {
        return soLuongDaDangKi>=siSoToiDa;
    }

    //key
    private int hash(int mssv) {
        return (int) (Math.abs((long) mssv) % danhSachSinhVien.length);
    }

    private static int SoNguyenTo(int n) {
        if (n<2) n=2;
        while (true) {
            boolean ok=true;
            for (int i=2; (long) i * i <= n; i++) {
                if (n%i==0) { ok=false; break; }
            }
            if (ok) return n;
            n++;
        }
    }

    public boolean coSinhVien(int MSSV) {
        for (Node p=danhSachSinhVien[hash(MSSV)]; p!= null; p=p.next) {
            if (p.mssv == MSSV) return true;
        }
        return false;
    }

    public boolean themSinhVien(int MSSV) {
        if (isFull() || coSinhVien(MSSV)) return false;
        int idx=hash(MSSV);
        danhSachSinhVien[idx] = new Node(MSSV, danhSachSinhVien[idx]);
        soLuongDaDangKi++;
        return true;
    }

    public boolean xoaSinhVien(int MSSV) {
        int idx = hash(MSSV);
        Node prev = null;
        for (Node p = danhSachSinhVien[idx]; p!= null; prev=p, p=p.next) {
            if (p.mssv == MSSV) {
                if (prev == null) danhSachSinhVien[idx] = p.next;
                else prev.next = p.next;
                soLuongDaDangKi--;
                return true;
            }
        }
        return false;
    }
    //danh sách MSSV của lớp
    public int[] getdanhSachSinhVien() {
        int[] kq = new int[soLuongDaDangKi];
        int k=0;
        for (Node head : danhSachSinhVien) {
            for (Node p=head; p!=null; p=p.next) kq[k++] = p.mssv;
        }
        return kq;
    }

    //lớp này có trùng giờ với lớp khác ko (dùng khi đăng kí lớp)
    public boolean trungThoiGian(LopHoc o) {
        for (ThoiGian a : thoiGianHoc)
            for (ThoiGian b : o.thoiGianHoc)
                if (a.trung(b)) return true;
        return false;
    }
}
