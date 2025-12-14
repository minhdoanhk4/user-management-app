/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa3w.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author msi2k
 */
public class RecaptchaUtils {

    public static final String SECRET_KEY = "6LfI_CEsAAAAABSvIixqI46Ibn_sIaFCXxg1xB5P";
    public static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public static boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }

        try {
            URL url = new URL(VERIFY_URL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            // Cấu hình kết nối
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Gửi tham số (secret và response)
            String params = "secret=" + SECRET_KEY + "&response=" + gRecaptchaResponse;
            try ( OutputStream out = conn.getOutputStream()) {
                out.write(params.getBytes());
                out.flush();
            }

            // Đọc phản hồi từ Google
            InputStream is = conn.getInputStream();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);

            // Kiểm tra kết quả
            return jsonObject.get("success").getAsBoolean();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
