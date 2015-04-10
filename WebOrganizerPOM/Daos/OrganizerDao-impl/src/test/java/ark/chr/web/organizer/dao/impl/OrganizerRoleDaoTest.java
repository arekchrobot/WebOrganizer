package ark.chr.web.organizer.dao.impl;

import ark.chr.web.organizer.dao.api.IOrganizerRoleDao;
import static org.assertj.core.api.Assertions.*;

import ark.chr.web.organizer.model.OrganizerRole;
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
public class OrganizerRoleDaoTest {

    @Inject
    private IOrganizerRoleDao sut;

    private OrganizerRole role;
    private final static String ROLE_NAME = "ROLE_USER";

    public OrganizerRoleDaoTest() {
    }

    @Before
    public void setUp() {
        role = new OrganizerRole();
        role.setName(ROLE_NAME);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldReturnCorrectRoleWhenRoleIsInDatabaseByFindRoleByName() {
        //given
        sut.insert(role);
        //when
        OrganizerRole result = sut.findRoleByName(ROLE_NAME);
        //then
        assertThat(result)
                .isNotNull();
        assertThat(result.getName())
                .isEqualTo(role.getName());
    }

    @Test
    public void shouldReturnNullWhenRoleIsNotInDatabaseByFindRoleByName() {
        //given
        
        //when
        OrganizerRole result = sut.findRoleByName(ROLE_NAME);
        //then
        assertThat(result)
                .isNull();
    }
}
