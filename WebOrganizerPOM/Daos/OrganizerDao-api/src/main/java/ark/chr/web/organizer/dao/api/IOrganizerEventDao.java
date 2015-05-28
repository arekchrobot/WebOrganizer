package ark.chr.web.organizer.dao.api;

import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import java.util.List;

/**
 *
 * @author Arek
 */
public interface IOrganizerEventDao extends ICrudDao<OrganizerEvent> {

    List<OrganizerEvent> getAllEventsForUser(OrganizerUser user);
}
