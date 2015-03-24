package ark.chr.web.organizer.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Arek
 */
@Entity
@Table(name = "organizer_event")
public class OrganizerEvent extends BaseEntity implements Serializable {

    @Column
    @NotNull
    private String name;
    
    @Column
    private String description;
    
    @Column(name = "event_date")
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date eventDate;
    
    @Column
    private String address;
    
    @ManyToOne
    private OrganizerUser owner;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private Priority priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrganizerUser getOwner() {
        return owner;
    }

    public void setOwner(OrganizerUser owner) {
        this.owner = owner;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
