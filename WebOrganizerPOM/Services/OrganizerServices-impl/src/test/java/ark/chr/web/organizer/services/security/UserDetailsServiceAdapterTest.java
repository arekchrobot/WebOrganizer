package ark.chr.web.organizer.services.security;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import ark.chr.web.organizer.dao.api.IOrganizerUserDao;
import ark.chr.web.organizer.model.OrganizerRole;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.model.Permission;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.wkr.fluentrule.api.FluentExpectedException;

/**
 *
 * @author Arek
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceAdapterTest {

    @InjectMocks
    private UserDetailsService sut = new UserDetailsServiceAdapter();

    @Mock
    private IOrganizerUserDao userDao;

    @Rule
    public FluentExpectedException fluentThrown = FluentExpectedException.none();

    public UserDetailsServiceAdapterTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldReturnUserDetailsWhenUserIsNotNullAndHaveRoles() {
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

        OrganizerUser newUser = new OrganizerUser();
        newUser.setId(1L);
        newUser.setLogin("testLogin");
        newUser.setName("Name");
        newUser.setPassword("Pass");
        newUser.setActive(true);
        newUser.getRoles().add(role1);
        newUser.getRoles().add(role2);

        when(userDao.findUserByLogin(any(String.class))).thenReturn(newUser);
        //when
        UserDetails result = sut.loadUserByUsername(newUser.getLogin());
        //then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(newUser.getLogin());
        assertThat(result.getPassword()).isEqualTo(newUser.getPassword());
    }

    @Test
    public void shouldThrowUsernameNotFoundExcetionWhenUserIsNull() {
        //given
        OrganizerUser newUser = new OrganizerUser();
        newUser.setId(1L);
        newUser.setLogin("testLogin");
        newUser.setName("Name");
        newUser.setPassword("Pass");
        newUser.setActive(true);

        when(userDao.findUserByLogin(any(String.class))).thenReturn(null);
        //when
        fluentThrown
                .expect(UsernameNotFoundException.class)
                .hasMessage("No such user: " + newUser.getLogin());
        sut.loadUserByUsername(newUser.getLogin());
        //then
    }

    @Test
    public void shouldThrowUsernameNotFoundExcetionWhenUserHasNoAuthorities() {
        //given
        OrganizerUser newUser = new OrganizerUser();
        newUser.setId(1L);
        newUser.setLogin("testLogin");
        newUser.setName("Name");
        newUser.setPassword("Pass");
        newUser.setActive(true);

        when(userDao.findUserByLogin(any(String.class))).thenReturn(newUser);
        //when
        fluentThrown
                .expect(UsernameNotFoundException.class)
                .hasMessage("User " + newUser.getLogin() + " has no authorities");
        sut.loadUserByUsername(newUser.getLogin());
        //then
    }

}
