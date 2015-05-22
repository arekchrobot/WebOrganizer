package ark.chr.web.organizer.controllers;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Arek
 */
@Named("scheduleView")
@Scope("session")
public class ScheduleView implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleView.class);
    
    private ScheduleModel schedule;
    
    @PostConstruct
    public void init() {
        schedule = new DefaultScheduleModel();
        schedule.addEvent(new DefaultScheduleEvent("Champions League Match", new Date(), new Date()));
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleModel schedule) {
        this.schedule = schedule;
    }
}
