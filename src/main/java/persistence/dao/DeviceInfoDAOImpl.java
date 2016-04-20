package persistence.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import persistence.entity.DeviceInfo;

import java.util.List;

/**
 * Created by christina on 20.04.16.
 */
public class DeviceInfoDAOImpl extends GenericDAOImpl<DeviceInfo>  {
    private Session session;

    public DeviceInfoDAOImpl(Session session) {
        super(DeviceInfo.class, session);
        this.session = session;
    }

    public List<DeviceInfo> getDeviceInfosByNameQuery(String name) {
        return session.createCriteria(DeviceInfo.class)
                .add(Restrictions.like("deviceinfo.name", name + "%"))
                .list();
    }
}
