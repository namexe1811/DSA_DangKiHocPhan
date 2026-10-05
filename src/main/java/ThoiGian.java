/*Mô tả ngắn:
-Struct ThoiGian: 1 buổi học của lớp gồm thứ, tiết đầu, tiết cuối
-Quy ước đợt 1: thứ 2 là 0, rồi tăng dần lên
-Quy ước đợt 2: thứ 2 là 7
*/

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ThoiGian {
    private int thu;
    private int tietDau;
    private int tietCuoi;
    @JsonCreator
    public ThoiGian(@JsonProperty("thu") int thu,
                    @JsonProperty("tietDau") int tietDau,
                    @JsonProperty("tietCuoi") int tietCuoi) {
        this.thu = thu;
        this.tietDau = tietDau;
        this.tietCuoi = tietCuoi;
    }

    public int getthu() { return thu; }
    public int gettietDau() { return tietDau; }
    public int gettietCuoi() { return tietCuoi; }

}
