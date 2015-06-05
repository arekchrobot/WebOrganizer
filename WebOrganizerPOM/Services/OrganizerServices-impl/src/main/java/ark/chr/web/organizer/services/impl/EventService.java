package ark.chr.web.organizer.services.impl;

import ark.chr.web.organizer.dao.api.IOrganizerEventDao;
import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.IEventService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arek
 */
@Service
@Transactional
public class EventService implements IEventService, Serializable {
    
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    
    @Inject
    IOrganizerEventDao eventDao;

    @Override
    public boolean addNewEvent(OrganizerEvent event) {
        eventDao.insert(event);
        return true;
    }

    @Override
    public List<OrganizerEvent> findAllEventsForUser(OrganizerUser user) {
        return eventDao.findAllEventsForUser(user);
    }

    @Override
    public OrganizerEvent findEventByNameStartDateAndUser(String name, Date startDate, OrganizerUser user) {
        return eventDao.findEventByNameStartDateAndUser(name, startDate, user);
    }

    @Override
    public void saveEventChanges(OrganizerEvent event) {
        eventDao.update(event);
    }

}
