package Project.Ministore.service.impl;
import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.AccountRepository;
import Project.Ministore.service.AccountService;
//import Project.Ministore.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountEntity saveUser(AccountEntity user) {
        user.setRole("ROLE_USER");
        user.setEnable(true);
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        AccountEntity saveUser = accountRepository.save(user);
        return saveUser;
    }

    @Override
    public AccountEntity getUserByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public List<AccountEntity> getUsers(String role) {
        return accountRepository.findByRole(role);
    }

    @Override
    public Boolean updateAccountStatus(Integer id, Boolean enable) {
        Optional<AccountEntity> findByUser = accountRepository.findById(id);
        if (findByUser.isPresent()){
            AccountEntity user = findByUser.get();
            user.setEnable(enable);
            accountRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void resetAttempt(int accountId) {

    }

    @Override
    public void updateUserResetToken(String email, String resetToken) {
        AccountEntity findByEmail = accountRepository.findByEmail(email);
        findByEmail.setResetToken(resetToken);
        accountRepository.save(findByEmail);
    }

    @Override
    public AccountEntity getUserByToken(String token) {
        return accountRepository.findByResetToken(token);
    }

    @Override
    public AccountEntity updateUser(AccountEntity user) {
        return accountRepository.save(user);
    }

    @Override
    public Page<AccountEntity> searchAccount(String keyword, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 8);
        return this.accountRepository.searchAccount(keyword, pageable);
    }

    @Override
    public Page<AccountEntity> getAllAccount(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,8);// Số sản phẩm mỗi trang
        return this.accountRepository.findAll(pageable);
    }

    @Override
    public AccountEntity updateUserProfile(AccountEntity user, MultipartFile profileImage) {
        return null;
    }

//    @Override
//    public AccountEntity updateUserProfile(AccountEntity user, MultipartFile profileImage) {
//        // Tìm người dùng hiện tại trong cơ sở dữ liệu
//        AccountEntity existingUser = accountRepository.findById(user.getId()).orElse(null);
//        if (existingUser == null) {
//            return null;
//        }
//        // Cập nhật các thuộc tính
//        existingUser.setName(user.getName());
//        existingUser.setPhone(user.getPhone());
//        existingUser.setAddress(user.getAddress());
//        existingUser.setCity(user.getCity());
//        existingUser.setProvince(user.getProvince());
//        existingUser.setPincode(user.getPincode());
//        // Cập nhật ảnh đại diện nếu có tải lên
//        try {
//            if (profileImage != null && !profileImage.isEmpty()) {
//                existingUser.setProfileImage(profileImage.getBytes());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        // Lưu người dùng đã cập nhật
//        return accountRepository.save(existingUser);
//    }

}

