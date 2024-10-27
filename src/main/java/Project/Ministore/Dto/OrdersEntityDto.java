package Project.Ministore.Dto;

import Project.Ministore.Entity.OrdersEntity;

public class OrdersEntityDto {
    private OrdersEntity orders;
    private String formattedOrderInventory;
    private String formattedProductEnterPrice;
    private String formattedProductSellPrice;
    private String formattedProductRevenuePrice;
    private int quantity;
    public OrdersEntity getOrders() {
        return orders;
    }

    public void setOrders(OrdersEntity orders) {
        this.orders = orders;
    }

    public String getFormattedOrderInventory() {
        return formattedOrderInventory;
    }

    public void setFormattedOrderInventory(String formattedOrderInventory) {
        this.formattedOrderInventory = formattedOrderInventory;
    }

    public String getFormattedProductEnterPrice() {
        return formattedProductEnterPrice;
    }

    public void setFormattedProductEnterPrice(String formattedProductEnterPrice) {
        this.formattedProductEnterPrice = formattedProductEnterPrice;
    }

    public String getFormattedProductSellPrice() {
        return formattedProductSellPrice;
    }

    public void setFormattedProductSellPrice(String formattedProductSellPrice) {
        this.formattedProductSellPrice = formattedProductSellPrice;
    }

    public String getFormattedProductRevenuePrice() {
        return formattedProductRevenuePrice;
    }

    public void setFormattedProductRevenuePrice(String formattedProductRevenuePrice) {
        this.formattedProductRevenuePrice = formattedProductRevenuePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
