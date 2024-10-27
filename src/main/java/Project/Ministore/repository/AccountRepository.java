package Project.Ministore.repository;
import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
     @Query("SELECT u FROM AccountEntity u WHERE u.email = :email")
    public AccountEntity findByEmail(@Param("email") String email);
     public List<AccountEntity> findByRole(String role);
     public AccountEntity findByResetToken(String token);
    @Query("SELECT a FROM AccountEntity a WHERE a.name LIKE %?1%")
    Page<AccountEntity> searchAccount(String keyword, Pageable pageable);

}
