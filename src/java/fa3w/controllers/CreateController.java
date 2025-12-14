/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fa3w.controllers;

import fa3w.users.UserDAO;
import fa3w.users.UserDTO;
import fa3w.users.UserError;
import fa3w.utils.RecaptchaUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author msi2k
 */
@WebServlet(name = "CreateController", urlPatterns = {"/CreateController"})
public class CreateController extends HttpServlet {

    private static final String ERROR = "create.jsp";
    private static final String SUCCESS = "login.jsp";
    private static final String NOT_ROBOT = "Please verify that you are not a robot!";

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
        UserError error = new UserError();
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
            boolean checkValidation = true;
            UserDAO dao = new UserDAO();
            String userID = request.getParameter("txtUserID");
            String fullName = request.getParameter("txtFullName");
            String roleID = request.getParameter("txtRoleID");
            String password = request.getParameter("txtPassword");
            String confirm = request.getParameter("txtConfirmPass");

            //validation
            if (dao.checkDuplicate(userID)) {
                checkValidation = false;
                error.setUserIDError("User ID already exists!!!");
            }

            if (userID.length() < 2 || userID.length() > 5) {
                checkValidation = false;
                error.setUserIDError("User ID must be in 2 to 5 character");
            }

            if (fullName.length() < 2 || fullName.length() > 20) {
                checkValidation = false;
                error.setFullNameError("Full Name must be in 2 to 20 character");
            }

            if (!password.equals(confirm)) {
                checkValidation = false;
                error.setConfirmdError("Confirm must be equal Password!");
            }

            if (checkValidation) {
                boolean checkInsert = dao.insertUserV2(new UserDTO(userID, fullName, roleID, password));
                if (checkInsert) {
                    url = SUCCESS;
                    request.setAttribute("ERROR", "Sign Up is successfully! Login now");
                } else {
                    error.setError("Unknow Error???");
                    request.setAttribute("USER_ERROR", error);
                }
            } else {
                request.setAttribute("USER_ERROR", error);
            }

        } catch (Exception e) {
            log("Error at CreateController: " + e.toString());
            if (e.toString().contains("duplicate")) {
                error.setUserIDError("User ID already exists");
                request.setAttribute("USER_ERROR", error);
            }
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
