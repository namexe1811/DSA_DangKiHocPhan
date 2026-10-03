import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Đọc file môn học
        MonHocData monHocData = mapper.readValue(new File("resources/DuLieuMonhoc.json"), MonHocData.class);
        TableMonHoc tableMonHoc = new TableMonHoc(monHocData.getSoMonHoc(), monHocData.getDanhSachMonHoc());

        // Đọc file sinh viên
        SinhVienData svData = mapper.readValue(new File("resources/DuLieuSinhVien.json"), SinhVienData.class);
        TableSinhVien tableSinhVien = new TableSinhVien(svData.getSoLuongSinhVien(), svData.getDanhSachSinhVien());

        // Thử truy vấn
        MonHoc mh = tableMonHoc.get("DSTT2025");
        System.out.println("Môn học: " + mh.getTenMonHoc());

        SinhVien sv = tableSinhVien.get(25110273);
        System.out.println("Sinh viên: " + sv.getTen());

        // --- Ghi lại bảng đã xử lý ---
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("resources/DuLieuMonhoc.json"), tableMonHoc);

        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("resources/DuLieuSinhVien.json"), tableSinhVien);

        System.out.println("Đã ghi bảng đã xử lý ra file JSON.");
    }
}