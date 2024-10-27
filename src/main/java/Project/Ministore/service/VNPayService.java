package Project.Ministore.service;

import Project.Ministore.Config.VNPayConfig;
import Project.Ministore.Entity.CartEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {

    // Tạo URL thanh toán sử dụng kiểu long cho số tiền và mã giao dịch
    public String createOrder(Long price, String orderId, String urlReturn) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";

        // Mã giao dịch kiểu long
        long vnp_TxnRefLong = VNPayConfig.getRandomLong(8); // Sinh mã giao dịch ngẫu nhiên kiểu long
        String vnp_TxnRef = String.valueOf(vnp_TxnRefLong); // Chuyển mã giao dịch thành chuỗi

        String vnp_IpAddr = "127.0.0.1"; // Địa chỉ IP của người dùng
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode; // Mã TmnCode của VNPay
        String orderType = "order-type"; // Loại đơn hàng

        // Tạo bảng tham số gửi tới VNPay
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);

        // Số tiền kiểu long, nhân 100 và chuyển thành chuỗi
        vnp_Params.put("vnp_Amount", String.valueOf(price * 100)); // Số tiền nhân với 100
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderId);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        urlReturn += VNPayConfig.vnp_Returnurl; // URL trả về sau khi thanh toán
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Ngày tạo giao dịch và ngày hết hạn
        long timestamp = System.currentTimeMillis(); // Thời gian hiện tại dưới dạng long
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(new Date(timestamp)); // Ngày tạo giao dịch
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        // Thêm 15 phút vào thời gian hiện tại để tạo thời gian hết hạn
        Calendar cld = Calendar.getInstance();
        cld.setTimeInMillis(timestamp);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Sắp xếp các tham số theo thứ tự alphabet
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        // Tạo chuỗi hash và query
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName).append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=')
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        // Tạo chữ ký bảo mật
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        // Tạo URL thanh toán
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + query.toString();
        return paymentUrl;
    }

    public int orderReturn(HttpServletRequest request) {
        // Process the request and return the payment status (1 for success, 0 for failure)
        // Your logic here
        return 1; // Assuming 1 means success, adjust this logic accordingly
    }
}