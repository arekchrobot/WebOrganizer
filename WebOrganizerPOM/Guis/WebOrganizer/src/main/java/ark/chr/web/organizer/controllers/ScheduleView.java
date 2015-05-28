package ark.chr.web.organizer.controllers;

import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.IEventService;
import ark.chr.web.organizer.services.security.UserDetailsAdapter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
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
@Scope("session")
public class ScheduleView implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleView.class);
    
    private ScheduleModel schedule;
    
    @Inject
    private IEventService eventService;
    
    @PostConstruct
    public void init() {
        OrganizerUser user = ((UserDetailsAdapter)SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).getUser();
        List<OrganizerEvent> allUserEvents = eventService.getAllEventsForUser(user);
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
}
