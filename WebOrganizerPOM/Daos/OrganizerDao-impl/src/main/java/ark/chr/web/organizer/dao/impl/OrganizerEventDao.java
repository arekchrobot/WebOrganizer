package ark.chr.web.organizer.dao.impl;

import ark.chr.web.organizer.dao.api.CrudDao;
import ark.chr.web.organizer.dao.api.IOrganizerEventDao;
import ark.chr.web.organizer.model.OrganizerEvent;
import ark.chr.web.organizer.model.OrganizerUser;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arek
 */
@Repository
@Transactional
public class OrganizerEventDao extends CrudDao<OrganizerEvent> implements IOrganizerEventDao, Serializable {

    @Override
    public List<OrganizerEvent> getAllEventsForUser(OrganizerUser user) {
        Query query = getSession().createQuery("from OrganizerEvent where owner = :user");
        query.setParameter("user", user);
        return query.list();
    }

}
