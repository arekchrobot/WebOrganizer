package ark.chr.web.organizer.utils;

import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.IEventService;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Arek
 */
@Component
public class Notifications implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Notifications.class);

    @Inject
    private IEventService eventService;

    private List<FacesMessage> result;

    public List<FacesMessage> createNotificationMessagesForUser(OrganizerUser user) throws ParseException {
        Cookie date = CookiesUtil.getCookie("date");
        Cookie users = CookiesUtil.getCookie("users");

        result = new ArrayList<>();
        Date current = new Date();

        if (date != null && users != null) {
            Set<String> notifiedUsers = new HashSet<>();
            notifiedUsers.addAll(Arrays.asList(users.getValue().split(",")));

            Date lastNotifiedDate = new SimpleDateFormat("EEEE MMMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH).parse(date.getValue());

            if (!notifiedUsers.isEmpty()) {
                if (notifiedUsers.contains(user.getLogin())) {
                    if (lastNotifiedDate.compareTo(current) == 0) {
                        logger.info("Already created notification for user: "
                                + user.getLogin()
                                + " at date: " + lastNotifiedDate.toString());
                    } else {
                        logger.info("User exists in cookie but no notification at date: "
                                + lastNotifiedDate.toString());
                        CookiesUtil.setCookie("date", current.toString());
                        generateJsfNotificationsFromEvents(user, current);
                    }
                } else {
                    logger.info("There are users in cookie but not user with login: "
                            + user.getLogin());
                    CookiesUtil.setCookie("date", current.toString());
                    String newAmountOfUsers = users.getValue() + "," + user.getLogin();
                    CookiesUtil.setCookie("users", newAmountOfUsers);
                    generateJsfNotificationsFromEvents(user, current);
                }
            } else {
                logger.info("No users in cookie");
                CookiesUtil.setCookie("users", user.getLogin());
                CookiesUtil.setCookie("date", current.toString());
                generateJsfNotificationsFromEvents(user, current);
            }
        } else {
            logger.info("No cookies found for notifications.");
            CookiesUtil.setCookie("users", user.getLogin());
            CookiesUtil.setCookie("date", current.toString());
            generateJsfNotificationsFromEvents(user, current);
        }
        return result;
    }

    private void generateJsfNotificationsFromEvents(OrganizerUser user, Date current) {
        List<OrganizerEvent> eventsToNotify = eventService.createNotificationsForEvents(user);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle = facesContext.getApplication().getResourceBundle(
                facesContext, "myMessages");
        for (OrganizerEvent eventsToNotify1 : eventsToNotify) {
            long daysDiff = eventsToNotify1.getEventDateStart().getTime() - current.getTime();
            daysDiff = TimeUnit.DAYS.convert(daysDiff, TimeUnit.MILLISECONDS);
            FacesMessage notification = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    bundle.getString("notification.prefix") + " "
                    + daysDiff + " " + bundle.getString("notification.suffix"),
                    eventsToNotify1.getName());
            result.add(notification);
        }
    }
}
