package ark.chr.web.organizer.dao.api;

import ark.chr.web.organizer.model.OrganizerRole;

/**
 *
 * @author Arek
 */
public interface IOrganizerRoleDao extends ICrudDao<OrganizerRole> {

    OrganizerRole findRoleByName(String name);
}
