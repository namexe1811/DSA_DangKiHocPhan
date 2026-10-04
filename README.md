# DSA_DangKiHocPhan
Đồ án DSA
# DSA_DangKiHocPhan

Project quản lý đăng ký học phần được xây dựng bằng **Java + Maven + JavaFX**.

Tài liệu này hướng dẫn cách tải và chạy project bằng **Visual Studio Code** hoặc **IntelliJ IDEA**.

---

## 1. Yêu cầu

Máy tính cần cài:

- **JDK 21**
- **Visual Studio Code** hoặc **IntelliJ IDEA**

Không cần cài riêng:

- JavaFX SDK
- Jackson
- Các file `.jar`

Maven sẽ tự động tải các thư viện được khai báo trong `pom.xml`.

> Máy tính cần có kết nối Internet trong lần đầu mở project để Maven tải các thư viện.

---

## 2. Cài JDK 21

Project sử dụng JavaFX 21 nên cần sử dụng **JDK 21**.

Có thể tải JDK 21 tại:

https://adoptium.net/

Sau khi cài đặt, mở Terminal hoặc Command Prompt và kiểm tra:

```bash
java -version
```

Sau đó:

```bash
javac -version
```

Kết quả cần hiển thị phiên bản Java 21, ví dụ:

```text
java version "21.x.x"
```

và:

```text
javac 21.x.x
```

Nếu hai lệnh trên hoạt động bình thường thì JDK đã được cài đặt thành công.

Nếu máy không nhận lệnh `java` hoặc `javac`, hãy kiểm tra lại việc cài đặt JDK hoặc biến môi trường `PATH`.

---

## 3. Tải project

Tải project `DSA_DangKiHocPhan` về máy.

Nếu project được tải dưới dạng `.zip`, hãy giải nén trước khi mở.

Khi mở project bằng VS Code hoặc IntelliJ IDEA, cần mở **thư mục gốc của project**, tức là thư mục chứa file:

`pom.xml`

Không mở riêng thư mục `src`.

---

# 4. Chạy project bằng Visual Studio Code

## 4.1. Cài Visual Studio Code

Tải Visual Studio Code tại:

https://code.visualstudio.com/

Cài đặt Visual Studio Code như bình thường.

---

## 4.2. Cài Extension Java

Mở Visual Studio Code.

Nhấn:

`Ctrl + Shift + X`

để mở Extensions.

Tìm:

**Extension Pack for Java**

và cài extension của **Microsoft**.

---

## 4.3. Mở project

Trong VS Code chọn:

**File → Open Folder...**

Sau đó chọn thư mục:

`DSA_DangKiHocPhan`

Lưu ý: phải mở **thư mục gốc của project**, là thư mục chứa file `pom.xml`.

Không mở riêng thư mục `src`.

---

## 4.4. Chờ Maven tải thư viện

Sau khi mở project lần đầu, Maven sẽ tự động tải các thư viện được khai báo trong `pom.xml`.

Project sử dụng:

- JavaFX Controls 21
- JavaFX FXML 21
- Jackson Databind 2.17.2

Lần đầu mở project cần có kết nối Internet để Maven tải các dependency.

> Không cần tự tải JavaFX SDK hoặc các file `.jar`.

---

## 4.5. Kiểm tra JDK trong VS Code

Nếu VS Code không nhận JDK hoặc báo lỗi Java:

Nhấn:

`Ctrl + Shift + P`

Tìm:

**Java: Configure Java Runtime**

Kiểm tra và đảm bảo VS Code đang sử dụng:

**JDK 21**

---

## 4.6. Chạy project

Project đã có cấu hình chạy trong thư mục `.vscode`.

Để chạy project, **không sử dụng nút `Run Java` xuất hiện phía trên `main()`**.

Thay vào đó:

1. Mở project trong VS Code.
2. Nhấn:

   `F5`

   hoặc mở:

   **Run and Debug**

3. Chọn cấu hình chạy của project nếu VS Code yêu cầu.
4. Nhấn:

   **Start Debugging**

VS Code sẽ sử dụng cấu hình trong:

`.vscode/launch.json`

để khởi chạy ứng dụng JavaFX.

> **Lưu ý:** Với project này, hãy chạy bằng **F5 / Run and Debug**, không chạy bằng nút **Run Java** trong `App.java`.
# 5. Chạy project bằng IntelliJ IDEA

## 5.1. Cài IntelliJ IDEA

Tải IntelliJ IDEA tại:

