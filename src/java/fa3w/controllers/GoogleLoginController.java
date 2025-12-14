/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fa3w.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport; // <--- Cần NetHttpTransport
import com.google.api.client.json.gson.GsonFactory; // <--- Cần GsonFactory
import fa3w.users.UserDAO;
import fa3w.users.UserDTO;
import java.io.IOException;
import java.util.Collections;
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
@WebServlet(name = "GoogleLoginController", urlPatterns = {"/GoogleLoginController"})
public class GoogleLoginController extends HttpServlet {

    private static final String CLIENT_ID = "780471524602-gn88fpdjcvd93meano38qvkuqrc0paqb.apps.googleusercontent.com";

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String USER_PAGE = "user.jsp";
    private static final String ADMIN_PAGE = "admin.jsp";
    private static final String AD_ROLE = "AD";
    private static final String US_ROLE = "US";

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
        String url = LOGIN_PAGE;
        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            request.setAttribute("ERROR", "Google JWT token is missing!");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        try {
            // Xác thực Token
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(token);

            if (idToken != null) {
                Payload payload = idToken.getPayload();

                String userID = (String) payload.get("email");
                String fullName = (String) payload.get("name");

                // 2. GỌI DAO ĐỂ XỬ LÝ VIỆC TẠO/TÌM NGƯỜI DÙNG (SIMPLE & CLEAN)
                UserDAO dao = new UserDAO();
                UserDTO loginUser = dao.checkAndCreateGoogleUser(userID, fullName); // Dùng phương thức mới

                if (loginUser != null) {
                    // 3. Thiết lập Session và Chuyển hướng
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", loginUser);

                    String roleID = loginUser.getRoleID();
                    if (AD_ROLE.equals(roleID)) {
                        url = ADMIN_PAGE;
                    } else if (US_ROLE.equals(roleID)) {
                        url = USER_PAGE;
                    } else {
                        request.setAttribute("ERROR", "Role not supported.");
                        url = LOGIN_PAGE;
                    }
                } else {
                    request.setAttribute("ERROR", "Failed to find or create Google user in DB.");
                }
            } else {
                request.setAttribute("ERROR", "Invalid Google ID token.");
            }

        } catch (Throwable e) { // <--- ĐỔI Exception THÀNH Throwable
            e.printStackTrace();
            // In lỗi ra console server để bạn check
            System.out.println("GOOGLE LOGIN ERROR: " + e.toString());
            // Hiện lỗi lên màn hình JSP
            request.setAttribute("ERROR", "Lỗi hệ thống: " + e.toString());
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
