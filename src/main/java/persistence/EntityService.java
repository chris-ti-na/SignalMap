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
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Dependent
public class EntityService implements Serializable {

    private SessionFactory sessionFactory;
    private EntityDAO signalDAO;
    private EntityDAO deviceInfoDAO;
    private EntityDAO cellInfoDAO;
    private EntityDAO providerDAO;
    private Session currentSession;

    public EntityService(){
        Locale.setDefault(Locale.ENGLISH);
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        currentSession = sessionFactory.openSession();
        signalDAO = getEntityDAO(Signal.class, currentSession);
        deviceInfoDAO = getEntityDAO(DeviceInfo.class, currentSession);
        cellInfoDAO = getEntityDAO(CellInfo.class, currentSession);
        providerDAO = getEntityDAO(Provider.class, currentSession);
    }

    private EntityDAO getEntityDAO(Class clazz, Session session){
        if(Signal.class.equals(clazz)){
            return new SignalDAOImpl(session);
        }
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

    public void addProvider(Provider provider){
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            providerDAO.create(provider);
            tx.commit();
        }catch (RuntimeException ex){
            if(tx != null) {
                tx.rollback();
                throw ex;
            }
        }
    }

    public void addCellInfo(CellInfo cellInfo){
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            cellInfoDAO.create(cellInfo);
            tx.commit();
        }catch (RuntimeException ex){
            if(tx != null) {
                tx.rollback();
                throw ex;
            }
        }
    }

    public void addDeviceInfo(DeviceInfo deviceInfo){
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            deviceInfoDAO.create(deviceInfo);
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

    public List getAllProviders(){
        return providerDAO.getAll();
    }

    public List getAllCellInfos(){
        return cellInfoDAO.getAll();
    }

    public List getAllSignals(){
        return signalDAO.getAll();
    }

    public List<Object[]> getAllAvdSignals(Date startDate, Date endDate, CellInfo cellInfo, Provider provider, DeviceInfo deviceInfo){
        return ((SignalDAOImpl)signalDAO).getAllAvdSignalsQuery(startDate, endDate, cellInfo, provider, deviceInfo);
    }

    public List getProvidersByNameQuery(String name){
        return ((ProviderDAOImpl)providerDAO).getProvidersByNameQuery(name);
    }

    public List getCellInfosByNameQuery(String name){
        return ((CellInfoDAOImpl)cellInfoDAO).getCellInfosByNameQuery(name);
    }

    public List getDeviceInfosByNameQuery(String model, String producer){
        return ((DeviceInfoDAOImpl)deviceInfoDAO).getDeviceInfosByNameQuery(model, producer);
    }

    public void closeConnection(){
        if(sessionFactory != null && currentSession != null) {
            currentSession.close();
            sessionFactory.close();
        }
    }
}
