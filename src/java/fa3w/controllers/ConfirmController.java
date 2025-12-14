/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fa3w.controllers;

import fa3w.products.Cart;
import fa3w.products.ProductDAO;
import fa3w.products.ProductDTO;
import fa3w.users.UserDTO;
import faw3.orders.OrderDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author msi2k
 */
@WebServlet(name = "ConfirmController", urlPatterns = {"/ConfirmController"})
public class ConfirmController extends HttpServlet {

    private static final String ERROR = "checkOut.jsp";
    private static final String SUCCESS = "shopping.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CART");
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

            if (user != null && cart != null && !cart.getCart().isEmpty()) {

                // --- BẮT ĐẦU ĐOẠN CODE THÊM MỚI ---
                // Mục đích: Kiểm tra lại tồn kho thực tế trong DB trước khi cho phép mua
                ProductDAO productDAO = new ProductDAO();
                boolean isStockAvailable = true;
                String errorMsg = "";

                for (ProductDTO itemInCart : cart.getCart().values()) {
                    // Lấy thông tin mới nhất từ DB
                    ProductDTO productInDB = productDAO.getProduct(itemInCart.getProductID());

                    // Trường hợp 1: Sản phẩm đã bị xóa khỏi DB
                    if (productInDB == null) {
                        isStockAvailable = false;
                        errorMsg = "Sản phẩm " + itemInCart.getName() + " không còn tồn tại.";
                        break;
                    }

                    // Trường hợp 2: Số lượng trong kho ít hơn số lượng muốn mua
                    if (productInDB.getQuantity() < itemInCart.getQuantity()) {
                        isStockAvailable = false;
                        errorMsg = "Sản phẩm " + itemInCart.getName() + " hiện chỉ còn "
                                + productInDB.getQuantity() + " cái. Vui lòng cập nhật lại giỏ hàng.";
                        break;
                    }
                }

                // Nếu có lỗi tồn kho, dừng ngay và báo lỗi về trang checkOut
                if (!isStockAvailable) {
                    request.setAttribute("ERROR", errorMsg);
                    request.getRequestDispatcher(ERROR).forward(request, response);
                    return; // Dừng xử lý tại đây
                }
                // --- KẾT THÚC ĐOẠN CODE THÊM MỚI ---

                OrderDAO dao = new OrderDAO();
                boolean check = dao.insertOrder(user, cart);
                if (check) {
                    session.removeAttribute("CART");
                    request.setAttribute("MESSAGE", "Mua hàng thành công! Cảm ơn bạn.");
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Thanh toán thất bại (Lỗi hệ thống).");
                }
            } else {
                request.setAttribute("ERROR", "Phiên làm việc hết hạn hoặc giỏ hàng trống.");
            }
        } catch (Exception e) {
            log("Error at ConfirmController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
