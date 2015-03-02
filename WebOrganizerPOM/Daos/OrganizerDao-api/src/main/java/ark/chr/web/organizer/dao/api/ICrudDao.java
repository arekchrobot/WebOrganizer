package ark.chr.web.organizer.dao.api;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Arek
 */
public interface ICrudDao<T extends Object> {

    void insert(T entity);

    T find(Serializable id);

    List<T> findAll();

    void update(T entity);

    void delete(T entity);

    void deleteById(Serializable id);

    void deleteAll();
}
