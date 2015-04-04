package ark.chr.web.organizer.dao.api;

import ark.chr.web.organizer.model.OrganizerUser;

/**
 *
 * @author Arek
 */
public interface IOrganizerUserDao extends ICrudDao<OrganizerUser> {

    OrganizerUser findUserByLogin(String login);
}
