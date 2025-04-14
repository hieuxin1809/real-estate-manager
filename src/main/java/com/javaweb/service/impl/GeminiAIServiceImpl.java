package com.javaweb.service.impl;

import com.javaweb.model.gennerate.BuildingGennerate;
import com.javaweb.service.GeminiAIService;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeminiAIServiceImpl implements GeminiAIService {

    @Value("${gemini.api.key}") // Inject API key từ application.properties
    private String geminiApiKey;

    // Khởi tạo OkHttpClient để gửi HTTP requests
    private final OkHttpClient client = new OkHttpClient();

    // URL endpoint của Gemini API (model Gemini 2.0 Flash)
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    @Override
    public String generateDescription(BuildingGennerate building) {
        // Tạo prompt đầu vào cho Gemini từ thông tin của toà nhà
        String prompt = String.format(
                "Bạn là một chuyên gia bất động sản. Hãy viết mô tả thu hút khách hàng cho một bất động sản với các thông tin sau:\n" +
                        "- Tên tòa nhà: %s\n" +
                        "- Diện tích: %d m2\n" +
                        "- Số phòng ngủ: %d\n" +
                        "- Địa chỉ: %s, %s\n\n" +
                        "Viết đoạn mô tả ngắn chỉ tầm 42-45 từ (1-2 câu), tự nhiên, hấp dẫn, mang tính marketing, không dùng dấu định dạng như *, **, _, #. " +
                        "Đây là nội dung để hiển thị trực tiếp lên trang web, không cần in đậm hay in nghiêng. Tránh lặp lại y nguyên thông tin đầu vào.",
                building.getName(), building.getFloorArea(), building.getBedrooms(), building.getStreet(), building.getWard()
        );

        // Tạo phần "text" trong nội dung gửi cho Gemini
        JSONObject textPart = new JSONObject();
        textPart.put("text", prompt);

        // Gói phần text vào mảng "parts"
        JSONObject content = new JSONObject();
        content.put("parts", new JSONArray().put(textPart));

        // Gói vào đối tượng "contents" đúng định dạng yêu cầu của Gemini
        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("contents", new JSONArray().put(content));

        // Tạo URL có gắn query param "key" là API key
        HttpUrl url = HttpUrl.parse(GEMINI_API_URL)
                .newBuilder()
                .addQueryParameter("key", geminiApiKey)
                .build();

        // Chuyển JSON request body thành kiểu `RequestBody` cho OkHttp
        RequestBody requestBody = RequestBody.create(
                requestBodyJson.toString(),
                MediaType.parse("application/json")
        );

        // Tạo HTTP POST request với OkHttp
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // Gửi request đến Gemini API
        try (Response response = client.newCall(request).execute()) {
            // Nếu không thành công thì ném exception
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Đọc response body dạng string
            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);

            // Truy cập vào cấu trúc JSON của Gemini để lấy phần nội dung trả về
            return json.getJSONArray("candidates")             // Lấy mảng các phương án trả lời
                    .getJSONObject(0)                          // Lấy phương án đầu tiên
                    .getJSONObject("content")                  // Lấy nội dung phần trả lời
                    .getJSONArray("parts")                     // Lấy mảng các phần (thường chỉ có 1 phần chứa "text")
                    .getJSONObject(0)
                    .getString("text");                        // Lấy chuỗi mô tả kết quả

        } catch (IOException e) {
            // Xử lý lỗi nếu có exception khi gửi/nhận
            e.printStackTrace();
            return "Không thể tạo mô tả."; // Trả về message lỗi để hiển thị
        }
    }
}
