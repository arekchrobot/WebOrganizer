package ark.chr.web.organizer.services.api;

import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Arek
 */
public interface IEventService {

    boolean addNewEvent(OrganizerEvent event);
    
    List<OrganizerEvent> findAllEventsForUser(OrganizerUser user);
    
    OrganizerEvent findEventByNameStartDateAndUser(String name, Date startDate, OrganizerUser user);
    
    void saveEventChanges(OrganizerEvent event);
    
    List<OrganizerEvent> createNotificationsForEvents(OrganizerUser user);
    
    int getDaysDifferenceFromCurrentTime(Date date);
}
