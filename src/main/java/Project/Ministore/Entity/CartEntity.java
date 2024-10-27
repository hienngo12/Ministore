package Project.Ministore.Entity;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    @Column(name = "quantity")
    private int quantity;
    @Transient
    private Long total_price;
    @Transient
    @Column(name = "total_orderPrice")
    private Long total_orderPrice;
public String getFormattedTotalPrice() {
    DecimalFormat df = new DecimalFormat("#,###");
    return df.format(getTotalPrice()) + " ₫";
}
    public Long getTotalPrice() {
        if (productEntity != null && productEntity.getPrice() != null) {
            return productEntity.getPrice() * quantity;
        }
        return 0L;
    }
public String getFormattedTotalOrderPrice() {
    DecimalFormat df = new DecimalFormat("#,###");
    return df.format(total_orderPrice) + " ₫";
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Long total_price) {
        this.total_price = total_price;
    }

    public Long getTotal_orderPrice() {
        return total_orderPrice;
    }

    public void setTotal_orderPrice(Long total_orderPrice) {
        this.total_orderPrice = total_orderPrice;
    }

}
