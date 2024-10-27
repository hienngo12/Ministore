package Project.Ministore.Entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "order_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "price")
    private Long price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "status")
    private String status;
    @Column(name = "payment_type")
    private String payment_type;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_address_id")
    private OrdersAddressEntity ordersAddressEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;
    public String getFormattedXPrice() {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(quantity * price) + " ₫";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public OrdersAddressEntity getOrdersAddressEntity() {
        return ordersAddressEntity;
    }

    public void setOrdersAddressEntity(OrdersAddressEntity ordersAddressEntity) {
        this.ordersAddressEntity = ordersAddressEntity;
    }
    public String getStatusText() {
        switch (this.status) {
            case "1":
                return "Đang tiến hành";
            case "2":
                return "Đã nhận được đơn hàng";
            case "3":
                return "Sản phẩm được đóng gói";
            case "4":
                return "Ra ngoài để giao hàng";
            case "5":
                return "Đã giao hàng";
            case "6":
                return "Huỷ đơn hàng";
            default:
                return "Trạng thái không xác định";
        }
    }

}

