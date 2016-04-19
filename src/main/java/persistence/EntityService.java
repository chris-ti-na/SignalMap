package persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import persistence.dao.EntityDAO;
import persistence.dao.GenericDAOImpl;
import persistence.entity.*;

import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Dependent
public class EntityService implements Serializable {

    private SessionFactory sessionFactory;
    private EntityDAO asuLevelDAO;
    private EntityDAO dbmLevelDAO;
    private EntityDAO barLevelDAO;
    private EntityDAO locationDAO;
    private EntityDAO signalDAO;
    private EntityDAO signalLocationDAO;
    private Session currentSession;

    public EntityService(){
        Locale.setDefault(Locale.ENGLISH);
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        currentSession = sessionFactory.openSession();
        asuLevelDAO = getEntityDAO(AsuLevel.class, currentSession);
        dbmLevelDAO = getEntityDAO(DbmLevel.class, currentSession);
        barLevelDAO = getEntityDAO(BarLevel.class, currentSession);
        locationDAO = getEntityDAO(Location.class, currentSession);
        signalDAO = getEntityDAO(Signal.class, currentSession);
        signalLocationDAO = getEntityDAO(SignalLocation.class, currentSession);
    }

    private EntityDAO getEntityDAO(Class clazz, Session session){
        return new GenericDAOImpl(clazz, session);
    }

    public void addSignalLocation(SignalLocation signalLocation){
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            asuLevelDAO.create(signalLocation.getSignal().getAsuLevel());
            dbmLevelDAO.create(signalLocation.getSignal().getDbmLevel());
            barLevelDAO.create(signalLocation.getSignal().getBarLevel());
            locationDAO.create(signalLocation.getLocation());
            signalDAO.create(signalLocation.getSignal());
            signalLocationDAO.create(signalLocation);
            tx.commit();
        }catch (RuntimeException ex){
            if(tx != null) {
                tx.rollback();
                throw ex;
            }
        }
    }

    public void deleteSignalLocation(int id){
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            signalLocationDAO.delete(id);
            tx.commit();
        }catch (RuntimeException ex){
            if(tx != null) {
                tx.rollback();
                throw ex;
            }
        }
    }

    public void editSignalLocation(SignalLocation signalLocation){
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            asuLevelDAO.update(signalLocation.getSignal().getAsuLevel());
            dbmLevelDAO.update(signalLocation.getSignal().getDbmLevel());
            barLevelDAO.update(signalLocation.getSignal().getBarLevel());
            locationDAO.update(signalLocation.getLocation());
            signalDAO.update(signalLocation.getSignal());
            signalLocationDAO.update(signalLocation);
            tx.commit();
        }catch (RuntimeException ex){
            if(tx != null) {
                tx.rollback();
                throw ex;
            }
        }
    }

    public SignalLocation getSignalLocationById(int id){
        return (SignalLocation)signalLocationDAO.getById(id);
    }

    public List<SignalLocation> getAllSignalLocations(){
        return (List<SignalLocation>)signalLocationDAO.getAll();
    }


    public void closeConnection(){
        if(sessionFactory != null && currentSession != null) {
            currentSession.close();
            sessionFactory.close();
        }
    }
}
