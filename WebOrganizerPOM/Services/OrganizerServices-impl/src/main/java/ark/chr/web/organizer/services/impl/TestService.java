package ark.chr.web.organizer.services.impl;

import ark.chr.web.organizer.dao.api.ITestDao;
//import ark.chr.web.organizer.dao.impl.TestDao;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.ITestService;
import java.io.Serializable;
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
public class TestService implements ITestService, Serializable {
    
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);
    
    @Inject
    private ITestDao testDao;

    @Override
    public String getMsg() {
        OrganizerUser user = testDao.find(1L);
        logger.info("Test service log");
        return "testService " + "+ testDao = " + user.getName();
    }

}
