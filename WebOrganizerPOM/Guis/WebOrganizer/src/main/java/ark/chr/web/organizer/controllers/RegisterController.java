package ark.chr.web.organizer.controllers;

import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.IMailRegisterConfirmation;
import ark.chr.web.organizer.services.api.IRegisterService;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Arek
 */
@Named("register")
@Scope("request")
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private static final String REGISTER_SUCCESS = "registerSuccess";

    @Inject
    private IRegisterService registerService;
    @Inject
    private IMailRegisterConfirmation mailRegisterConfirmation;

    private OrganizerUser registerUser;

    public RegisterController() {
        registerUser = new OrganizerUser();
    }

    public OrganizerUser getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(OrganizerUser registerUser) {
        this.registerUser = registerUser;
    }

    public String registerNewUser() {
        logger.info("Registering new user: " + registerUser.getLogin());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (registerService.registerUser(registerUser)) {
            mailRegisterConfirmation.sendConfirmRegistrationEmail(registerUser, 
                    facesContext.getExternalContext().getRequestLocale());
            return REGISTER_SUCCESS;
        }
        ResourceBundle bundle
                = facesContext.getApplication().getResourceBundle(
                        facesContext, "myMessages");
        facesContext.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        bundle.getString("register.error.message.header"),
                        bundle.getString("register.error.message.detail")));
        return null;
    }
}
