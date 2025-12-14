# --- GIAI ĐOẠN 1: Build (Dùng Ant để đóng gói code) ---
# Sử dụng image chứa sẵn Ant và Java 8
FROM frekele/ant:1.10.3-jdk8 AS build
WORKDIR /app

# Copy toàn bộ mã nguồn từ máy bạn vào trong Docker
COPY . .

# Chạy lệnh Ant để tạo ra file .war (file web)
# Lệnh này sẽ tìm file build.xml ngay tại thư mục gốc /app
RUN ant -noinput -buildfile build.xml dist

# --- GIAI ĐOẠN 2: Run (Dùng Tomcat để chạy web) ---
# Sử dụng Tomcat 9 (nhẹ) để chạy ứng dụng
FROM tomcat:9.0-jre8-openjdk-slim

# Xóa các ứng dụng mặc định của Tomcat để nhẹ và sạch
RUN rm -rf /usr/local/tomcat/webapps/*

# QUAN TRỌNG: Copy Driver kết nối Database Postgres
# Lấy từ thư mục lib của code bạn đưa vào thư mục thư viện của Tomcat
COPY --from=build /app/lib/postgresql-42.2.20.jar /usr/local/tomcat/lib/

# Copy file .war đã build ở Giai đoạn 1 vào thư mục chạy web của Tomcat
# Đổi tên thành ROOT.war để khi truy cập không cần gõ tên project
COPY --from=build /app/dist/*.war /usr/local/tomcat/webapps/ROOT.war

# Mở cổng 8080 để bên ngoài truy cập được
EXPOSE 8080

# Lệnh khởi động Server
CMD ["catalina.sh", "run"]