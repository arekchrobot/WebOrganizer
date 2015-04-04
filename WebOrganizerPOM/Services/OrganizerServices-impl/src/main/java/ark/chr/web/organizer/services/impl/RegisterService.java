package ark.chr.web.organizer.services.impl;

import ark.chr.web.organizer.dao.api.IOrganizerUserDao;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.IRegisterService;
import java.util.Date;
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
public class RegisterService implements IRegisterService {
    
    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);
    
    @Inject
    private IOrganizerUserDao userDao;

    @Override
    public boolean registerUser(OrganizerUser user) {
        if(userDao.findUserByLogin(user.getLogin()) != null) {
            logger.info("Registration failed. User with login " 
                    + user.getLogin() + " already exists.");
            return false;
        }
        user.setActive(false);
        user.setDateCreated(new Date());
        userDao.insert(user);
        return true;
    }

}
