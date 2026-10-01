public class TableSinhVien {
    private int size;//số lượng sinh viên
    private SinhVien[] danhSachSinhVien;//danh sách sinh viên
    public TableSinhVien(int size){
        this.size=size;
        this.danhSachSinhVien = new SinhVien[size];
    }
    public int getsize(){
        return size;
    }
    private int hash(String key) {
        //phần mã hash của số
        int numPart = 0;
        String digits = key.replaceAll("\\D", "");//bỏ kí tự ko phải số
        if (!digits.isEmpty()) {
            numPart = Integer.parseInt(digits);
        }
        //phần chữ
        int charPart = 0;
        for (char c : key.toCharArray()) {
            if (Character.isLetter(c)) {
                charPart += c; // cộng mã ASCII của chữ
            }
        }

        return (numPart + charPart) % size;
    }

}
