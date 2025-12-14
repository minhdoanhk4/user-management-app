/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package fa3w.controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author msi2k
 */
public class MainController extends HttpServlet {

    private static final String WELCOME = "login.jsp";
    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";
    private static final String GOOGLE_LOGIN = "GoogleLogin";
    private static final String GOOGLE_LOGIN_CONTROLLER = "GoogleLoginController";
    private static final String SEARCH = "Search";
    private static final String SEARCH_CONTROLLER = "SearchController";
    private static final String UPDATE = "Update";
    private static final String UPDATE_CONTROLLER = "UpdateController";
    private static final String DELETE = "Delete";
    private static final String DELETE_CONTROLLER = "DeleteController";
    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";
    private static final String SIGN_UP_PAGE = "SignUp";
    private static final String SIGN_UP_PAGE_VIEW = "create.jsp";
    private static final String CREATE = "Sign Up";
    private static final String CREATE_CONTROLLER = "CreateController";
    private static final String SHOPPING_BUTTON = "Shopping Page";
    private static final String SHOPPING_PAGE = "shopping.jsp";
    private static final String SEARCH_PRODUCT = "Search Product";
    private static final String SEARCH_PRODUCT_CONTROLLER = "SearchProductController";
    private static final String ADD = "Add";
    private static final String ADD_CONTROLLER = "AddController";
    private static final String VIEW_CART = "View Cart";
    private static final String VIEW_CART_PAGE = "viewCart.jsp";
    private static final String EDIT_PRODUCT = "Edit";
    private static final String EDIT_PRODUCT_CONTROLLER = "EditProductController";
    private static final String REMOVE = "Remove";
    private static final String REMOVECONTROLLER = "RemoveController";
    private static final String BACK_BUTTON = "Back";
    private static final String USER_VIEW = "user.jsp";
    private static final String CHECH_OUT = "Check Out";
    private static final String CHECH_OUT_PAGE = "checkOut.jsp";
    private static final String CONFIRM = "Confirm";
    private static final String CONFIRM_CONTROLLER = "ConfirmController";

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
        String url = WELCOME;
        try {
            String action = request.getParameter("action");
            System.out.println("DEBUG MainController - Action received: " + action);
            if (action == null) {
                url = WELCOME;
            } else if (LOGIN.equals(action)) {
                url = LOGIN_CONTROLLER;
            } else if (GOOGLE_LOGIN.equals(action)) {
                url = GOOGLE_LOGIN_CONTROLLER;
            } else if (SEARCH.equals(action)) {
                url = SEARCH_CONTROLLER;
            } else if (UPDATE.equals(action)) {
                url = UPDATE_CONTROLLER;
            } else if (DELETE.equals(action)) {
                url = DELETE_CONTROLLER;
            } else if (LOGOUT.equals(action)) {
                url = LOGOUT_CONTROLLER;
            } else if (SIGN_UP_PAGE.equals(action)) {
                url = SIGN_UP_PAGE_VIEW;
            } else if (CREATE.equals(action)) {
                url = CREATE_CONTROLLER;
            } else if (SHOPPING_BUTTON.equals(action)) {
                url = SHOPPING_PAGE;
            } else if (SEARCH_PRODUCT.equals(action)) {
                url = SEARCH_PRODUCT_CONTROLLER;
            } else if (ADD.equals(action)) {
                url = ADD_CONTROLLER;
            } else if (VIEW_CART.equals(action)) {
                url = VIEW_CART_PAGE;
            } else if (EDIT_PRODUCT.equals(action)) {
                url = EDIT_PRODUCT_CONTROLLER;
            } else if (REMOVE.equals(action)) {
                url = REMOVECONTROLLER;
            } else if (BACK_BUTTON.equals(action)) {
                url = USER_VIEW;
            } else if (CHECH_OUT.equals(action)) {
                url = CHECH_OUT_PAGE;
            } else if (CONFIRM.equals(action)) {
                url = CONFIRM_CONTROLLER;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
