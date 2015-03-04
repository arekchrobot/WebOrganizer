package ark.chr.web.organizer.controllers;

import ark.chr.web.organizer.services.api.ITestService;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Arek
 */
@Named("test")
@Scope("session")
public class TestClass implements Serializable {
    
    private static final Logger logger = LoggerFactory.getLogger(TestClass.class);
    
    @Inject
    private ITestService testService;

    public String getText() {
        logger.info("Test log");
        return testService.getMsg();
    }
}
