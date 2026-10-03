public class MangDong {

    private String[] data;
    private int soLuong;
    private int sucChua;

    public MangDong() {
        this(3);
    }

    public MangDong(int sucChuaBanDau) {
        this.sucChua = Math.max(sucChuaBanDau, 1);
        this.data = new String[sucChua];
        this.soLuong = 0;
    }

    public int size() {
        return soLuong;
    }

    public boolean isEmpty() {
        return soLuong == 0;
    }

    public void them(String giaTri) {
        if (soLuong == sucChua) {
            capPhatLai(sucChua * 2); // day thi tang gap doi (2n)
        }
        data[soLuong] = giaTri;
        soLuong++;
    }

    public int timViTri(String giaTri) {
        for (int i = 0; i < soLuong; i++) {
            if (data[i].equals(giaTri)) return i;
        }
        return -1;
    }

    public boolean coTonTai(String giaTri) {
        return timViTri(giaTri) != -1;
    }

    public boolean xoa(String giaTri) {
        int idx = timViTri(giaTri);
        if (idx == -1) return false;
        for (int i = idx; i < soLuong - 1; i++) {
            data[i] = data[i + 1];
        }
        data[soLuong - 1] = null;
        soLuong--;
        return true;
    }

    public String get(int index) {
        if (index < 0 || index >= soLuong) {
            throw new IndexOutOfBoundsException("Vi tri khong hop le: " + index);
        }
        return data[index];
    }

    private void capPhatLai(int sucChuaMoi) {
        String[] mangMoi = new String[sucChuaMoi];
        for (int i = 0; i < soLuong; i++) {
            mangMoi[i] = data[i];
        }
        data = mangMoi;
        sucChua = sucChuaMoi;
    }

    public String[] toArray() {
        String[] ketQua = new String[soLuong];
        for (int i = 0; i < soLuong; i++) {
            ketQua[i] = data[i];
        }
        return ketQua;
    }
}