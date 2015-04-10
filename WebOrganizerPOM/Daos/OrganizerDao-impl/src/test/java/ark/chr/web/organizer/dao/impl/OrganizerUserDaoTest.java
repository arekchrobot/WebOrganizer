package ark.chr.web.organizer.dao.impl;

import ark.chr.web.organizer.dao.api.IOrganizerRoleDao;
import static org.assertj.core.api.Assertions.*;

import ark.chr.web.organizer.dao.api.IOrganizerUserDao;
import ark.chr.web.organizer.model.OrganizerRole;
import ark.chr.web.organizer.model.OrganizerUser;
import java.util.Date;
import javax.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arek
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-test.xml")
@Transactional
public class OrganizerUserDaoTest {

    @Inject
    private IOrganizerUserDao sut;

    private OrganizerUser user1;
    private final static String PASSWORD = "password";
    private final static String LOGIN = "test@gmail.com";
    private final static String ROLE_NAME = "ROLE_USER";

    @Inject
    private IOrganizerRoleDao organzerRoleDao;

    public OrganizerUserDaoTest() {
    }

    @Before
    public void setUp() {
        user1 = new OrganizerUser();
        user1.setActive(true);
        user1.setDateCreated(new Date());
        user1.setLogin(LOGIN);
        user1.setName("testName");
        user1.setPassword(PASSWORD);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldEncodePasswordAndAddRoleWhileInsertingUserAndRoleIsNotNull() {
        //given
        OrganizerRole role = new OrganizerRole();
        role.setName(ROLE_NAME);

        organzerRoleDao.insert(role);
        //when
        sut.insert(user1);
        //then
        assertThat(user1.getPassword())
                .isNotEqualTo(PASSWORD);
        assertThat(user1.getRoles())
                .isNotEmpty()
                .hasSize(1)
                .doesNotContainNull();
    }

    @Test
    public void shouldHaveEmptyRolesCollectionWhenNoRoleInDatabaseWhenInserting() {
        //given
        //when
        sut.insert(user1);
        //then
        assertThat(user1.getRoles())
                .isEmpty();
    }

    @Test
    public void shouldReturnCorrectUserWhenUserIsInDatabaseByFindByUsername() {
        //given
        sut.insert(user1);
        //when
        OrganizerUser resultUser = sut.findUserByLogin(LOGIN);
        //then
        assertThat(resultUser)
                .isNotNull();
        assertThat(resultUser.getLogin())
                .isEqualTo(user1.getLogin());
        assertThat(resultUser.getDateCreated())
                .isEqualTo(user1.getDateCreated());
    }

    @Test
    public void shouldReturnNullWhenUserIsNotInDatabaseByFindByUsername() {
        //given
        //when
        OrganizerUser resultUser = sut.findUserByLogin(LOGIN);
        //then
        assertThat(resultUser)
                .isNull();
    }
}
