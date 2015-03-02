package ark.chr.web.organizer.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Arek
 */
@Entity
@Table(name = "organizer_user")
public class OrganizerUser extends BaseEntity implements Serializable{

    @Column
    private String name;
    
    public OrganizerUser() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
