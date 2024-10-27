package Project.Ministore.repository;

import Project.Ministore.Entity.OrdersAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersAddessRepository extends JpaRepository<OrdersAddressEntity, Integer> {
    
}
