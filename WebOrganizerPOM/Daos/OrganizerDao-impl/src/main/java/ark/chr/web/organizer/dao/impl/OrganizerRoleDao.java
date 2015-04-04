package ark.chr.web.organizer.dao.impl;

import ark.chr.web.organizer.dao.api.CrudDao;
import ark.chr.web.organizer.dao.api.IOrganizerRoleDao;
import ark.chr.web.organizer.model.OrganizerRole;
import java.io.Serializable;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arek
 */
@Repository
@Transactional
public class OrganizerRoleDao extends CrudDao<OrganizerRole> implements IOrganizerRoleDao, Serializable {

    @Override
    public OrganizerRole findRoleByName(String name) {
        Query query = getSession().createQuery("from OrganizerRole where name = :name");
        query.setParameter("name", name);
        return (OrganizerRole) query.uniqueResult();
    }

}
