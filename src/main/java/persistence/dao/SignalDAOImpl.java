package persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import persistence.entity.CellInfo;
import persistence.entity.DeviceInfo;
import persistence.entity.Provider;
import persistence.entity.Signal;

import java.util.Date;
import java.util.List;

public class SignalDAOImpl extends GenericDAOImpl<Signal> {
    private Session session;

    public SignalDAOImpl(Session session) {
        super(Signal.class, session);
        this.session = session;
    }

    public List<Object[]> getAllAvdSignalsQuery(Date startDate, Date endDate, CellInfo cellInfo, Provider provider, DeviceInfo deviceInfo) {
        Criteria criteria =  session.createCriteria(Signal.class);
        criteria.setProjection(Projections.projectionList()
                        .add(Projections.avg("asuLevel"))
                        .add(Projections.avg("barLevel"))
                        .add(Projections.avg("dbmLevel"))
                        .add(Projections.groupProperty("latitude"))
                        .add(Projections.groupProperty("longitude"))
        );
        if (cellInfo != null){
            criteria.add(Restrictions.eq("cellInfo", cellInfo));
        }
        if (provider != null){
            criteria.add(Restrictions.eq("provider", provider));
        }
        if (deviceInfo != null){
            criteria.add(Restrictions.eq("deviceInfo", deviceInfo));
        }
        if(startDate != null){
            criteria.add(Restrictions.ge("timestamp", startDate));
        }
        if (endDate != null){
            criteria.add(Restrictions.le("timestamp", startDate));
        }

        return criteria.list();
    }
}
