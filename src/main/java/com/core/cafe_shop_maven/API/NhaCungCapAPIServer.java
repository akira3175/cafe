package com.core.cafe_shop_maven.API;

import com.core.cafe_shop_maven.DTO.NhaCungCap;
import com.core.cafe_shop_maven.DAO.NhaCungCapDAO;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class NhaCungCapAPIServer extends JFrame {
    private JTextArea logArea;
    private HttpServer server;
    private static final int PORT = 8000;
    private final NhaCungCapDAO nhaCungCapDAO;
    private final Gson gson;

    public NhaCungCapAPIServer() {
        nhaCungCapDAO = NhaCungCapDAO.getInstance();
        gson = new Gson();
        initializeUI();
    }

    private void initializeUI() {
        try {
            // Đặt Look and Feel mặc định
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Nhà Cung Cấp API Server");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton startButton = new JButton("Start Server");
        startButton.addActionListener(e -> startServer());
        add(startButton, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);
    }

    private void startServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            registerHandlers();
            server.setExecutor(null);
            server.start();
            log("Server đang chạy tại http://localhost:" + PORT);
        } catch (IOException e) {
            log("Lỗi khi khởi động server: " + e.getMessage());
        }
    }

    private void registerHandlers() {
        server.createContext("/nhacungcap", new NhaCungCapHandler());
        server.createContext("/nhacungcap/add", new ThemNhaCungCapHandler());
        server.createContext("/nhacungcap/change", new CapNhatNhaCungCapHandler());
        server.createContext("/nhacungcap/delete", new XoaNhaCungCapHandler());
        server.createContext("/nhacungcap/", new TimKiemNhaCungCapHandler());
    }

    private class NhaCungCapHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ArrayList<NhaCungCap> danhSachNhaCungCap = nhaCungCapDAO.getListNhaCungCap();
            sendJsonResponse(exchange, danhSachNhaCungCap, 200);
            log("Request tới /nhacungcap từ " + exchange.getRemoteAddress());
        }
    }

    private class ThemNhaCungCapHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    NhaCungCap nhaCungCap = readNhaCungCapFromRequest(exchange);
                    validateNhaCungCap(nhaCungCap);
                    boolean ketQua = nhaCungCapDAO.addNCC(nhaCungCap);
                    sendResponseBasedOnResult(exchange, ketQua, nhaCungCap, "add");
                } catch (ValidationException ve) {
                    sendErrorResponse(exchange, ve.getStatusCode(), ve.getMessage());
                } catch (Exception e) {
                    sendErrorResponse(exchange, 500, "Lỗi xử lý: " + e.getMessage());
                }
            } else {
                sendErrorResponse(exchange, 405, "Phương thức không được hỗ trợ");
            }
        }
    }

    private class CapNhatNhaCungCapHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("PUT".equals(exchange.getRequestMethod())) {
                try {
                    NhaCungCap nhaCungCap = readNhaCungCapFromRequest(exchange);
                    validateNhaCungCap(nhaCungCap);
                    boolean ketQua = nhaCungCapDAO.updateNCC(nhaCungCap);
                    sendResponseBasedOnResult(exchange, ketQua, nhaCungCap, "update");
                } catch (ValidationException ve) {
                    sendErrorResponse(exchange, ve.getStatusCode(), ve.getMessage());
                } catch (Exception e) {
                    sendErrorResponse(exchange, 500, "Lỗi xử lý: " + e.getMessage());
                }
            } else {
                sendErrorResponse(exchange, 405, "Phương thức không được hỗ trợ");
            }
        }
    }

    private class XoaNhaCungCapHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/"); // Tách đường dẫn
            if (pathParts.length < 4 || pathParts[3].isEmpty()) {
                sendErrorResponse(exchange, 400, "Mã nhà cung cấp không hợp lệ");
                return;
            }

            try {
                int maNCC = Integer.parseInt(pathParts[3]);

                // Kiểm tra xem nhà cung cấp có tồn tại không
                NhaCungCap nhaCungCap = nhaCungCapDAO.getNhaCungCap(maNCC);
                if (nhaCungCap == null) {
                    sendErrorResponse(exchange, 404, "Không tìm thấy nhà cung cấp");
                    return;
                }

                // Thực hiện xóa nhà cung cấp
                boolean ketQua = nhaCungCapDAO.deleteNCC(maNCC);
                if (ketQua) {
                    sendJsonResponse(exchange, null, 204); // Trả về mã 204 No Content
                    log("Đã xóa nhà cung cấp với ID: " + maNCC);
                } else {
                    sendErrorResponse(exchange, 500, "Lỗi khi xóa nhà cung cấp");
                }
            } catch (NumberFormatException e) {
                sendErrorResponse(exchange, 400, "Mã nhà cung cấp không hợp lệ");
            }
        }
    }

    private class TimKiemNhaCungCapHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/"); // Tách đường dẫn
            if (pathParts.length < 3) {
                sendErrorResponse(exchange, 400, "Mã nhà cung cấp không hợp lệ");
                return;
            }

            try {
                int maNCC = Integer.parseInt(pathParts[2]);
                NhaCungCap nhaCungCap = nhaCungCapDAO.getNhaCungCap(maNCC);

                if (nhaCungCap == null) {
                    sendErrorResponse(exchange, 404, "Không tìm thấy nhà cung cấp");
                    return;
                }

                sendJsonResponse(exchange, nhaCungCap, 200);
                log("Tìm kiếm nhà cung cấp theo ID: " + maNCC);
            } catch (NumberFormatException e) {
                sendErrorResponse(exchange, 400, "Mã nhà cung cấp không hợp lệ");
            }
        }
    }

    private NhaCungCap readNhaCungCapFromRequest(HttpExchange exchange) {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        BufferedReader br = new BufferedReader(isr);
        String jsonInput = br.lines().collect(Collectors.joining());
        return gson.fromJson(jsonInput, NhaCungCap.class);
    }

    private void validateNhaCungCap(NhaCungCap nhaCungCap) throws ValidationException {
        // Kiểm tra đối tượng null
        if (nhaCungCap == null) {
            throw new ValidationException(400, "Dữ liệu nhà cung cấp không được để trống");
        }

        // Validate Tên NCC (Name)
        if (nhaCungCap.getTenNCC() == null || nhaCungCap.getTenNCC().trim().isEmpty()) {
            throw new ValidationException(422, "Hãy nhập tên nhà cung cấp!");
        }

        // Validate Địa Chỉ (Address)
        if (nhaCungCap.getDiaChi() == null || nhaCungCap.getDiaChi().trim().isEmpty()) {
            throw new ValidationException(422, "Hãy nhập địa chỉ!");
        }

        // Validate Điện Thoại (Phone)
        if (nhaCungCap.getDienThoai() == null || nhaCungCap.getDienThoai().trim().isEmpty()) {
            throw new ValidationException(422, "Hãy nhập số điện thoại!");
        }

        // Validate Fax
        if (nhaCungCap.getFax() == null || nhaCungCap.getFax().trim().isEmpty()) {
            throw new ValidationException(422, "Hãy nhập fax!");
        }

        // Validate Phone Number Format (10 digits)
        Pattern phonePattern = Pattern.compile("^\\d{10}$");
        if (!phonePattern.matcher(nhaCungCap.getDienThoai()).matches()) {
            throw new ValidationException(400, "Số điện thoại không hợp lệ - phải là 10 chữ số!");
        }
    }

    private void sendResponseBasedOnResult(HttpExchange exchange, boolean result, NhaCungCap nhaCungCap, String action) throws IOException {
        if (result) {
            String responseMessage;
            Map<String, Object> responseMap = new HashMap<>();

            switch (action) {
                case "add":
                    responseMessage = "Thêm nhà cung cấp thành công!";
                    responseMap.put("message", responseMessage);
                    responseMap.put("data", nhaCungCap);
                    sendJsonResponse(exchange, responseMap, 201);
                    break;
                case "update":
                    responseMessage = "Cập nhật nhà cung cấp thành công!";
                    responseMap.put("message", responseMessage);
                    responseMap.put("data", nhaCungCap);
                    sendJsonResponse(exchange, responseMap, 200);
                    break;
                case "delete":
                    // Nếu xóa thành công, trả về 204 (No Content)
                    exchange.sendResponseHeaders(204, -1);
                    return; // Không cần gửi nội dung phản hồi
                default:
                    sendErrorResponse(exchange, 400, "Thao tác không hợp lệ");
            }
        } else {
            sendErrorResponse(exchange, 400, "Thao tác thất bại");
        }
    }

    private void sendJsonResponse(HttpExchange exchange, Object data, int statusCode) throws IOException {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResponse = prettyGson.toJson(data);
        byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String errorMessage) throws IOException {
        log("Error Response - Status: " + statusCode + ", Message: " + errorMessage);

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", errorMessage);
        String jsonErrorResponse = gson.toJson(errorResponse);

        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        byte[] responseBytes = jsonErrorResponse.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
            os.flush();
        }
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(java.time.LocalDateTime.now() + ": " + message + "\n"));
    }

    // Lớp Exception tùy chỉnh để xử lý lỗi validation
    private static class ValidationException extends Exception {
        private final int statusCode;

        public ValidationException(int statusCode, String message) {
            super(message);
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NhaCungCapAPIServer().setVisible(true));
    }
}
