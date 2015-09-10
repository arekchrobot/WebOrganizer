package ark.chr.web.organizer.dao.impl;

import static org.assertj.core.api.Assertions.*;

import ark.chr.web.organizer.dao.api.IOrganizerEventDao;
import ark.chr.web.organizer.dao.api.IOrganizerUserDao;
import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.model.Priority;
import java.util.Date;
import java.util.List;
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
    
    private final long ONE_DAY_MILLIS = 86400000;
    
    @Inject
    private IOrganizerEventDao sut;
    
    @Inject
    private IOrganizerUserDao userDao;
    
    private OrganizerUser user;
    private OrganizerUser user2;
    
    private OrganizerEvent event1;
    private OrganizerEvent event2;
    private OrganizerEvent event3;
    
    private Date dateStart;
    
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
        Date now = new Date();
        user = new OrganizerUser();
        user.setActive(true);
        user.setDateCreated(new Date());
        user.setLogin("login");
        user.setName("name");
        user.setPassword("pass");
        
        user2 = new OrganizerUser();
        user2.setActive(true);
        user2.setDateCreated(new Date());
        user2.setLogin("login2");
        user2.setName("name2");
        user2.setPassword("pass2");
        
        userDao.insert(user);
        userDao.insert(user2);
        
        event1 = new OrganizerEvent();
        event1.setAddress("ad");
        event1.setCustomReminder(1);
        event1.setDescription("desc");
        event1.setEventDateEnd(new Date());
        event1.setEventDateStart(new Date(now.getTime() - ONE_DAY_MILLIS));
        event1.setName("name");
        event1.setOwner(user);
        event1.setPriority(Priority.HIGH);
        
        event2 = new OrganizerEvent();
        event2.setAddress("ad2");
        event2.setCustomReminder(1);
        event2.setDescription("desc2");
        event2.setEventDateEnd(new Date(now.getTime() + 2*ONE_DAY_MILLIS +20000));
        dateStart = new Date(now.getTime() + ONE_DAY_MILLIS +20000);
        event2.setEventDateStart(dateStart);
        event2.setName("name2");
        event2.setOwner(user);
        event2.setPriority(Priority.HIGH);
        
        event3 = new OrganizerEvent();
        event3.setAddress("ad");
        event3.setCustomReminder(1);
        event3.setDescription("desc");
        event3.setEventDateEnd(new Date());
        event3.setEventDateStart(new Date());
        event3.setName("name");
        event3.setOwner(user2);
        event3.setPriority(Priority.HIGH);
        
        sut.insert(event1);
        sut.insert(event2);
        sut.insert(event3);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void shouldReturnTwoEventsWhenFindAllEventsForUser() {
        //given
        //when
        List<OrganizerEvent> result = sut.findAllEventsForUser(user);
        //then
        assertThat(result)
                .hasSize(2)
                .contains(event1, event2);
    }
    
    @Test
    public void shouldReturnEmptyListWhenFindAllEventsForUser() {
        //given
        OrganizerUser user3 = new OrganizerUser();
        user3.setActive(true);
        user3.setDateCreated(new Date());
        user3.setLogin("login3");
        user3.setName("name3");
        user3.setPassword("pass3");
        
        userDao.insert(user3);
        //when
        List<OrganizerEvent> result = sut.findAllEventsForUser(user3);
        //then
        assertThat(result)
                .isEmpty();
    }

    @Test
    public void shouldReturnCorrectEventWhenFindEventByNameStartDateAndUser() {
        //given
        //when
        OrganizerEvent result = sut.findEventByNameStartDateAndUser("name2", dateStart, user);
        //then
        assertThat(result)
                .isNotNull()
                .isEqualTo(event2);
    }
    
    @Test
    public void shouldReturnOnlyOneEventWhenFindAllEventsForUserWhereStartDateGreaterEqualThanNow() {
        //given
        //when
        List<OrganizerEvent> result = sut.findAllEventsForUserWhereStartDateGreaterEqualThanNow(user);
        //then
        assertThat(result)
                .hasSize(1)
                .contains(event2);
    }
}
