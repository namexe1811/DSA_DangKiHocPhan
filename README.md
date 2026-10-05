# DSA_DangKiHocPhan

Đồ án DSA – Hệ thống quản lý đăng ký học phần.

---

# 1. Yêu cầu

Máy tính cần cài:

* **JDK 21**
* **Visual Studio Code** hoặc **IntelliJ IDEA**
* Kết nối Internet trong lần đầu mở project để Maven tải các thư viện.

Không cần cài riêng:

* JavaFX SDK
* Jackson
* Các file `.jar`

Maven sẽ tự động tải các thư viện được khai báo trong `pom.xml`.

---

# 2. Cài JDK 21

Project sử dụng JavaFX 21 nên cần sử dụng **JDK 21**.

Có thể tải JDK 21 tại:

[Eclipse Adoptium](https://adoptium.net/?utm_source=chatgpt.com)

Sau khi cài đặt, mở Terminal hoặc Command Prompt và kiểm tra:

```bash
java -version
```

Sau đó:

```bash
javac -version
```

Kết quả cần hiển thị Java 21, ví dụ:

```text
java version "21.x.x"
```

và:

```text
javac 21.x.x
```

Nếu hai lệnh trên hoạt động bình thường thì JDK đã được cài đặt thành công.

---

# 3. Tải và mở project

Tải project `DSA_DangKiHocPhan` về máy.

Nếu project được tải dưới dạng `.zip`, hãy giải nén trước khi mở.

Khi mở project bằng VS Code hoặc IntelliJ IDEA, cần mở **thư mục gốc của project**, tức là thư mục chứa file:

```text
pom.xml
```

Không mở riêng thư mục `src`.

---

# 4. Chạy project bằng Visual Studio Code

## 4.1. Cài Visual Studio Code

Có thể tải Visual Studio Code tại:

[Visual Studio Code](https://code.visualstudio.com/?utm_source=chatgpt.com)

Sau khi cài đặt, mở Visual Studio Code.

---

## 4.2. Cài Extension Java

Nhấn:

```text
Ctrl + Shift + X
```

Tìm:

**Extension Pack for Java**

và cài extension của **Microsoft**.

---

## 4.3. Mở project

Trong VS Code chọn:

**File → Open Folder...**

Sau đó chọn thư mục:

```text
DSA_DangKiHocPhan
```

Lưu ý: phải mở thư mục gốc chứa file `pom.xml`.

---

## 4.4. Chờ Maven tải thư viện

Sau khi mở project lần đầu, Maven sẽ tự động tải các thư viện được khai báo trong `pom.xml`.

Project sử dụng một số thư viện chính như:

* JavaFX Controls 21
* JavaFX FXML 21
* Jackson Databind 2.17.2

Không cần tự tải các thư viện `.jar`.

---

# 5. Cách chạy chương trình

Project cần chạy theo **2 bước**.

## Bước 1: Chạy `Server.class`

Trong VS Code:

1. Mở file `Server.java`.
2. Tìm phần chạy chương trình của Server.
3. Chọn **`Server.class`**.
4. Nhấn **Run** để chạy Server.

Sau khi chạy thành công, **không đóng Terminal hoặc tiến trình đang chạy Server**.

Server cần được chạy nền để chương trình có thể hoạt động.

> **Lưu ý:** Phải để Server tiếp tục chạy trong nền trong suốt quá trình sử dụng chương trình.

---

## Bước 2: Chạy `Launch Play`

Sau khi Server đã chạy:
1. Tìm cấu hình hoặc file **`Launch Play`**.
2. Chọn **`Launch Play`**.
3. Nhấn **Run**.

Sau khi chạy thành công, chương trình sẽ tạo ra một **đường link**.

Ví dụ:

```text
http://localhost:xxxx
```

Copy đường link được chương trình cung cấp.

Sau đó mở trình duyệt như Chrome hoặc Edge và **dán đường link vào thanh địa chỉ**.

Nhấn Enter để mở giao diện hệ thống đăng ký học phần.

> **Lưu ý:** Không đóng chương trình Server trong Terminal. Nếu Server bị tắt, hệ thống có thể không hoạt động.

---

# 6. Đăng nhập hệ thống

Sau khi mở giao diện trên trình duyệt, người dùng có thể đăng nhập bằng tài khoản sinh viên.

Thông tin đăng nhập được quy định như sau:

### Tên tài khoản

Sử dụng:

```text
MSSV
```

Ví dụ:

```text
25110001
```

### Mật khẩu

Mật khẩu có dạng:

```text
mk + 4 số cuối MSSV
```

Trong đó:

* `mk` là chuỗi mật khẩu mặc định.
* `4 số cuối MSSV` là 4 chữ số cuối của mã số sinh viên.

Ví dụ:

MSSV:

```text
25110001
```

thì 4 số cuối là:

```text
0001
```

Mật khẩu:

```text
mk0001
```

Người dùng cần sử dụng đúng MSSV và mật khẩu tương ứng với dữ liệu sinh viên trong hệ thống.

---

# 7. Chạy project bằng IntelliJ IDEA

Có thể tải IntelliJ IDEA tại:

[JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/?utm_source=chatgpt.com)

## 7.1. Mở project

Mở IntelliJ IDEA.

Chọn:

**Open**

Sau đó chọn thư mục:

```text
DSA_DangKiHocPhan
```

Phải chọn thư mục gốc chứa:

```text
pom.xml
```

---

## 7.2. Import Maven

IntelliJ sẽ tự nhận diện file:

```text
pom.xml
```

Nếu IntelliJ hỏi có muốn load Maven project hay không, chọn:

**Load Maven Project**

Nếu hỏi có tin cậy project hay không, chọn:

**Trust Project**

Sau đó chờ Maven tải các dependency.

---

# 8. Cấu hình JDK 21 trong IntelliJ IDEA

Trong IntelliJ chọn:

**File → Project Structure**

Chọn:

**Project**

Thiết lập:

```text
Project SDK: JDK 21
Language level: 21
```

Nếu chưa có JDK 21, chọn:

**Add SDK → JDK**

Sau đó chọn thư mục JDK 21 đã cài trên máy.

---

# 9. Chạy project trong IntelliJ IDEA

Trong IntelliJ, project cũng cần chạy theo **2 bước**.

### Bước 1: Chạy Server

Chọn:

```text
Server.class
```

Sau đó nhấn:

**Run**

Để Server tiếp tục chạy nền.

### Bước 2: Chạy Launch Play

Sau khi Server đã chạy, chọn:

```text
Launch Play
```

Sau đó nhấn:

**Run**

Chương trình sẽ cung cấp một đường link.

Copy đường link đó và mở bằng trình duyệt.

Ví dụ:

```text
http://localhost:xxxx
```

---

# 10. Maven Dependency

Project sử dụng Maven để quản lý thư viện.

Các dependency chính gồm:
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

Người dùng **không cần tự tải các thư viện trên**.

Maven sẽ tự động tải chúng khi project được import.

---

# 11. Nếu Maven chưa tải dependency

## VS Code

Nhấn:

```text
Ctrl + Shift + P
```

Tìm các lệnh liên quan đến Maven để reload/update Maven project.

Có thể mở:

**View → Output**

để kiểm tra quá trình Maven tải thư viện.

## IntelliJ IDEA

Mở:

**View → Tool Windows → Maven**

Sau đó chọn:

**Reload All Maven Projects**

Chờ Maven hoàn thành quá trình tải dependency.

---

# 12. Nếu VS Code không nhận Java

Kiểm tra:

```bash
java -version
```

và:

```bash
javac -version
```

Nếu chưa nhận JDK, nhấn:

```text
Ctrl + Shift + P
```

Sau đó chọn:

**Java: Configure Java Runtime**

Đảm bảo VS Code đang sử dụng:

**JDK 21**

Sau đó khởi động lại VS Code.

---

# 13. Nếu IntelliJ không nhận SDK

Vào:

**File → Project Structure → Project**

Đặt:

```text
Project SDK: JDK 21
```

Nếu chưa có JDK 21, cài JDK 21 rồi quay lại cấu hình.

---

# 14. Nếu gặp lỗi JavaFX

Nếu xuất hiện:

```text
JavaFX runtime components are missing
```

hãy kiểm tra:

1. Đã cài JDK 21.
2. IDE đang sử dụng JDK 21.
3. Maven project đã được import.
4. Maven đã tải JavaFX dependency.
5. Không chạy file `.class` hoặc `.jar` thủ công.
6. Thử reload Maven project rồi chạy lại.

Không cần tải JavaFX SDK riêng nếu Maven đã tải thành công các dependency JavaFX trong `pom.xml`.

---

# 15. Nếu chương trình không tìm thấy file JSON

Project sử dụng dữ liệu trong thư mục `resources`.

Một số file dữ liệu gồm:

```text
DuLieuMonHoc.json
DuLieuSinhVien.json
```

Nếu chương trình báo không tìm thấy file JSON, hãy kiểm tra các file tài nguyên của project có đầy đủ hay không.

Không tự ý xóa hoặc di chuyển các file này.

Cần tải **toàn bộ project**, không chỉ các file `.java`.

---

# 16. Lưu ý khi chia sẻ project

Không cần chia sẻ thư mục:

```text
target
```

Đây là thư mục Maven tự động tạo ra trong quá trình build.

Người dùng chỉ cần tải đầy đủ source code và các file cần thiết của project.
Maven sẽ tự động tạo lại các thư mục cần thiết.

---

# 17. Checklist chạy chương trình

Trước khi chạy project, hãy đảm bảo:

* [ ] Đã cài JDK 21.
* [ ] `java -version` hiển thị Java 21.
* [ ] Đã cài VS Code hoặc IntelliJ IDEA.
* [ ] Nếu dùng VS Code: đã cài Extension Pack for Java.
* [ ] Đã mở đúng thư mục gốc chứa `pom.xml`.
* [ ] Maven đã import project.
* [ ] Maven đã tải dependency.
* [ ] IDE đang sử dụng JDK 21.
* [ ] Các file JSON và tài nguyên vẫn đầy đủ.
* [ ] Đã chạy **`Server.class`**.
* [ ] Server vẫn đang chạy nền.
* [ ] Đã chạy **`Launch Play`**.
* [ ] Đã lấy đường link do `Launch Play` cung cấp.
* [ ] Đã mở đường link bằng trình duyệt.

---

# 18. Tóm tắt cách chạy

Để chạy hệ thống:

**Bước 1:** Cài JDK 21.

**Bước 2:** Mở project bằng VS Code hoặc IntelliJ IDEA.

**Bước 3:** Chờ Maven tải dependency.

**Bước 4:** Chạy:

```text
Server.class
```

**Bước 5:** Để Server tiếp tục chạy nền.

**Bước 6:** Chạy:

```text
Launch Play
```

**Bước 7:** Copy đường link mà chương trình cung cấp.

**Bước 8:** Dán đường link vào trình duyệt và truy cập.

**Bước 9:** Đăng nhập bằng:

```text
Tên tài khoản: MSSV
Mật khẩu: mk + 4 số cuối MSSV
```

Ví dụ:

```text
MSSV: 25110001
Mật khẩu: mk0001
```

> **Quan trọng:** Server phải được chạy trước và tiếp tục hoạt động nền trong khi sử dụng hệ thống.

---

# 19. Kịch bản kiểm thử hệ thống đăng ký học phần

## Kịch bản 1: Chuyển lớp học phần trong cùng một môn học

**Mục tiêu:** Kiểm tra tính năng đổi lớp học phần khi sinh viên đã đăng ký môn đó.

### Tài khoản đăng nhập

* MSSV: `25110001`
* Mật khẩu: `mk0001`

### Trạng thái ban đầu

Sinh viên đang học môn **Đại số tuyến tính** ở lớp:

```text
DSTT2025001
```

Lịch học:

```text
Thứ 0, Thứ 2 - Tiết 0-3
```

### Thao tác

Chuyển sang lớp:

```text
DSTT2025002
```

Cùng môn:

```text
DSTT2025
```

Lịch học:

```text
Thứ 7, Thứ 9 - Tiết 0-3
```

### Kết quả kỳ vọng

* Chuyển lớp thành công.
* `monDaDangKi`: bỏ `DSTT2025001`, thêm `DSTT2025002`.
* Sĩ số lớp `DSTT2025001` giảm từ 3 → 2.
* Sĩ số lớp `DSTT2025002` tăng từ 2 → 3.
* Thời khóa biểu được cập nhật đúng.

---

## Kịch bản 2: Đăng ký môn học mới thành công

**Mục tiêu:** Kiểm tra đăng ký môn học khi sinh viên đủ điều kiện tiên quyết và không bị trùng lịch.

### Tài khoản đăng nhập

* MSSV: `25110004`
* Mật khẩu: `mk0004`

### Trạng thái ban đầu

Sinh viên đã tích lũy môn:

```text
LTCB1001
```

Lịch hiện tại:

```text
Thứ 4 - Tiết 0-9
```
### Thao tác

Đăng ký môn:

**Lập trình hướng đối tượng**

Mã học phần:

```text
OOP2030
```

Lớp:

```text
OOP2030001
```

Lịch:

```text
Thứ 12 - Tiết 0-4
```

### Kết quả kỳ vọng

* Đăng ký thành công.
* Sinh viên thỏa điều kiện môn học trước `LTCB1001`.
* Lịch học không bị trùng.
* Số tín chỉ đăng ký tăng từ 6 → 9.

---

## Kịch bản 3: Bị chặn do trùng lịch học

**Mục tiêu:** Kiểm tra thuật toán phát hiện xung đột thời khóa biểu.

### Tài khoản đăng nhập

* MSSV: `25110002`
* Mật khẩu: `mk0002`

### Trạng thái ban đầu

Sinh viên đang học lớp:

```text
LTCB1001001
```

Lịch học:

```text
Thứ 1, Thứ 3 - Tiết 0-3
```

### Thao tác

Thử đăng ký một lớp học phần có thời gian học bị trùng với lịch hiện tại.

### Kết quả kỳ vọng

* Hệ thống từ chối đăng ký.
* Hiển thị thông báo trùng lịch.
* Không thêm lớp học phần vào danh sách môn đang đăng ký.
* Thời khóa biểu của sinh viên không bị thay đổi.

