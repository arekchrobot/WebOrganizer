package ark.chr.web.organizer.services.api;

import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.exceptions.RegisterConfirmationFailedException;
import java.util.Locale;

/**
 *
 * @author Arek
 */
public interface IMailRegisterConfirmation {
    
    void sendConfirmRegistrationEmail(OrganizerUser userToRegister, Locale locale);
    
    void confirmUserRegistration(Long userId, String digest) throws RegisterConfirmationFailedException;
}
