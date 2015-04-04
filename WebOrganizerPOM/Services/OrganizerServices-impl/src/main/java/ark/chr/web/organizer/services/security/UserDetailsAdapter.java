package ark.chr.web.organizer.services.security;

import ark.chr.web.organizer.model.OrganizerRole;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.model.Permission;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Arek
 */
public class UserDetailsAdapter implements UserDetails {

    private OrganizerUser user;

    public UserDetailsAdapter(OrganizerUser user) {
        this.user = user;
    }
    
    public OrganizerUser getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        Set<Permission> perms = new HashSet<>();
        for (OrganizerRole role : user.getRoles()) {
            perms.addAll(role.getPermissions());
        }
        authorities.addAll(user.getRoles());
        authorities.addAll(perms);
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
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
        return user.isActive();
    }

    public String getName() {
        return user.getName();
    }
}
