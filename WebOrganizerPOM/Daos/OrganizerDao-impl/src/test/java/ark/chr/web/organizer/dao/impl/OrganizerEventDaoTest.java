package ark.chr.web.organizer.dao.impl;

import static org.assertj.core.api.Assertions.*;

import ark.chr.web.organizer.dao.api.IOrganizerEventDao;
import ark.chr.web.organizer.dao.api.IOrganizerUserDao;
import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import java.util.Date;
import javax.inject.Inject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
public class OrganizerEventDaoTest {
    
    @Inject
    private IOrganizerEventDao sut;
    
    @Inject
    private IOrganizerUserDao userDao;
    
    private OrganizerUser user;
    
    private OrganizerEvent event1;
    private OrganizerEvent event2;
    
    public OrganizerEventDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        user = new OrganizerUser();
        user.setActive(true);
        user.setDateCreated(new Date());
        user.setLogin("login");
        user.setName("name");
        user.setPassword("pass");
        
        userDao.insert(user);
        
        event1 = new OrganizerEvent();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void shouldReturnTwoEventsForUser() {
        fail("fail");
    }

    @Test
    public void shouldReturnCorrectEventWhenFindEventByNameStartDateAndUser() {
        fail("fail");
    }
    
}
