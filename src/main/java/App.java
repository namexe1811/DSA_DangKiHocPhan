import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, JSON!");

        ObjectMapper mapper = new ObjectMapper();

        // Đọc file môn học
        TableMonHoc tableMonHoc = mapper.readValue(
            new File("src/main/resources/DuLieuMonhoc.json"), TableMonHoc.class);

        // Đọc file sinh viên
        TableSinhVien tableSinhVien = mapper.readValue(
            new File("src/main/resources/DuLieuSinhVien.json"), TableSinhVien.class);

        // Thử truy vấn
        MonHoc mh = tableMonHoc.get("DSTT2025");
        System.out.println("Mon hoc: " + mh.gettenMonHoc());

        SinhVien sv = tableSinhVien.get(25110001);
        System.out.println("Sinh vien: " + sv.getten());

        if (sv.dangKiLop(tableMonHoc.get("LTCB1001"), "LTCB1001001")) {
            System.out.println("Dang ki thanh cong");
        } else {
            System.out.println("Dang ki that bai");
        }

        // --- Ghi lại bảng đã xử lý với pretty print ---
        DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
        // ép Jackson xuống dòng cho mảng
        DefaultIndenter indenter = new DefaultIndenter("  ", System.lineSeparator());
        pp.indentArraysWith(indenter);

        mapper.writer(pp)
              .writeValue(new File("src/main/resources/DuLieuMonhoc.json"), tableMonHoc);

        mapper.writer(pp)
              .writeValue(new File("src/main/resources/DuLieuSinhVien.json"), tableSinhVien);

        System.out.println("Da ghi lai du lieu vao file DuLieuMonhoc.json va DuLieuSinhVien.json");
    }
}
