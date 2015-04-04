package ark.chr.web.organizer.dao.impl;

import ark.chr.web.organizer.dao.api.CrudDao;
import ark.chr.web.organizer.dao.api.IOrganizerRoleDao;
import ark.chr.web.organizer.dao.api.IOrganizerUserDao;
import ark.chr.web.organizer.model.OrganizerRole;
import ark.chr.web.organizer.model.OrganizerUser;
import java.io.Serializable;
import javax.inject.Inject;
import org.hibernate.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 *
 * @author Arek
 */
@Repository
@Transactional
public class OrganizerUserDao extends CrudDao<OrganizerUser> implements IOrganizerUserDao, Serializable {
    
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private IOrganizerRoleDao roleDao;

    @Override
    public void insert(OrganizerUser entity) {
        super.insert(entity);
        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);
        update(entity);
        OrganizerRole role = roleDao.findRoleByName("ROLE_USER");
        entity.getRoles().add(role);
        update(entity);
    }

    @Override
    public OrganizerUser findUserByLogin(String login) {
        Query query = getSession().createQuery("from OrganizerUser where login = :login");
        query.setParameter("login", login);
        return (OrganizerUser) query.uniqueResult();
    }

}
