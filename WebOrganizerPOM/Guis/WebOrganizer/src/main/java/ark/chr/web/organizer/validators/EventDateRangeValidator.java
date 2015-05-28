package ark.chr.web.organizer.validators;

import java.util.Date;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Arek
 */
@FacesValidator("eventDateRangeValidator")
public class EventDateRangeValidator implements Validator{

    @Override
    public void validate(FacesContext facesContext, UIComponent uic, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }
        
        Object startDateValue = uic.getAttributes().get("startDate");
        if(startDateValue == null) {
            return;
        }
        
        Date startDate = (Date) startDateValue;
        Date endDate = (Date) value;
        if(endDate.before(startDate)) {
            ResourceBundle bundle
                = facesContext.getApplication().getResourceBundle(
                        facesContext, "myMessages");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        bundle.getString("register.error.message.header"),
                        bundle.getString("register.error.message.detail")));
        }
    }

}
