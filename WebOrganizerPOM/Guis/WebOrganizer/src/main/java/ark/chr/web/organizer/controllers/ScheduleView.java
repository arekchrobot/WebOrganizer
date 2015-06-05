package ark.chr.web.organizer.controllers;

import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.IEventService;
import ark.chr.web.organizer.services.security.UserDetailsAdapter;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Arek
 */
@Named("scheduleView")
@Scope("view")
public class ScheduleView implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleView.class);
    
    private ScheduleModel schedule;
    
    private OrganizerEvent event;
    
    @Inject
    private IEventService eventService;
    
    @PostConstruct
    public void init() {
        OrganizerUser user = ((UserDetailsAdapter)SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).getUser();
        List<OrganizerEvent> allUserEvents = eventService.findAllEventsForUser(user);
        schedule = new DefaultScheduleModel();
        for (OrganizerEvent allUserEvent : allUserEvents) {
            schedule.addEvent(new DefaultScheduleEvent(allUserEvent.getName(), 
                    allUserEvent.getEventDateStart(), allUserEvent.getEventDateEnd()));
        }
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleModel schedule) {
        this.schedule = schedule;
    }

    public OrganizerEvent getEvent() {
        return event;
    }

    public void setEvent(OrganizerEvent event) {
        this.event = event;
    }
    
    public void onEventSelect(SelectEvent selectEvent) {
        ScheduleEvent selected = (ScheduleEvent) selectEvent.getObject();
        event = eventService.findEventByNameStartDateAndUser(selected.getTitle(), 
                selected.getStartDate(), ((UserDetailsAdapter)SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).getUser());
        logger.info("Showing details for event: " + event.getId());
    }
}
