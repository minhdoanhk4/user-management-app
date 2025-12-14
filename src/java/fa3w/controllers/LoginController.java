/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fa3w.controllers;

import fa3w.users.UserDAO;
import fa3w.users.UserDTO;
import fa3w.utils.RecaptchaUtils;
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
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String US_PAGE = "user.jsp";
    private static final String AD_PAGE = "admin.jsp";
    private static final String AD = "AD";
    private static final String US = "US";
    private static final String NOT_SUPPORT = "Your role is not support yet!";
    private static final String NOT_ROBOT = "Please verify that you are not a robot!";
    private static final String INCORRECT_MESSAGE = "Incorrect userID or Password!";

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

        // 1. Lấy mã phản hồi từ client
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

        // 2. Xác thực với Google
        boolean isHuman = RecaptchaUtils.verify(gRecaptchaResponse);

        if (!isHuman) {
            // Nếu xác thực thất bại hoặc chưa tích chọn
            request.setAttribute("ERROR", NOT_ROBOT);
            request.getRequestDispatcher(ERROR).forward(request, response);
            return; // Dừng xử lý, không check user/pass nữa
        }
        try {
            String userID = request.getParameter("txtUserID");
            String password = request.getParameter("txtPassword");

            UserDAO dao = new UserDAO();
            UserDTO loginUser = dao.checkLogin(userID, password);
            //xac thuc nguoi dung bang userID va pass
            if (loginUser == null) {
                request.setAttribute("ERROR", INCORRECT_MESSAGE);
                url = ERROR;
            } else {
                //phan quyen nguoi dung bang roleID
                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_USER", loginUser);
                String roleID = loginUser.getRoleID();
                if (AD.equals(roleID)) {
                    url = AD_PAGE;
                } else if (US.equals(roleID)) {
                    url = US_PAGE;
                } else {
                    request.setAttribute("ERROR", NOT_SUPPORT);
                }
            }

        } catch (Exception e) {
            log("Error at LoginController: " + e.toString());
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
