package Project.Ministore.controller;

import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.service.AccountService;
import Project.Ministore.service.CartService;
import Project.Ministore.service.EmailService;
import Project.Ministore.service.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class PaymentController {
    @Autowired
    VNPayService vnPayService;
    @Autowired
    CartService cartService;
    @Autowired
    AccountService accountService;
    @Autowired
    EmailService emailService;

    public AccountEntity getLoggedInUserDetails(Principal principal) {
        String email = principal.getName();
        return accountService.getUserByEmail(email);
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model, HttpSession session, Principal principal) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if (paymentStatus == 1) {
            AccountEntity user = getLoggedInUserDetails(principal); // Lấy thông tin người dùng
            cartService.clearCart(user.getId()); // Xóa giỏ hàng của người dùng

            // Gửi email thông báo thanh toán thành công
            String to = user.getEmail();
            String subject = "Thông báo thanh toán thành công";
            String body = "Chào " + user.getName() + ",\n\n" +
                    "Bạn đã thanh toán thành công đơn hàng với thông tin sau:\n" +
                    "Mã đơn hàng: " + orderInfo + "\n" +
                    "Tổng tiền: " + totalPrice + " VND\n" +
                    "Thời gian thanh toán: " + paymentTime + "\n" +
                    "Mã giao dịch: " + transactionId + "\n\n" +
                    "Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!\n" +
                    "Trân trọng,\n" +
                    "MiniStore";

            emailService.sendEmail(to, subject, body);

            // Đặt thông báo thành công trong session
            session.setAttribute("succMsg", "Thanh toán thành công! Giỏ hàng của bạn đã được xử lý.");
            return "user/ordersuccess";
        } else {
            return "user/orderfail";
        }
    }
}