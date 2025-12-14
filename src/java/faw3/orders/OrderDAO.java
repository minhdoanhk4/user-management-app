/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package faw3.orders;

import fa3w.products.Cart;
import fa3w.products.ProductDTO;
import fa3w.users.UserDTO;
import fa3w.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author msi2k
 */
public class OrderDAO {

    private static final String INSERT_ORDER = "INSERT INTO \"tblOrder\"(\"orderDate\", \"userID\", \"total\") VALUES(?,?,?)";

    private static final String ORDER_DETAIL = "INSERT INTO \"tblOrderDetail\"(\"productID\", \"orderID\", \"price\", \"quantity\", \"userID\") VALUES(?,?,?,?,?)";

    private static final String UPDATE_QUANTITY = "UPDATE \"tblProduct\" SET \"quantity\" = \"quantity\" - ? WHERE \"productID\" = ?";

    public boolean insertOrder(UserDTO user, Cart cart) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false); // 1. BẮT ĐẦU TRANSACTION

                // RETURN_GENERATED_KEYS để lấy ID vừa tự sinh ra
                ptm = conn.prepareStatement(INSERT_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
                ptm.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                ptm.setString(2, user.getUserID());

                // Tính tổng tiền
                double total = 0;
                for (ProductDTO dto : cart.getCart().values()) {
                    total += dto.getPrice() * dto.getQuantity();
                }
                ptm.setDouble(3, total);
                ptm.executeUpdate();

                // Lấy OrderID vừa tạo
                rs = ptm.getGeneratedKeys();
                int orderID = 0;
                if (rs.next()) {
                    orderID = rs.getInt(1);
                }

                // --- Bước B: Insert Chi tiết & Trừ Kho ---
                PreparedStatement ptmDetail = conn.prepareStatement(ORDER_DETAIL);
                PreparedStatement ptmProduct = conn.prepareStatement(UPDATE_QUANTITY);

                for (ProductDTO item : cart.getCart().values()) {
                    // Thêm chi tiết
                    ptmDetail.setString(1, item.getProductID());
                    ptmDetail.setInt(2, orderID);
                    ptmDetail.setDouble(3, item.getPrice());
                    ptmDetail.setInt(4, item.getQuantity());
                    ptmDetail.setString(5, user.getUserID());
                    ptmDetail.executeUpdate(); // Chạy lệnh insert detail

                    // Trừ kho
                    ptmProduct.setInt(1, item.getQuantity());
                    ptmProduct.setString(2, item.getProductID());
                    ptmProduct.executeUpdate(); // Chạy lệnh update kho
                }

                conn.commit(); // 2. LƯU TẤT CẢ THÀNH CÔNG
                check = true;
            }
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback(); // 3. NẾU LỖI -> HOÀN TÁC SẠCH SẼ
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

}
