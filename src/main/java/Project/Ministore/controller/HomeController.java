package Project.Ministore.controller;
import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.Entity.CategoryEntity;
import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.AccountRepository;
import Project.Ministore.repository.CartRepository;
import Project.Ministore.repository.CategoryRepository;
import Project.Ministore.repository.ProductRepository;
import Project.Ministore.service.*;
import Project.Ministore.util.CommonUtil;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

@Controller
public class HomeController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @ModelAttribute
    public void getUserDetails(Principal principal, Model model){
        if (principal !=null ){
            String email = principal.getName();
            AccountEntity user = accountService.getUserByEmail(email);
            model.addAttribute("user", user);
            int countCart = cartService.getCountCart(user.getId());
            model.addAttribute("countCart", countCart);
        }
        List<CategoryEntity> allActiveCategory = categoryService.findByActiveTrue();
        model.addAttribute("categories", allActiveCategory);
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/view_product")
    public String viewproduct(Model model) {
        List<ProductEntity> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "view_product";
    }
    @GetMapping("/products")
    public String showProduct(Model model, @Param("keyword") String keyword,
                              @RequestParam(value = "category_id", required = false) Integer categoryId,
                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        List<CategoryEntity> category = categoryRepository.findAll();
        List<ProductEntity> product = productRepository.findAll();
        model.addAttribute("category", category);
        model.addAttribute("products", product);
        Page<ProductEntity> list;
        // Nếu có từ khóa tìm kiếm, thực hiện tìm kiếm với phân trang
        if (categoryId != null) {
            // Fetch products by category if categoryId is provided
            list = productService.getProductsByCategory(categoryId, pageNo);
        } else if (keyword != null && !keyword.isEmpty()) {
            list = productService.searchProduct(keyword, pageNo);
            model.addAttribute("keyword", keyword);// Để hiển thị lại từ khóa trên giao diện
        } else {
            // Nếu không có từ khóa, chỉ thực hiện phân trang
            list = productService.getAllProduct(pageNo);
        }
        // Thêm các thông tin cần thiết vào model
        model.addAttribute("totalPage", list.getTotalPages());// Trang hiện tại
        model.addAttribute("currentPage", pageNo);// Tổng số trang
        model.addAttribute("products", list.getContent());// Lấy danh sách sản phẩm hiện tại
        model.addAttribute("totalProducts", list.getTotalElements());// Tổng số sản phẩm
        return "product";
    }
    @GetMapping("/filter")
    public String filterProducts(
            @RequestParam(value = "priceRange", required = false) String priceRange,
            @RequestParam(value = "minPrice", required = false) String minPrice,
            @RequestParam(value = "maxPrice", required = false) String maxPrice,
            Model model) {
        List<CategoryEntity> category = categoryRepository.findAll();
        model.addAttribute("category", category);
        // Khởi tạo danh sách sản phẩm được lọc
        List<ProductEntity> filteredProducts = new ArrayList<>();
        // Kiểm tra lựa chọn khoảng giá cố định (priceRange)
        if (priceRange != null) {
            switch (priceRange) {
                case "under2m":
                    filteredProducts = productService.findByPriceLessThan(2000000L);
                    break;
                case "2to4m":
                    filteredProducts = productService.findByPriceBetween(2000000L, 4000000L);
                    break;
                case "6to8m":
                    filteredProducts = productService.findByPriceBetween(6000000L, 8000000L);
                    break;
                case "10to30m":
                    filteredProducts = productService.findByPriceBetween(10000000L, 30000000L);
                    break;
                case "50to90m":
                    filteredProducts = productService.findByPriceBetween(50000000L, 90000000L);
                    break;
                case "over100m":
                    filteredProducts = productService.findByPriceGreaterThan(100000000L);
                    break;
                default:
                    filteredProducts = productService.getAllProduct();
                    break;
            }
        }
        // Kiểm tra khoảng giá tùy chỉnh (minPrice và maxPrice)
        if (minPrice != null && maxPrice != null) {
            try {
                Long min = Long.parseLong(minPrice);
                Long max = Long.parseLong(maxPrice);
                if (min > max) {
                    model.addAttribute("error", "Giá tối thiểu không được lớn hơn giá tối đa.");
                    filteredProducts = productService.getAllProduct();
                } else {
                    filteredProducts = productService.findByPriceBetween(min, max);
                }
            } catch (NumberFormatException e) {
                model.addAttribute("error", "Khoảng giá không hợp lệ.");
            }
        }
        // Đưa dữ liệu sản phẩm vào model để hiển thị trên view
        model.addAttribute("products", filteredProducts);
        return "product";
    }
    @GetMapping("/products/{id}")
    public String Product(@PathVariable int id, Model model) {
        ProductEntity productById = productRepository.findById(id).orElse(null);
        if (productById == null) {
            // Thêm thông báo lỗi vào model nếu sản phẩm không được tìm thấy
            model.addAttribute("error", "Sản phẩm không tồn tại.");
            return "403"; // Chuyển hướng đến trang lỗi tùy chỉnh
        }
        model.addAttribute("product", productById);
        return "view_product"; // Nếu tìm thấy sản phẩm, hiển thị trang chi tiết
    }
    @PostMapping(value = "/saveUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveUser(@RequestParam("name") String name,
                           @RequestParam("email") String email,
                           @RequestParam("address") String address,
                           @RequestParam("city") String city,
                           @RequestParam("province") String province,
                           @RequestParam("pincode") String pincode,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           @RequestParam("phone") Long phone,
                           @RequestParam(value = "roles", required = false) List<String> roles,
                           @RequestParam("profile_image") MultipartFile profileImage,// Đảm bảo rằng profileImage khớp với form
                           Model model) {
        try {
//             Kiểm tra logic và xử lý dữ liệu
            if (!password.equals(confirmPassword)) {
                model.addAttribute("msg", "Mật khẩu không khớp!");
                return "register";
            }
            // Tạo đối tượng AccountEntity và thiết lập các thuộc tính
            AccountEntity user = new AccountEntity();
            user.setName(name);
            user.setEmail(email);
            user.setAddress(address);
            user.setCity(city);
            user.setProvince(province);
            user.setPincode(pincode);
            user.setPhone(phone);
            user.setEnable(true);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setRole("ROLE_USER");
//            user.setPassword(password);
            // Kiểm tra nếu có tệp hình ảnh được tải lên
            if (!profileImage.isEmpty()) {
                user.setProfileImage(profileImage.getBytes());
            }
            // Lưu dữ liệu người dùng vào cơ sở dữ liệu
            accountRepository.save(user);
            model.addAttribute("msg", "Người dùng đã đăng ký thành công!");
            return "redirect:/register";
        } catch (Exception e) {
            model.addAttribute("msg", "Error: " + e.getMessage());
            return "register";
        }
    }
    @GetMapping(value = "/getCategoryFile/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getCategoryFile(@PathVariable("id") int id) {
        CategoryEntity category = categoryRepository.findById(id).orElse(null);
        if (category != null && category.getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(category.getImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/getProductFile/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getProductFile(@PathVariable("id") int id) {
        ProductEntity product = productRepository.findById(id).orElse(null);
        if (product != null && product.getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(product.getImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/forgot-password")
    public String showForgotPassword(){
        return "forgot_password";
    }
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, HttpSession session, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        AccountEntity userByEmail = accountService.getUserByEmail(email);
        if (ObjectUtils.isEmpty(userByEmail)){
            session.setAttribute("errorMsg", "Email không hợp lệ");
        }else {
            String reset_token = UUID.randomUUID().toString();
            accountService.updateUserResetToken(email, reset_token);
            String url = CommonUtil.generateUrl(request)+"/reset-password?token="+reset_token;

            Boolean sendEmail = commonUtil.sendMail(url,email);

            if (sendEmail) {
                session.setAttribute("succMsg", "Vui lòng kiểm tra email của bạn...Đã gửi liên kết đặt lại mật khẩu");
            } else {
                session.setAttribute("errorMsg", "Đã xảy ra lỗi trên máy chủ! Email không gửi được");
            }
        }
        return "redirect:/forgot-password";
    }
    @GetMapping("/reset-password")
    public String showResetPassword(@RequestParam String token, HttpSession session, Model model) {

        AccountEntity userByToken = accountService.getUserByToken(token);

        if (userByToken == null) {
            model.addAttribute("msg", "Liên kết của bạn không hợp lệ hoặc đã hết hạn !!");
            return "redirect:/reset-fail";
        }
        model.addAttribute("token", token);
        return "reset_password";
    }
    @GetMapping("/reset-fail")
    public String showResetPasswordFail(Model model) {
        model.addAttribute("msg", "Liên kết của bạn không hợp lệ hoặc đã hết hạn !!");
        return "message";

    }
    @PostMapping("/reset-password")
    public String showResetPassword(@RequestParam String token ,HttpSession session, Model model,@RequestParam String password){
        AccountEntity userByToken = accountService.getUserByToken(token);
        if (userByToken == null) {
            model.addAttribute("errorMsg", "Liên kết của bạn không hợp lệ hoặc đã hết hạn !!");
            return "message";
        } else {
            userByToken.setPassword(bCryptPasswordEncoder.encode(password));
            userByToken.setResetToken(null);
            accountService.updateUser(userByToken);
            model.addAttribute("msg", "Đổi mật khẩu thành công");
            return "message";
        }
    }

}
