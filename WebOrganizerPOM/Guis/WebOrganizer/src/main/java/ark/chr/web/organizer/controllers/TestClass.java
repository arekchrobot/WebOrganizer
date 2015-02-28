package ark.chr.web.organizer.controllers;

import java.io.Serializable;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Arek
 */
@Named("test")
@Scope("session")
public class TestClass implements Serializable{

    public String getText() {
        return "hello";
    }
}
