package Project.Ministore.service;

import Project.Ministore.Dto.OrdersAddressEntityDto;
import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.Entity.OrdersAddressEntity;
import Project.Ministore.Entity.OrdersEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrdersService {
public void saveOrder(int accountId, OrdersAddressEntityDto ordersAddressEntityDto);
public List<OrdersEntity> getOrdersByUser(int accountId);
public Boolean updateOrderStatus(int id, String status);
public List<OrdersEntity> getAllOrders();

    List<OrdersEntity> searchOrders(String orderId);
}
