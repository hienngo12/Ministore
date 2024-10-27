package Project.Ministore.util;

import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

@Component
public class CommonUtil {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AccountService accountService;


    public Boolean sendMail(String url, String reciepentEmail)
            throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("tanhien0201@gmail.com", "Shopping Cart");
        helper.setTo(reciepentEmail);
        String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + url
                + "\">Change my password</a></p>";
        helper.setSubject("Password Reset");
        helper.setText(content, true);
        mailSender.send(message);
        return true;
    }
    public static String generateUrl(HttpServletRequest request){
       String siteUrl = request.getRequestURL().toString();

      return siteUrl.replace(request.getServletPath(),"") ;

    }

    public AccountEntity getLoggedInUserDetails(Principal principal) {
        String email = principal.getName();
        AccountEntity account = accountService.getUserByEmail(email);
        return account;
    }

}
