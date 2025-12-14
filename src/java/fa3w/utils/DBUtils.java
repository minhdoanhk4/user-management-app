/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa3w.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author msi2k
 */
public class DBUtils {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("org.postgresql.Driver");

        // --- CẤU HÌNH SUPABASE ---
        // Bạn hãy điền thông tin lấy từ Bước 1 vào đây
        String host = "aws-1-ap-south-1.pooler.supabase.com"; // Thay bằng Host của bạn
        String port = "5432";
        String dbName = "postgres";
        String user = "postgres.iugbcauxxhwisuareasl"; // Kiểm tra kỹ user trên Supabase
        String pass = "12345"; // Điền mật khẩu DB vào đây

        // --- XỬ LÝ CHO RENDER (Tùy chọn nâng cao) ---
        // Nếu sau này deploy lên Render, code sẽ tự ưu tiên lấy biến môi trường
        // để bạn không bị lộ mật khẩu cứng trong code.
        if (System.getenv("DB_HOST") != null) {
            host = System.getenv("DB_HOST");
            user = System.getenv("DB_USER");
            pass = System.getenv("DB_PASSWORD");
        }

        // --- TẠO CHUỖI KẾT NỐI ---
        // Lưu ý: Supabase BẮT BUỘC phải có ?sslmode=require
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName + "?sslmode=require";

        conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }
}
