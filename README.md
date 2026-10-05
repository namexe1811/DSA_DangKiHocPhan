# DSA_DangKiHocPhan

Đồ án DSA – Hệ thống quản lý đăng ký học phần.

# 1. Cách chạy chương trình

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

# 2. Đăng nhập hệ thống

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

# 3. Chạy project bằng IntelliJ IDEA

Có thể tải IntelliJ IDEA tại:

[JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/?utm_source=chatgpt.com)

## 3.1. Mở project

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

## 3.2. Import Maven

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

# 4. Cấu hình JDK 21 trong IntelliJ IDEA

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

# 5. Chạy project trong IntelliJ IDEA

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

# 6. Nếu chương trình không tìm thấy file JSON

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

# 7. Lưu ý khi chia sẻ project

Không cần chia sẻ thư mục:

```text
target
```

Đây là thư mục Maven tự động tạo ra trong quá trình build.

Người dùng chỉ cần tải đầy đủ source code và các file cần thiết của project.
Maven sẽ tự động tạo lại các thư mục cần thiết.

---


# 8. Kịch bản kiểm thử hệ thống đăng ký học phần

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

