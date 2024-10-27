package Project.Ministore.Entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "orders_address")
public class OrdersAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Họ & Tên  không thể rỗng hoặc trống")
    @Column(name = "name",nullable = false)
    private String name;
    @Email(message = "Email phải hợp lệ")
    @NotBlank(message = "Email không thể rỗng hoặc trống")
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private int phone;
    @NotBlank(message = "Địa chỉ không thể rỗng hoặc trống")
    @Column(name = "address")
    private String address;
    @NotBlank(message = "Thành phố không thể rỗng hoặc trống")
    @Column(name = "city")
    private String city;
    @NotBlank(message = "Tỉnh không thể rỗng hoặc trống")
    @Column(name = "province")
    private String province;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
