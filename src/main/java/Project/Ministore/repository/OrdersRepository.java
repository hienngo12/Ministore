package Project.Ministore.repository;
import Project.Ministore.Entity.OrdersEntity;
import Project.Ministore.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {
List<OrdersEntity> findByAccountEntityId(int accountId);
    Page<OrdersEntity> findAll(Pageable pageable);
    Optional<OrdersEntity> findByOrderId(String orderId);
}
