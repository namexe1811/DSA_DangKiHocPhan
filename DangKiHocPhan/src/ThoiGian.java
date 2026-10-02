/*Mô tả ngắn:
-Struct ThoiGian: 1 buổi học của lớp gồm thứ, tiết đầu, tiết cuối
-Quy ước đợt 1: thứ 2 là 0, rồi tăng dần lên
-Quy ước đợt 2: thứ 2 là 7
-Hàm trung(ThoiGian o): kiểm tra 2 buổi học có trùng giờ ko (cùng thứ và các tiết giao nhau)
*/
public class ThoiGian {
    private int thu;
    private int tietDau;
    private int tietCuoi;

    public ThoiGian(int thu, int tietDau, int tietCuoi) {
        this.thu = thu;
        this.tietDau = tietDau;
        this.tietCuoi = tietCuoi;
    }

    public int getThu() { return thu; }
    public int getTietDau() { return tietDau; }
    public int getTietCuoi() { return tietCuoi; }

    public boolean trung(ThoiGian o) {
        return thu == o.thu && tietDau <= o.tietCuoi && o.tietDau <= tietCuoi;
    }
}
