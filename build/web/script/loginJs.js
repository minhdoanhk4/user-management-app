/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
/**
 * Xử lý phản hồi từ Google sau khi người dùng đăng nhập thành công.
 * @param {Object} response - Đối tượng chứa credential (JWT token) từ Google.
 */
function handleCredentialResponse(response) {
    // 1. Lấy JWT token từ phản hồi của Google
    var token = response.credential;

    // 2. Điền token vào input ẩn trong form
    var tokenInput = document.getElementById('google-token-form-jwt');
    if (tokenInput) {
        tokenInput.value = token;

        // 3. Gửi form (submit) về phía server (GoogleLoginController)
        document.getElementById('google-token-form').submit();
    } else {
        console.error("Không tìm thấy input ẩn để chứa token!");
    }
}

