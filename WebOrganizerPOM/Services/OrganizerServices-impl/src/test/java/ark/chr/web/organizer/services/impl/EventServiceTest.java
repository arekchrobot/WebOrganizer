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
    public void shouldInsertNewEventWithCustomReminderSetToDependingPriorityAmount() {
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
        
        OrganizerEvent event2 = new OrganizerEvent();
        event2.setAddress("");
        event2.setCustomReminder(0);
        event2.setDescription("");
        event2.setEventDateEnd(new Date(current.getTime() + ONE_DAY_MILLIS));
        event2.setEventDateStart(current);
        event2.setName("name");
        event2.setPriority(Priority.MEDIUM);
        event2.setOwner(new OrganizerUser());
        
        OrganizerEvent event3 = new OrganizerEvent();
        event3.setAddress("");
        event3.setCustomReminder(0);
        event3.setDescription("");
        event3.setEventDateEnd(new Date(current.getTime() + ONE_DAY_MILLIS));
        event3.setEventDateStart(current);
        event3.setName("name");
        event3.setPriority(Priority.LOW);
        event3.setOwner(new OrganizerUser());
        //when
        sut.addNewEvent(event);
        sut.addNewEvent(event2);
        sut.addNewEvent(event3);
        //then
        assertThat(event.getCustomReminder()).isEqualTo(5);
        assertThat(event2.getCustomReminder()).isEqualTo(3);
        assertThat(event3.getCustomReminder()).isEqualTo(1);
    }
    
     @Test
    public void shouldInsertNewEventWithCustomReminderSetToAmountGivenByUser() {
        //given
        Date current = new Date();
        OrganizerEvent event = new OrganizerEvent();
        event.setAddress("");
        event.setCustomReminder(9);
        event.setDescription("");
        event.setEventDateEnd(new Date(current.getTime() + ONE_DAY_MILLIS));
        event.setEventDateStart(current);
        event.setName("name");
        event.setPriority(Priority.HIGH);
        event.setOwner(new OrganizerUser());
        //when
        sut.addNewEvent(event);
        //then
        assertThat(event.getCustomReminder()).isEqualTo(9);
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
        event.setEventDateStart(new Date(current.getTime() + ONE_DAY_MILLIS));
        event.setName("name");
        event.setPriority(Priority.HIGH);
        event.setOwner(new OrganizerUser());
        
        OrganizerEvent event2 = new OrganizerEvent();
        event2.setAddress("");
        event2.setCustomReminder(0);
        event2.setDescription("");
        event2.setEventDateEnd(new Date(current.getTime() + 3*ONE_DAY_MILLIS));
        event2.setEventDateStart(new Date(current.getTime() + 2*ONE_DAY_MILLIS));
        event2.setName("name");
        event2.setPriority(Priority.MEDIUM);
        event2.setOwner(new OrganizerUser());
        
        List<OrganizerEvent> events = new ArrayList<>();
        events.add(event);
        events.add(event2);
        
        when(eventDao
                .findAllEventsForUserWhereStartDateGreaterEqualThanNow(
                        any(OrganizerUser.class)))
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
        event.setEventDateStart(new Date(current.getTime() + ONE_DAY_MILLIS));
        event.setName("name");
        event.setPriority(Priority.HIGH);
        event.setOwner(new OrganizerUser());
        
        OrganizerEvent event2 = new OrganizerEvent();
        event2.setAddress("1212");
        event2.setCustomReminder(2);
        event2.setDescription("");
        event2.setEventDateEnd(new Date(current.getTime() + 3*ONE_DAY_MILLIS));
        event2.setEventDateStart(new Date(current.getTime() + 2*ONE_DAY_MILLIS));
        event2.setName("name123");
        event2.setPriority(Priority.MEDIUM);
        event2.setOwner(new OrganizerUser());
        
        List<OrganizerEvent> events = new ArrayList<>();
        events.add(event);
        events.add(event2);
        
        when(eventDao
                .findAllEventsForUserWhereStartDateGreaterEqualThanNow(
                        any(OrganizerUser.class)))
                .thenReturn(events);
        //when
        List<OrganizerEvent> result = sut.createNotificationsForEvents(new OrganizerUser());
        //then
        assertThat(result)
                .hasSize(2)
                .contains(event);
    }
    
    @Test
    public void shouldReturnEmptyArrayWhenNoMatchingReminders() {
        //given
        Date current = new Date();
        OrganizerEvent event = new OrganizerEvent();
        event.setAddress("");
        event.setCustomReminder(25);
        event.setDescription("");
        event.setEventDateEnd(new Date(current.getTime() + 3*ONE_DAY_MILLIS));
        event.setEventDateStart(new Date(current.getTime() + 2*ONE_DAY_MILLIS));
        event.setName("name");
        event.setPriority(Priority.HIGH);
        event.setOwner(new OrganizerUser());
        
        OrganizerEvent event2 = new OrganizerEvent();
        event2.setAddress("1212");
        event2.setCustomReminder(7);
        event2.setDescription("");
        event2.setEventDateEnd(new Date(current.getTime() + 3*ONE_DAY_MILLIS));
        event2.setEventDateStart(new Date(current.getTime() + 2*ONE_DAY_MILLIS));
        event2.setName("name123");
        event2.setPriority(Priority.MEDIUM);
        event2.setOwner(new OrganizerUser());
        
        List<OrganizerEvent> events = new ArrayList<>();
        events.add(event);
        events.add(event2);
        
        when(eventDao
                .findAllEventsForUserWhereStartDateGreaterEqualThanNow(
                        any(OrganizerUser.class)))
                .thenReturn(events);
        //when
        List<OrganizerEvent> result = sut.createNotificationsForEvents(new OrganizerUser());
        //then
        assertThat(result)
                .isEmpty();
    }
    
    @Test
    public void shouldReturnOneEventWhenCustomReminderIsSetToOneDayAndPriorityHigh() {
        //given
        Date current = new Date();
        OrganizerEvent event = new OrganizerEvent();
        event.setAddress("");
        event.setCustomReminder(1);
        event.setDescription("");
        event.setEventDateEnd(new Date(current.getTime() + 2*ONE_DAY_MILLIS));
        event.setEventDateStart(new Date(current.getTime() + ONE_DAY_MILLIS));
        event.setName("name");
        event.setPriority(Priority.HIGH);
        event.setOwner(new OrganizerUser());
        
        OrganizerEvent event2 = new OrganizerEvent();
        event2.setAddress("");
        event2.setCustomReminder(0);
        event2.setDescription("");
        event2.setEventDateEnd(new Date(current.getTime() + 3*ONE_DAY_MILLIS));
        event2.setEventDateStart(new Date(current.getTime() + 2*ONE_DAY_MILLIS));
        event2.setName("name");
        event2.setPriority(Priority.MEDIUM);
        event2.setOwner(new OrganizerUser());
        
        List<OrganizerEvent> events = new ArrayList<>();
        events.add(event);
        events.add(event2);
        
        when(eventDao
                .findAllEventsForUserWhereStartDateGreaterEqualThanNow(
                        any(OrganizerUser.class)))
                .thenReturn(events);
        //when
        List<OrganizerEvent> result = sut.createNotificationsForEvents(new OrganizerUser());
        //then
        assertThat(result)
                .hasSize(1)
                .contains(event);
    }
    
}
