package ark.chr.web.organizer.controllers;

import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.IRegisterService;
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
    
    @Inject
    private IRegisterService registerService;
    
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
        if(registerService.registerUser(registerUser)) {
            return "OK";
        }
        return "FAIL";
    }
}
