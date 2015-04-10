package ark.chr.web.organizer.services.impl;

import ark.chr.web.organizer.dao.api.IOrganizerUserDao;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.IRegisterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Arek
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceTest {

    @InjectMocks
    private IRegisterService sut = new RegisterService();

    @Mock
    private IOrganizerUserDao userDao;

    public RegisterServiceTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldInsertNewUserWithActiveFalseAndReturnTrue() {
        //given
        when(userDao.findUserByLogin(any(String.class))).thenReturn(null);
        OrganizerUser newUser = new OrganizerUser();
        newUser.setId(1L);
        newUser.setLogin("testLogin");
        newUser.setName("Name");
        newUser.setPassword("Pass");
        //when
        boolean result = sut.registerUser(newUser);
        //then
        assertThat(result).isEqualTo(true);
        assertThat(newUser.isActive()).isEqualTo(false);
    }

    @Test
    public void shouldReturnFalseWhenUsernameIsInDatabase() {
        //given
        OrganizerUser newUser = new OrganizerUser();
        newUser.setId(1L);
        newUser.setLogin("testLogin");
        newUser.setName("Name");
        newUser.setPassword("Pass");
        newUser.setActive(true);
        when(userDao.findUserByLogin(any(String.class))).thenReturn(newUser);
        //when
        boolean result = sut.registerUser(newUser);
        //then
        assertThat(result).isEqualTo(false);
        assertThat(newUser.isActive()).isEqualTo(true);
    }

}
