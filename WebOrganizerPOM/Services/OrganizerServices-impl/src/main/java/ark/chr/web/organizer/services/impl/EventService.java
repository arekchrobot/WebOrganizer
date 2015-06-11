package ark.chr.web.organizer.services.impl;

import ark.chr.web.organizer.dao.api.IOrganizerEventDao;
import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.IEventService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

    private static final int HIGH_PRIOR_DAYS_AMOUNT = 5;
    private static final int MEDIUM_PRIOR_DAYS_AMOUNT = 3;
    private static final int LOW_PRIOR_DAYS_AMOUNT = 1;

    @Inject
    IOrganizerEventDao eventDao;

    @Override
    public boolean addNewEvent(OrganizerEvent event) {
        if (event.getCustomReminder() <= 0) {
            logger.info("Setting auto reminder for event");
            switch (event.getPriority()) {
                case HIGH:
                    event.setCustomReminder(HIGH_PRIOR_DAYS_AMOUNT);
                    break;
                case MEDIUM:
                    event.setCustomReminder(MEDIUM_PRIOR_DAYS_AMOUNT);
                    break;
                case LOW:
                    event.setCustomReminder(LOW_PRIOR_DAYS_AMOUNT);
                    break;
            }
        }
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

    @Override
    public List<OrganizerEvent> createNotificationsForEvents(OrganizerUser user) {
        List<OrganizerEvent> allEvents = eventDao.findAllEventsForUser(user);
        List<OrganizerEvent> result = new ArrayList<>();

        Date currentDate = new Date();
        for (OrganizerEvent event : allEvents) {
            long daysDiff = event.getEventDateStart().getTime() - currentDate.getTime();
            daysDiff = TimeUnit.DAYS.convert(daysDiff, TimeUnit.MILLISECONDS);
            logger.info("Days diff amount: " + daysDiff);
            switch (event.getPriority()) {
                case HIGH:
                    if (daysDiff == HIGH_PRIOR_DAYS_AMOUNT
                            || daysDiff == MEDIUM_PRIOR_DAYS_AMOUNT
                            || daysDiff == LOW_PRIOR_DAYS_AMOUNT) {
                        logger.info("Adding event notification for high priority for user: " 
                                + user.getLogin());
                        result.add(event);
                    }
                    break;
                case MEDIUM:
                    if (daysDiff == MEDIUM_PRIOR_DAYS_AMOUNT
                            || daysDiff == LOW_PRIOR_DAYS_AMOUNT) {
                        logger.info("Adding event notification for medium priority for user: " 
                                + user.getLogin());
                        result.add(event);
                    }
                    break;
                case LOW:
                    if (daysDiff == LOW_PRIOR_DAYS_AMOUNT) {
                        logger.info("Adding event notification for low priority for user: " 
                                + user.getLogin());
                        result.add(event);
                    }
                    break;
            }
            if (daysDiff == event.getCustomReminder() && !result.contains(event)) {
                logger.info("Adding event notification custom reminder for user: " 
                                + user.getLogin());
                result.add(event);
            }
        }

        return result;
    }

}
