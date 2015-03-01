package ark.chr.web.organizer.controllers;

import ark.chr.web.organizer.services.api.ITestService;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Arek
 */
@Named("test")
@Scope("session")
public class TestClass implements Serializable{
    
    @Inject
    private ITestService testService;

    public String getText() {
//        return "hello";
        return testService.getMsg();
    }
}
