package ark.chr.web.organizer.controllers;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Arek
 */
@Named("localeManager")
@Scope("session")
public class LocaleManager implements Serializable {
    
    private static final Logger logger = LoggerFactory.getLogger(LocaleManager.class);

    private Locale locale;
    
    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    public Locale getLocale() {
        return locale;
    }
    
    public String getLanguage() {
        return locale.getLanguage();
    }
    
    public void setLanguage(String language) {
        logger.info("Changing locale to: " + language);
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
