package Project.Ministore.Entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_name")
    private String product_name;
    @Column(name = "description")
    private String description;
    @Column(name = "price" )
    private Long price;
    @Column(name = "import_price")
    private Long import_price;
    @Column(name = "stock_quantity")
    private int stock_quantity;
    @Column(name = "image",nullable = false)
    @Lob
    private byte[] image;
    @Column(name = "origin")
    private String origin;
    @Column(name = "discount")
    private int discount;
    @Column(name = "discount_price")
    private Long discount_price;
    @Column(name = "is_active")
    private Boolean active;
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartEntity> cartEntities;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id",nullable = true)
    private CategoryEntity categoryEntity;
    public String getFormattedPrice() {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(price) + " ₫";
    }
    public String getFormattedImportPrice(){
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(import_price) + "₫";
    }
    public String getFormattedDiscountPrice() {
        // Kiểm tra nếu price là null hoặc discount là giá trị không hợp lệ
        if (price == null || discount < 0) {
            return "Giá không xác định";
        }

        // Tính toán giá sau khi giảm giá
        double discountedPrice = price - (price * discount / 100.0);
        DecimalFormat df = new DecimalFormat("#,###");
        try {
            return df.format(discountedPrice) + " ₫";
        } catch (IllegalArgumentException e) {
            // Log lỗi nếu cần và trả về giá trị mặc định hoặc thông báo lỗi
            return "Giá không hợp lệ";
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(Long discount_price) {
        this.discount_price = discount_price;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getImport_price() {
        return import_price;
    }

    public void setImport_price(Long import_price) {
        this.import_price = import_price;
    }

    public List<CartEntity> getCartEntities() {
        return cartEntities;
    }

    public void setCartEntities(List<CartEntity> cartEntities) {
        this.cartEntities = cartEntities;
    }
    public boolean isInStock() {
        return stock_quantity > 0;
    }
}
