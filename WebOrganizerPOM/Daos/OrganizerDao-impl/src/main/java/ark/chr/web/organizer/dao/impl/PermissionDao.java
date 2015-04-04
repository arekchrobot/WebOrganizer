package ark.chr.web.organizer.dao.impl;

import ark.chr.web.organizer.dao.api.CrudDao;
import ark.chr.web.organizer.dao.api.IPermissionDao;
import ark.chr.web.organizer.model.Permission;
import java.io.Serializable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Arek
 */
@Repository
@Transactional
public class PermissionDao extends CrudDao<Permission> implements IPermissionDao, Serializable {

}
