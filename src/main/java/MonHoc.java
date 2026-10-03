/*
 */
public class MonHoc {
    private int soTinChi;
    private String tenMonHoc;
    private String maHocPhan;
    ///
    private String[] nganh; 
    private LopHoc[] cacLopHoc; 
    private String[] monHocTruoc; 
    private String[] monTienQuyet; 

    private int soLuongLopHienTai = 0;

    public MonHoc(int soTinChi, String tenMonHoc, String maHocPhan, 
                  String[] nganh, String[] monHocTruoc, String[] monTienQuyet, int soLuongLopToiDa, LopHoc[] cacLopHoc) {
        this.soTinChi = soTinChi;
        this.tenMonHoc = tenMonHoc;
        this.maHocPhan = maHocPhan;

        // Khởi tạo Bảng băm với size x2 
        int sizeNganh = (nganh != null) ? nganh.length : 0;
        this.nganh = new String[SoNguyenTo(sizeNganh > 0 ? sizeNganh * 2 : 7)];
        if (nganh != null) {
            for (String n : nganh) this.themVaoBangBamChuoi(this.nganh, n);
        }

        int sizeMHT = (monHocTruoc != null) ? monHocTruoc.length : 0;
        this.monHocTruoc = new String[SoNguyenTo(sizeMHT > 0 ? sizeMHT * 2 : 7)];
        if (monHocTruoc != null) {
            for (String m : monHocTruoc) this.themVaoBangBamChuoi(this.monHocTruoc, m);
        }

        int sizeMTQ = (monTienQuyet != null) ? monTienQuyet.length : 0;
        this.monTienQuyet = new String[SoNguyenTo(sizeMTQ > 0 ? sizeMTQ * 2 : 7)];
        if (monTienQuyet != null) {
            for (String m : monTienQuyet) this.themVaoBangBamChuoi(this.monTienQuyet, m);
        }

        this.cacLopHoc = new LopHoc[SoNguyenTo(soLuongLopToiDa > 0 ? soLuongLopToiDa * 2 : 7)];
        if (cacLopHoc != null) { // Kiểm tra xem mảng cacLopHoc có bị rỗng không
            for(LopHoc lop : cacLopHoc) {
                if (lop != null) { 
                    this.themLop(lop);
                    // Kiểm tra phần tử bên trong có bị rỗng không
                }
            }
        }
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

    private int hash(String key, int size) {
        long h = 0;
        int p = 31;
        for (int i = 0; i < key.length(); i++) {
            h = h * p + key.charAt(i);
        }
        return (int)(Math.abs(h) % size);
    }

    private boolean themVaoBangBamChuoi(String[] bang, String giaTri) {
        if (giaTri == null) return false;
        int idx = hash(giaTri, bang.length);
        int startIdx = idx;
        
        while (bang[idx] != null) {
            if (bang[idx].equals(giaTri)) return false;
            idx = (idx + 1) % bang.length;
            if (idx == startIdx) return false; 
        }
        bang[idx] = giaTri;
        return true;
    }

    private boolean tonTaiTrongBangBamChuoi(String[] bang, String giaTri) {
        if (giaTri == null) return false;
        int idx = hash(giaTri, bang.length);
        int startIdx = idx;
        
        while (bang[idx] != null) {
            if (bang[idx].equals(giaTri)) return true;
            idx = (idx + 1) % bang.length;
            if (idx == startIdx) break;
        }
        return false;
    }

    public boolean themLop(LopHoc lopHocMoi) {
        if (lopHocMoi == null) return false;
        String key = lopHocMoi.getmaLopHoc();
        int idx = hash(key, this.cacLopHoc.length);
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
        this.soLuongLopHienTai++;
        return true;
    }
    
    public LopHoc getLop(String maLopHoc) {
        if (maLopHoc == null) return null;
        int idx = hash(maLopHoc, this.cacLopHoc.length);
        int startIdx = idx;
        
        while (this.cacLopHoc[idx] != null) {
            if (this.cacLopHoc[idx].getmaLopHoc().equals(maLopHoc)) {
                return this.cacLopHoc[idx];
            }
            idx = (idx + 1) % this.cacLopHoc.length;
            if (idx == startIdx) break;
        }
        return null;
    }

    public boolean laMonDaiCuong() {
        return tonTaiTrongBangBamChuoi(this.nganh, "ALL");
    }

    public boolean thuocNganh(String tenNganh) {
        return tonTaiTrongBangBamChuoi(this.nganh, tenNganh);
    }

    public boolean coMonTienQuyet(String maMon) {
        return tonTaiTrongBangBamChuoi(this.monTienQuyet, maMon);
    }

    public String getTenMonHoc() {return this.tenMonHoc;}
    public String getMaHocPhan() {return this.maHocPhan;}
    public int getSoTinChi() {return this.soTinChi;}
    public String[] getNganh() {return this.nganh;}
    public String[] getMonTienQuyet() {return this.monTienQuyet;}
    public String[] getMonHocTruoc() {return this.monHocTruoc;}
    public LopHoc[] getCacLopHoc() {return this.cacLopHoc;}
}
