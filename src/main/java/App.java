import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, JSON!");
         
        ObjectMapper mapper = new ObjectMapper();

        // Đọc file môn học
        TableMonHoc tableMonHoc = mapper.readValue(new File("/DuLieuMonhoc.json"), TableMonHoc.class);

        // Đọc file sinh viên
        TableSinhVien tableSinhVien = mapper.readValue(new File("/DuLieuSinhVien.json"), TableSinhVien.class);

        // Thử truy vấn
        MonHoc mh = tableMonHoc.get("DSTT2025");
        System.out.println("Môn học: " + mh.getTenMonHoc());

        SinhVien sv = tableSinhVien.get(25110273);
        System.out.println("Sinh viên: " + sv.getTen());

        // --- Ghi lại bảng đã xử lý ---
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("/DuLieuMonhoc.json"), tableMonHoc);

        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("/DuLieuSinhVien.json"), tableSinhVien);

        System.out.println("Đã ghi bảng đã xử lý ra file JSON.");
        
    }
}