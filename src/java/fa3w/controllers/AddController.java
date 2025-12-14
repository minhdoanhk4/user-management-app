/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fa3w.controllers;

import fa3w.products.Cart;
import fa3w.products.ProductDAO;
import fa3w.products.ProductDTO;
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
@WebServlet(name = "AddController", urlPatterns = {"/AddController"})
public class AddController extends HttpServlet {

    private static final String ERROR = "SearchProductController";
    private static final String SUCCESS = "SearchProductController";

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
            String id = request.getParameter("id");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            HttpSession sesion = request.getSession();
            Cart cart = (Cart) sesion.getAttribute("CART");
            if (cart == null) {
                cart = new Cart();
            }

            ProductDAO dao = new ProductDAO();
            ProductDTO product = dao.getProduct(id); // Lúc này product đã có quantity chuẩn từ DB

            // 1. Kiểm tra số lượng đã có trong giỏ hàng
            int quantityInCart = 0;
            if (cart.getCart() != null && cart.getCart().containsKey(id)) {
                quantityInCart = cart.getCart().get(id).getQuantity();
            }

            // 2. Logic kiểm tra tồn kho
            // Nếu (Trong giỏ + Muốn mua thêm) > Tồn kho thực tế -> Báo lỗi
            if (quantityInCart + quantity > product.getQuantity()) {
                String message = "Sản phẩm " + product.getName() + " chỉ còn " + product.getQuantity() + " cái trong kho.";
                if (quantityInCart > 0) {
                    message += " (Bạn đã có " + quantityInCart + " cái trong giỏ hàng)";
                }
                request.setAttribute("ERROR", message);
                url = ERROR;
            } else {
                // Đủ hàng -> Set số lượng muốn mua để add vào giỏ
                product.setQuantity(quantity);
                boolean check = cart.add(product);
                if (check) {
                    sesion.setAttribute("CART", cart);
                    url = SUCCESS;
                    request.setAttribute("MESSAGE", "Đã thêm " + quantity + " " + product.getName() + " vào giỏ thành công!");
                }
            }

        } catch (Exception e) {
            log("Error at AddController " + e.toString());
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
