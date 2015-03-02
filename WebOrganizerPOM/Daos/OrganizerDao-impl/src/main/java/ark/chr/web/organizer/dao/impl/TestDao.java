package ark.chr.web.organizer.dao.impl;

import ark.chr.web.organizer.dao.api.CrudDao;
import ark.chr.web.organizer.model.OrganizerUser;
import java.io.Serializable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arek
 */
@Repository
@Transactional
public class TestDao extends CrudDao<OrganizerUser> implements Serializable{

}