https://www.jetbrains.com/idea/

Cài đặt IntelliJ IDEA như bình thường.

---

## 5.2. Mở project

Mở IntelliJ IDEA.

Chọn:

**Open**

Sau đó chọn thư mục:

`DSA_DangKiHocPhan`

Lưu ý: phải chọn thư mục gốc chứa file `pom.xml`.

---

## 5.3. Import Maven project

IntelliJ sẽ tự nhận diện file:

`pom.xml`

Nếu IntelliJ hỏi có muốn load Maven project hay không, chọn:

**Load Maven Project**

Nếu IntelliJ hỏi có tin cậy project hay không, chọn:

**Trust Project**

Sau đó chờ IntelliJ tải các dependency.

---

# 6. Cấu hình JDK 21 trong IntelliJ IDEA

Trong IntelliJ chọn:

**File → Project Structure**

Chọn:

**Project**

Thiết lập:

**Project SDK: JDK 21**

và:

**Language level: 21**

Nếu chưa có JDK 21, chọn:

**Add SDK → JDK**

Sau đó chọn thư mục JDK 21 đã cài trên máy.

---

# 7. Chạy chương trình trong IntelliJ IDEA

Mở file:

`App.java`

Tìm phương thức:

```java
public static void main(String[] args)
```

Nhấn biểu tượng **▶** ở bên trái phương thức `main`.

Chọn:

**Run 'App.main()'**

Chương trình sẽ được khởi chạy.

---

# 8. Maven Dependency

Project sử dụng Maven để quản lý các thư viện.

Các dependency chính được khai báo trong `pom.xml` gồm:

