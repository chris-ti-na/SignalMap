package persistence.dao;

import java.util.List;

public interface EntityDAO<Entity> {
    List<Entity> getAll();
    Entity getById(int id);
    void create(Entity entity);
    void update(Entity entity);
    void delete(int id);
}
