package Project.Ministore.Config;
import Project.Ministore.Entity.AccountEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

public class MyAccountDetails implements UserDetails {

private AccountEntity account;

    public MyAccountDetails(AccountEntity account) {
        super();
        this.account = account;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(account.getRole());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.getEnable();
    }

    private AccountEntity accountEntity; // Đối tượng thực thể được ánh xạ từ cơ sở dữ liệu

    public AccountEntity getAccountEntity() {
        return this.accountEntity;
    }
}
