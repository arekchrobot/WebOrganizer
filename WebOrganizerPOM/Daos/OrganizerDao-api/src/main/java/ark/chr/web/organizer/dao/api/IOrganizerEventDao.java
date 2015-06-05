package ark.chr.web.organizer.dao.api;

import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Arek
 */
public interface IOrganizerEventDao extends ICrudDao<OrganizerEvent> {

    List<OrganizerEvent> findAllEventsForUser(OrganizerUser user);
    
    OrganizerEvent findEventByNameStartDateAndUser(String name, Date startDate, OrganizerUser user);
}
