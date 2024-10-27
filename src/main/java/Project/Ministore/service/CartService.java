package Project.Ministore.service;

import Project.Ministore.Entity.CartEntity;

import java.util.List;

public interface CartService {
    public CartEntity saveCart(int productId, int accountId);
    public List<CartEntity> getCartByUser(int accountId);
    public int getCountCart(int accountId);
    public void updateQuantity(String sy, int cid);
    public Long getTotalCart(int accountId);
    public void clearCart(int accountId);

}
