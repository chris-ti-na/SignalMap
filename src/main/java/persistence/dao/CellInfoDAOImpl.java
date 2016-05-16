package persistence.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import persistence.entity.CellInfo;

import java.util.List;

/**
 * Created by christina on 20.04.16.
 */
public class CellInfoDAOImpl extends GenericDAOImpl<CellInfo>  {
    private Session session;

    public CellInfoDAOImpl(Session session) {
        super(CellInfo.class, session);
        this.session = session;
    }

    public List getCellInfosByNameQuery(String name) {
        return session.createCriteria(CellInfo.class)
                .add(Restrictions.like("name", name + "%"))
                .list();
    }
}
