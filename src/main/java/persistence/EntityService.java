package persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import persistence.dao.*;
import persistence.entity.*;

import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Dependent
public class EntityService implements Serializable {

    private SessionFactory sessionFactory;
    private EntityDAO signalDAO;
    private DeviceInfoDAOImpl deviceInfoDAO;
    private CellInfoDAOImpl cellInfoDAO;
    private ProviderDAOImpl providerDAO;
    private Session currentSession;

    public EntityService(){
        Locale.setDefault(Locale.ENGLISH);
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        currentSession = sessionFactory.openSession();
        signalDAO = getEntityDAO(Signal.class, currentSession);
        deviceInfoDAO = (DeviceInfoDAOImpl)getEntityDAO(DeviceInfo.class, currentSession);
        cellInfoDAO = (CellInfoDAOImpl)getEntityDAO(CellInfo.class, currentSession);
        providerDAO = (ProviderDAOImpl)getEntityDAO(Provider.class, currentSession);
    }

    private EntityDAO getEntityDAO(Class clazz, Session session){
        if(CellInfo.class.equals(clazz)){
            return new CellInfoDAOImpl(session);
        }
        if(DeviceInfo.class.equals(clazz)){
            return new DeviceInfoDAOImpl(session);
        }
        if(Provider.class.equals(clazz)){
            return new ProviderDAOImpl(session);
        }
        return new GenericDAOImpl(clazz, session);
    }

    public void addSignal(Signal signal){
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            signalDAO.create(signal);
            deviceInfoDAO.create(signal.getDeviceInfo());
            cellInfoDAO.create(signal.getCellInfo());
            providerDAO.create(signal.getProvider());
            tx.commit();
        }catch (RuntimeException ex){
            if(tx != null) {
                tx.rollback();
                throw ex;
            }
        }
    }

    public void deleteSignal(int id){
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            signalDAO.delete(id);
            tx.commit();
        }catch (RuntimeException ex){
            if(tx != null) {
                tx.rollback();
                throw ex;
            }
        }
    }

    public void editSignal(Signal signal){
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            signalDAO.update(signal);
            deviceInfoDAO.update(signal.getDeviceInfo());
            cellInfoDAO.update(signal.getCellInfo());
            providerDAO.update(signal.getProvider());
            tx.commit();
        }catch (RuntimeException ex){
            if(tx != null) {
                tx.rollback();
                throw ex;
            }
        }
    }

    public Signal getSignalById(int id){
        return (Signal) signalDAO.getById(id);
    }

    public Provider getProviderById(int id){
        return (Provider) providerDAO.getById(id);
    }

    public CellInfo getCellInfoById(int id){
        return (CellInfo) cellInfoDAO.getById(id);
    }

    public DeviceInfo getDeviceInfoById(int id){
        return (DeviceInfo) deviceInfoDAO.getById(id);
    }

    public List<Signal> getAllSignals(){
        return (List<Signal>) signalDAO.getAll();
    }

    public List<Provider> getProvidersByNameQuery(String name){
        return providerDAO.getProvidersByNameQuery(name);
    }

    public List<CellInfo> getCellInfosByNameQuery(String name){
        return cellInfoDAO.getCellInfosByNameQuery(name);
    }

    public List<DeviceInfo> getDeviceInfosByNameQuery(String name){
        return deviceInfoDAO.getDeviceInfosByNameQuery(name);
    }

    public void closeConnection(){
        if(sessionFactory != null && currentSession != null) {
            currentSession.close();
            sessionFactory.close();
        }
    }
}
