package ark.chr.web.organizer.controllers;

import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.model.Priority;
import ark.chr.web.organizer.services.api.IEventService;
import ark.chr.web.organizer.services.security.UserDetailsAdapter;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Arek
 */
@Named("events")
@Scope("request")
public class Events {
    
    private static final Logger logger = LoggerFactory.getLogger(Events.class);
    private static final String SUCCESS_PAGE = "home.xhtml?faces-redirect=true";

    private OrganizerEvent event;

    @Inject
    private IEventService eventService;
    
    public Events() {
        event = new OrganizerEvent();
    }

    public OrganizerEvent getEvent() {
        return event;
    }

    public void setEvent(OrganizerEvent event) {
        this.event = event;
    }
    
    public Priority[] getPriorities() {
        return Priority.values();
    }

    public String addNewEvent() {
        OrganizerUser user = ((UserDetailsAdapter)SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).getUser();
        event.setOwner(user);
        //TODO poprawic!!!
        event.setCustomReminder(3);
        logger.info("Adding new event for user: " + user.getLogin());
        boolean success = eventService.addNewEvent(event);
        if (success) {
            logger.info("Successfully added new event for user: " + user.getLogin());
            return SUCCESS_PAGE;
        } else {
            logger.warn("Error adding new event for user: " + user.getLogin());
            return null;
        }
    }
    
    public String editEvent(OrganizerEvent event) {
        logger.info("Saving changes to event: " + event.getId());
        eventService.saveEventChanges(event);
        return SUCCESS_PAGE;
    }
}
