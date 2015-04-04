package ark.chr.web.organizer.dao.impl;

import ark.chr.web.organizer.dao.api.CrudDao;
import ark.chr.web.organizer.dao.api.IOrganizerEventDao;
import ark.chr.web.organizer.model.OrganizerEvent;
import java.io.Serializable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arek
 */
@Repository
@Transactional
public class OrganizerEventDao extends CrudDao<OrganizerEvent> implements IOrganizerEventDao, Serializable {

}
