/* Mô tả: Để làm file đọc*/
public class MonHocData {
    private int soTinChi;
    private String tenMonHoc;
    private String maHocPhan;
    ///
    private String[] nganh; 
    private String[] monHocTruoc; 
    private String[] monTienQuyet; 
    
    public MonHocData(int soTinChi, String tenMonHoc, String maHocPhan, 
                      String[] nganh, String[] monHocTruoc, String[] monTienQuyet) {
        this.soTinChi = soTinChi;
        this.tenMonHoc = tenMonHoc;
        this.maHocPhan = maHocPhan;
        this.nganh = nganh;
        this.monHocTruoc = monHocTruoc;
        this.monTienQuyet = monTienQuyet;
    }

    public int getSoTinChi() { return soTinChi; }
    public String getTenMonHoc() { return tenMonHoc; }
    public String getMaHocPhan() { return maHocPhan; }
    public String[] getNganh() { return nganh; }
    public String[] getMonHocTruoc() { return monHocTruoc; }
    public String[] getMonTienQuyet() { return monTienQuyet; }
}
