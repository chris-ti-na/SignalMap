package persistence.dao;

import org.hibernate.*;

import java.util.List;

public class GenericDAOImpl<Entity> implements EntityDAO<Entity> {

    private Session session;
    private Class<Entity> entityClass;

    public GenericDAOImpl(Class<Entity> entityClass, Session session){
        this.entityClass = entityClass;
        this.session = session;
    }

    public List getAll() {
        return session.createCriteria(this.entityClass).list();
    }

    public Entity getById(int id) {
        return session.get(this.entityClass, id);
    }

    public void create(Entity entity) {
        session.save(entity);
    }

    public void update(Entity entity) {
        session.merge(entity);
    }

    public void delete(int id){
        Entity entity = getById(id);
        session.delete(entity);
    }
}
