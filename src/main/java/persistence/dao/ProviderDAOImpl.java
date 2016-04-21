package persistence.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import persistence.entity.Provider;

import java.util.List;

/**
 * Created by christina on 20.04.16.
 */
public class ProviderDAOImpl extends GenericDAOImpl<Provider> {
    private Session session;

    public ProviderDAOImpl(Session session) {
        super(Provider.class, session);
        this.session = session;
    }

    public List<Provider> getProvidersByNameQuery(String name) {
        return session.createCriteria(Provider.class)
                .add(Restrictions.like("name", name + "%"))
                .list();
    }
}