### Jackson

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.2</version>
</dependency>
```

### JavaFX Controls

```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>21</version>
</dependency>
```

### JavaFX FXML

```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>21</version>
</dependency>
```

Người dùng **không cần tự tải hoặc cài đặt các thư viện trên**.

Maven sẽ tự động tải chúng khi project được import.

---

# 9. Nếu Maven chưa tải dependency

## VS Code

Nhấn:

`Ctrl + Shift + P`

Sau đó tìm các lệnh liên quan đến Maven và thực hiện reload/update Maven project.

Có thể mở:

**View → Output**

để kiểm tra quá trình Maven tải thư viện.

---

## IntelliJ IDEA

Mở:

**View → Tool Windows → Maven**

Sau đó nhấn:

**Reload All Maven Projects**

Chờ Maven hoàn thành quá trình tải dependency.

---

# 10. Nếu VS Code không nhận Java

Kiểm tra JDK bằng:

```bash
java -version
```

và:

```bash
javac -version
```

Nếu chưa nhận JDK, nhấn:

`Ctrl + Shift + P`

và chọn:

**Java: Configure Java Runtime**

Đảm bảo đang sử dụng **JDK 21**.

Sau đó khởi động lại VS Code.

---

# 11. Nếu IntelliJ báo không có SDK

Vào:

**File → Project Structure → Project**

Đặt:

**Project SDK: JDK 21**

Nếu chưa có JDK 21, cài JDK 21 rồi quay lại cấu hình.

---

# 12. Nếu gặp lỗi JavaFX

Nếu xuất hiện lỗi:

```text
JavaFX runtime components are missing
```

hãy kiểm tra:

1. Đã cài JDK 21.
2. IDE đang sử dụng JDK 21.
3. Maven project đã được import.
4. Maven đã tải JavaFX dependency.
5. Không chạy file `.class` hoặc `.jar` thủ công.
6. Thử reload Maven project rồi chạy lại `App.java`.

Không cần tải JavaFX SDK riêng nếu Maven đã tải thành công các dependency JavaFX trong `pom.xml`.

---

# 13. Nếu chương trình không tìm thấy file JSON

Project sử dụng các file dữ liệu trong thư mục `resources`.

Nếu chương trình báo không tìm thấy:

`DuLieuMonHoc.json`

hoặc:

`DuLieuSinhVien.json`

hãy kiểm tra xem các file tài nguyên của project có đầy đủ hay không.

Không được tự ý xóa hoặc di chuyển các file này.

Không chỉ copy các file `.java` để chạy project. Cần tải **toàn bộ project**.

---

# 14. Lưu ý khi chia sẻ project

Không cần chia sẻ thư mục `target` vì đây là thư mục được Maven tự động tạo ra trong quá trình build.

Người dùng chỉ cần tải đầy đủ project.

Không cần tự tạo thư mục `target`.

Maven sẽ tự động tạo lại thư mục này khi project được build.

---

# 15. Checklist

Trước khi chạy project, hãy đảm bảo:

- [ ] Đã cài JDK 21
- [ ] `java -version` hiển thị Java 21
- [ ] Đã cài VS Code hoặc IntelliJ IDEA
- [ ] Nếu dùng VS Code: đã cài Extension Pack for Java
- [ ] Đã mở đúng thư mục gốc của project
- [ ] IDE nhận diện file `pom.xml`
- [ ] Maven đã import project
- [ ] Maven đã tải dependency
- [ ] IDE đang sử dụng JDK 21
- [ ] Các file tài nguyên của project vẫn đầy đủ
- [ ] Đã mở `App.java`
- [ ] Đã chạy chương trình bằng nút Run

---

# 16. Tóm tắt

Để chạy `DSA_DangKiHocPhan`, người dùng chỉ cần:

**Bước 1:** Cài JDK 21.

**Bước 2:** Cài VS Code hoặc IntelliJ IDEA.

**Bước 3:** Tải project `DSA_DangKiHocPhan`.

**Bước 4:** Mở thư mục project bằng IDE.

**Bước 5:** Chờ Maven tự động tải các thư viện.

**Bước 6:** Đảm bảo IDE đang sử dụng JDK 21.

**Bước 7:** Mở `App.java`.

**Bước 8:** Nhấn **Run**.

> **Không cần cài JavaFX SDK riêng.** JavaFX và các thư viện khác sẽ được Maven tự động quản lý thông qua `pom.xml`.
Hướng dẫn test:
Kịch bản 1: Chuyển lớp học phần trong cùng 1 môn học (Thành công)
# Kịch bản kiểm thử hệ thống đăng ký học phần

## Kịch bản 1: Chuyển lớp học phần trong cùng 1 môn học (Thành công)
**Mục tiêu:** Kiểm tra tính năng đổi lớp học phần khi sinh viên đã đăng ký môn đó.

- **Tài khoản đăng nhập:**
  - MSSV: `25110001`
  - Mật khẩu: `12345678` (Tran Hao Nam)

- **Trạng thái ban đầu:**
  - Đang học môn *Đại số tuyến tính* ở lớp `DSTT2025001` (Thứ 0, Thứ 2 - Tiết 0-3).

- **Thao tác:**
  - Chuyển sang lớp `DSTT2025002` (cùng môn `DSTT2025`, lịch học: Thứ 7, Thứ 9 - Tiết 0-3).

- **Kết quả kỳ vọng:**
  - Chuyển lớp thành công.
  - `monDaDangKi`: bỏ `DSTT2025001`, thêm `DSTT2025002`.
  - Sĩ số lớp `DSTT2025001` giảm từ 3 → 2.
  - Sĩ số lớp `DSTT2025002` tăng từ 2 → 3.
  - Thời khóa biểu cập nhật đúng các ô Thứ 7, 9.

---

## Kịch bản 2: Đăng ký môn học mới thành công (Đủ điều kiện tiên quyết + Không trùng lịch)
**Mục tiêu:** Đăng ký thêm môn học mới hợp lệ.

- **Tài khoản đăng nhập:**
  - MSSV: `25110004`
  - Mật khẩu: `dung2025` (Pham Quoc Dung)

- **Trạng thái ban đầu:**
  - Đã tích lũy môn `LTCB1001`.
  - Lịch hiện tại: Thứ 4 (Tiết 0-9).

- **Thao tác:**
  - Đăng ký môn *Lập trình hướng đối tượng* (`OOP2030`), lớp `OOP2030001` (Thứ 12 - Tiết 0-4).

- **Kết quả kỳ vọng:**
  - Đăng ký thành công (thỏa môn học trước `LTCB1001`, lịch Thứ 12 trống).
  - `soTinDangKi`: tăng từ 6 → 9 tín chỉ.

---

## Kịch bản 3: Bị chặn do trùng lịch học
**Mục tiêu:** Kiểm tra thuật toán phát hiện xung đột thời khóa biểu.

- **Tài khoản đăng nhập:**
  - MSSV: `25110002`
  - Mật khẩu: `abcdef12` (Nguyen Van Binh)

- **Trạng thái ban đầu:**
  - Đang học lớp `LTCB1001001` (Thứ 1, Thứ 3 - Tiết 0-3).

- **Thao tác:**
  - Thử đăng ký lớp