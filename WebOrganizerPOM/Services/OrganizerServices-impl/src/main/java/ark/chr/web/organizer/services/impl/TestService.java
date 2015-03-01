package ark.chr.web.organizer.services.impl;

import ark.chr.web.organizer.services.api.ITestService;
import java.io.Serializable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Arek
 */
@Service
public class TestService implements ITestService, Serializable{

    @Override
    public String getMsg() {
        return "testService";
    }

}
