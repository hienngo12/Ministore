package Project.Ministore.service.impl;

import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.Entity.CartEntity;
import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.AccountRepository;
import Project.Ministore.repository.CartRepository;
import Project.Ministore.repository.ProductRepository;
import Project.Ministore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartEntity saveCart(int productId, int accountId) {
        AccountEntity user = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tìm thấy"));
        CartEntity cartStatus = cartRepository.findByProductEntity_IdAndAccountEntity_Id(productId, accountId);

        CartEntity cart;
        if (ObjectUtils.isEmpty(cartStatus)) {
            // Create new cart
            cart = new CartEntity();
            cart.setProductEntity(product);
            cart.setAccountEntity(user);
            cart.setQuantity(1); // Set default quantity to 1

            // Check stock quantity
            if (product.getStock_quantity() < 1) {
                throw new RuntimeException("Hàng hóa không đủ cho sản phẩm: " + product.getProduct_name());
            }

            // Calculate price
            Long discountPrice = product.getDiscount_price() != null ? product.getDiscount_price() : product.getPrice();
            cart.setTotal_price(1 * discountPrice); // Calculate price for 1 product
        } else {
            // Update existing cart
            cart = cartStatus;
            int newQuantity = cart.getQuantity() + 1;

            // Check stock quantity
            if (product.getStock_quantity() < newQuantity) {
                throw new RuntimeException("Hàng hóa không đủ cho sản phẩm: " + product.getProduct_name());
            }

            cart.setQuantity(newQuantity); // Increase quantity by 1
            // Calculate price
            Long discountPrice = cart.getProductEntity().getDiscount_price() != null ? cart.getProductEntity().getDiscount_price() : cart.getProductEntity().getPrice();
            cart.setTotal_price(cart.getQuantity() * cart.getProductEntity().getPrice());
        }

        return cartRepository.save(cart);
    }

    @Override
    public List<CartEntity> getCartByUser(int accountId) {
        List<CartEntity> carts = cartRepository.findByAccountEntity_Id(accountId);
        Long totalOrderPrice = 0L;

        for (CartEntity cart : carts) {
            cart.setTotal_price(cart.getQuantity() * cart.getProductEntity().getPrice());
            totalOrderPrice += cart.getTotalPrice();
        }
        if (!carts.isEmpty()) {
            carts.get(carts.size() -1).setTotal_orderPrice(totalOrderPrice);
        }

        return carts;
    }

    @Override
    public int getCountCart(int accountId) {
        return cartRepository.countByAccountEntity_Id(accountId);
    }

    @Override
    public void updateQuantity(String sy, int cid) {
        CartEntity cart = cartRepository.findById(cid)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tìm thấy"));
        ProductEntity product = cart.getProductEntity();

        int updateQuantity;
        if (sy.equalsIgnoreCase("de")) {
            updateQuantity = cart.getQuantity() - 1;
            if (updateQuantity <= 0) {
                cartRepository.delete(cart);
            } else {
                cart.setQuantity(updateQuantity);
                // Update total price when quantity changes
                Long discountPrice = cart.getProductEntity().getDiscount_price() != null ? cart.getProductEntity().getDiscount_price() : cart.getProductEntity().getPrice();
                cart.setTotal_price(updateQuantity * discountPrice);
                cartRepository.save(cart);
            }
        } else {
            updateQuantity = cart.getQuantity() + 1;

            // Check stock quantity
            if (product.getStock_quantity() < updateQuantity) {
                throw new RuntimeException("Hàng hóa không đủ cho sản phẩm: " + product.getProduct_name());
            }

            cart.setQuantity(updateQuantity);
            // Update total price when quantity changes
            Long discountPrice = cart.getProductEntity().getDiscount_price() != null ? cart.getProductEntity().getDiscount_price() : cart.getProductEntity().getPrice();
            cart.setTotal_price(updateQuantity * discountPrice);
            cartRepository.save(cart);
        }
    }

    @Override
    public Long getTotalCart(int accountId) {
        List<CartEntity> carts = getCartByUser(accountId);
        Long totalPrice = 0L;

        if (!carts.isEmpty()) {
            totalPrice = carts.get(carts.size() - 1).getTotal_orderPrice() + 25000; // Add shipping fee (assumed to be 25000)
        }

        return totalPrice;
    }

    @Override
    public void clearCart(int accountId) {
        List<CartEntity> carts = cartRepository.findByAccountEntity_Id(accountId);
        for (CartEntity cart : carts) {
            cartRepository.delete(cart);
        }
    }
}