/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fa3w.controllers;

import fa3w.users.UserDAO;
import fa3w.users.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author msi2k
 */
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    private static final String ERROR = "admin.jsp";
    private static final String SUCCESS = "admin.jsp";
    private static final String NOT_FOUND = "Not Found any data about: ";

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
            String search = request.getParameter("txtSearchValue");
            if (search == null) {
                search = "";
            }

            String indexPage = request.getParameter("index");
            if (indexPage == null) {
                indexPage = "1";
            }
            int index = Integer.parseInt(indexPage);

            UserDAO dao = new UserDAO();
            List<UserDTO> listUser = dao.getListUser(search);
            if (listUser.size() > 0) {
                // --- BẮT ĐẦU LOGIC PHÂN TRANG ---
                int pageSize = 5; // Quy định 5 dòng/trang
                int count = listUser.size();
                
                // Tính tổng số trang (endPage)
                int endPage = count / pageSize;
                if (count % pageSize != 0) {
                    endPage++;
                }

                // Kiểm tra index (đề phòng user sửa URL)
                if(index > endPage) index = endPage;
                if(index < 1) index = 1;

                // Cắt danh sách con (Sublist)
                int start = (index - 1) * pageSize;
                int end = Math.min(start + pageSize, count);
                List<UserDTO> pageList = listUser.subList(start, end);

                // Gửi dữ liệu sang JSP
                request.setAttribute("LIST_USER", pageList); // Danh sách ĐÃ CẮT (5 dòng)
                request.setAttribute("endPage", endPage);    // Tổng số trang
                request.setAttribute("currentPage", index);  // Trang hiện tại
                request.setAttribute("txtSearchValue", search); // Giữ lại từ khóa để phân trang đúng
                
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", NOT_FOUND + "'" + search + "'");
            }
        } catch (Exception e) {
            log("Error at SearchController " + e.toString());
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
