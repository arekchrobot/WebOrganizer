package ark.chr.web.organizer.services.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import ark.chr.web.organizer.dao.api.IOrganizerEventDao;
import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.model.Priority;
import ark.chr.web.organizer.services.api.IEventService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
public class EventServiceTest {
    
    private final long ONE_DAY_MILLIS = 86400000;
    
    @InjectMocks
    private IEventService sut = new EventService();
    
    @Mock
    private IOrganizerEventDao eventDao;
    
    public EventServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void shouldInsertNewEventWithCustomReminderSetToHighPrioritzAmount() {
        //given
        Date current = new Date();
        OrganizerEvent event = new OrganizerEvent();
        event.setAddress("");
        event.setCustomReminder(0);
        event.setDescription("");
        event.setEventDateEnd(new Date(current.getTime() + ONE_DAY_MILLIS));
        event.setEventDateStart(current);
        event.setName("name");
        event.setPriority(Priority.HIGH);
        event.setOwner(new OrganizerUser());
        //when
        sut.addNewEvent(event);
        //then
        assertThat(event.getCustomReminder()).isEqualTo(5);
    }
    
    @Test
    public void shouldReturnOneEvent() {
        //given
        Date current = new Date();
        OrganizerEvent event = new OrganizerEvent();
        event.setAddress("");
        event.setCustomReminder(0);
        event.setDescription("");
        event.setEventDateEnd(new Date(current.getTime() + 2*ONE_DAY_MILLIS));
        event.setEventDateStart(new Date(current.getTime() + ONE_DAY_MILLIS + 5000));
        event.setName("name");
        event.setPriority(Priority.HIGH);
        event.setOwner(new OrganizerUser());
        
        OrganizerEvent event2 = new OrganizerEvent();
        event2.setAddress("");
        event2.setCustomReminder(0);
        event2.setDescription("");
        event2.setEventDateEnd(new Date(current.getTime() + 3*ONE_DAY_MILLIS));
        event2.setEventDateStart(new Date(current.getTime() + 2*ONE_DAY_MILLIS + 5000));
        event2.setName("name");
        event2.setPriority(Priority.MEDIUM);
        event2.setOwner(new OrganizerUser());
        
        List<OrganizerEvent> events = new ArrayList<>();
        events.add(event);
        events.add(event2);
        
        when(eventDao.findAllEventsForUser(any(OrganizerUser.class)))
                .thenReturn(events);
        //when
        List<OrganizerEvent> result = sut.createNotificationsForEvents(new OrganizerUser());
        //then
        assertThat(result)
                .hasSize(1)
                .contains(event);
    }
    
    @Test
    public void shouldReturnTwoEventsWithOneCustom() {
        //given
        Date current = new Date();
        OrganizerEvent event = new OrganizerEvent();
        event.setAddress("");
        event.setCustomReminder(5);
        event.setDescription("");
        event.setEventDateEnd(new Date(current.getTime() + 2*ONE_DAY_MILLIS));
        event.setEventDateStart(new Date(current.getTime() + ONE_DAY_MILLIS + 20000));
        event.setName("name");
        event.setPriority(Priority.HIGH);
        event.setOwner(new OrganizerUser());
        
        OrganizerEvent event2 = new OrganizerEvent();
        event2.setAddress("1212");
        event2.setCustomReminder(2);
        event2.setDescription("");
        event2.setEventDateEnd(new Date(current.getTime() + 3*ONE_DAY_MILLIS));
        event2.setEventDateStart(new Date(current.getTime() + 2*ONE_DAY_MILLIS + 20000));
        event2.setName("name123");
        event2.setPriority(Priority.MEDIUM);
        event2.setOwner(new OrganizerUser());
        
        List<OrganizerEvent> events = new ArrayList<>();
        events.add(event);
        events.add(event2);
        
        when(eventDao.findAllEventsForUser(any(OrganizerUser.class)))
                .thenReturn(events);
        //when
        List<OrganizerEvent> result = sut.createNotificationsForEvents(new OrganizerUser());
        //then
        assertThat(result)
                .hasSize(2)
                .contains(event);
    }
    
}
