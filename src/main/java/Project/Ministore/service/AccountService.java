package Project.Ministore.service;


import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {
    public static AccountEntity saveAdmin(AccountEntity user) {
        return null;
    }

    AccountEntity saveUser(AccountEntity user);
    public AccountEntity getUserByEmail(String email);
    List<AccountEntity> getUsers(String role);
    public Boolean updateAccountStatus(Integer id, Boolean enable);
    public void resetAttempt(int accountId);
    public void updateUserResetToken(String email, String resetToken);
    public AccountEntity getUserByToken(String token);
    public AccountEntity updateUser(AccountEntity user);
    public Page<AccountEntity> searchAccount(String keyword, Integer pageNo);
    public Page<AccountEntity> getAllAccount(Integer pageNo);

    AccountEntity updateUserProfile(AccountEntity user, MultipartFile profileImage);
}
