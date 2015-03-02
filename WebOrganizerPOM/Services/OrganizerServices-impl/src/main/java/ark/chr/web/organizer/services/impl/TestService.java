package ark.chr.web.organizer.services.impl;

import ark.chr.web.organizer.dao.impl.TestDao;
import ark.chr.web.organizer.model.OrganizerUser;
import ark.chr.web.organizer.services.api.ITestService;
import java.io.Serializable;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arek
 */
@Service
@Transactional
public class TestService implements ITestService, Serializable{
    
    @Inject
    private TestDao testDao;

    @Override
    public String getMsg() {
        OrganizerUser user = testDao.find(1L);
        return "testService " + "+ testDao = " + user.getName();
    }

}
