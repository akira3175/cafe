package com.core.cafe_shop_maven.API;

import com.core.cafe_shop_maven.DTO.NhaCungCap;
import com.core.cafe_shop_maven.DAO.NhaCungCapDAO;
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
        server.createContext("/nhacungcap/search", new TimKiemNhaCungCapHandler());
    }

    private class NhaCungCapHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ArrayList<NhaCungCap> danhSachNhaCungCap = nhaCungCapDAO.getListNhaCungCap();
            sendJsonResponse(exchange, gson.toJson(danhSachNhaCungCap));
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
                    sendResponseBasedOnResult(exchange, ketQua, nhaCungCap);
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
                    sendResponseBasedOnResult(exchange, ketQua, nhaCungCap);
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
            if ("DELETE".equals(exchange.getRequestMethod())) {
                int maNCC = getMaNCCFromQuery(exchange);
                if (maNCC <= 0) {
                    sendErrorResponse(exchange, 400, "Mã nhà cung cấp không hợp lệ");
                    return;
                }
                boolean ketQua = nhaCungCapDAO.deleteNCC(maNCC);
                sendResponseBasedOnResult(exchange, ketQua, null);
            } else {
                sendErrorResponse(exchange, 405, "Phương thức không được hỗ trợ");
            }
        }
    }


    private class TimKiemNhaCungCapHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            int maNCC = getMaNCCFromQuery(exchange);
            NhaCungCap nhaCungCap = nhaCungCapDAO.getNhaCungCap(maNCC);
            System.out.println(maNCC);

            if (nhaCungCap == null) {
                sendErrorResponse(exchange, 404, "Không tìm thấy nhà cung cấp");
                return;
            }

            sendJsonResponse(exchange, gson.toJson(nhaCungCap));
            log("Tìm kiếm nhà cung cấp theo ID: " + maNCC);
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

    private int getMaNCCFromQuery(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if ("maNCC".equals(keyValue[0])) { // Thay đổi tên tham số thành "maNCC"
                    try {
                        return Integer.parseInt(keyValue[1]);
                    } catch (NumberFormatException e) {
                        return -1;
                    }
                }
            }
        }
        return -1;
    }

    private String getTenNCCFromQuery(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] keyValue = param.split("=");
                if ("tenNCC".equals(keyValue[0])) {
                    return java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                }
            }
        }
        return null;
    }

    private void sendResponseBasedOnResult(HttpExchange exchange, boolean result, NhaCungCap nhaCungCap) throws IOException {
        if (result) {
            String response = gson.toJson(nhaCungCap);
            sendJsonResponse(exchange, response);
        } else {
            sendErrorResponse(exchange, 400, "Thao tác thất bại");
        }
    }

    private void sendJsonResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
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
