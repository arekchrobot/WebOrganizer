package ark.chr.web.organizer.services.security;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import ark.chr.web.organizer.model.OrganizerRole;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.model.Permission;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Arek
 */
public class UserDetailsAdapterTest {

    private OrganizerUser user;
    private UserDetailsAdapter sut;

    public UserDetailsAdapterTest() {
    }

    @Before
    public void setUp() {
        user = new OrganizerUser();
        user.setActive(true);
        user.setId(1L);
        user.setLogin("testLogin");
        user.setName("testName");
        user.setPassword("pass");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldReturnCorrectAuthoritiesForUserWithRoles() {
        //given
        Permission perm1 = new Permission();
        perm1.setId(1L);
        perm1.setName("PERM_1");
        Permission perm2 = new Permission();
        perm2.setId(2L);
        perm2.setName("PERM_2");
        Permission perm3 = new Permission();
        perm3.setId(3L);
        perm3.setName("PERM_3");

        OrganizerRole role1 = new OrganizerRole();
        role1.setId(1L);
        role1.setName("ROLE_1");
        role1.getPermissions().add(perm1);
        role1.getPermissions().add(perm2);

        OrganizerRole role2 = new OrganizerRole();
        role2.setId(2L);
        role2.setName("ROLE_2");
        role2.getPermissions().add(perm3);

        user.getRoles().add(role1);
        user.getRoles().add(role2);

        sut = new UserDetailsAdapter(user);
        //when
        Collection<? extends GrantedAuthority> result = sut.getAuthorities();
        //then
        assertThat(result)
                .hasSize(5)
                .extracting("authority")
                .contains(perm1.getAuthority(), perm2.getAuthority(),
                        perm3.getAuthority(), role1.getAuthority(),
                        role2.getAuthority());
    }

    @Test
    public void shouldReturnEmptyCollectionIfUserHaveNoRoles() {
        //given
        sut = new UserDetailsAdapter(user);
        //when
        Collection<? extends GrantedAuthority> result = sut.getAuthorities();
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnCorrectCredentails() {
        //given
        Permission perm1 = new Permission();
        perm1.setId(1L);
        perm1.setName("PERM_1");
        
        OrganizerRole role1 = new OrganizerRole();
        role1.setId(1L);
        role1.setName("ROLE_1");
        role1.getPermissions().add(perm1);
        
        user.getRoles().add(role1);

        sut = new UserDetailsAdapter(user);
        //when
        OrganizerUser returnedUser = sut.getUser();
        String password = sut.getPassword();
        String name = sut.getName();
        String login = sut.getUsername();
        boolean isEnabled = sut.isEnabled();
        //then
        
        assertThat(returnedUser).isSameAs(user);
        assertThat(password).isEqualTo(user.getPassword());
        assertThat(name).isEqualTo(user.getName());
        assertThat(login).isEqualTo(user.getLogin());
        assertThat(isEnabled).isEqualTo(user.isActive());
        assertTrue(sut.isAccountNonExpired());
        assertTrue(sut.isAccountNonLocked());
        assertTrue(sut.isCredentialsNonExpired());
    }
}
