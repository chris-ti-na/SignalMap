package persistence.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import persistence.entity.CellInfo;
import persistence.entity.DeviceInfo;
import persistence.entity.Provider;
import persistence.entity.Signal;

import java.util.Date;
import java.util.List;

/**
 * Created by christina on 21.04.16.
 */
public class SignalDAOImpl extends GenericDAOImpl<Signal> {
    private Session session;

    public SignalDAOImpl(Session session) {
        super(Signal.class, session);
        this.session = session;
    }

    public List<Signal> getAllAvdSignalsQuery() {
        return session.createCriteria(Signal.class)
                .setProjection(Projections.projectionList()
                                .add(Projections.avg("asulevel"))
                                .add(Projections.avg("barlevel"))
                                .add(Projections.avg("dbmlevel"))
                                .add(Projections.property("latitude"))
                                .add(Projections.property("longitude"))
                                .add(Projections.groupProperty("latitude"))
                                .add(Projections.groupProperty("longitude"))
                )
                .list();
    }

    public List<Signal> getAvdSignalsByDateQuery(Date startDate, Date endDate) {
        return session.createCriteria(Signal.class)
                .setProjection(Projections.projectionList()
                                .add(Projections.avg("asulevel"))
                                .add(Projections.avg("barlevel"))
                                .add(Projections.avg("dbmlevel"))
                                .add(Projections.property("latitude"))
                                .add(Projections.property("longitude"))
                                .add(Projections.groupProperty("latitude"))
                                .add(Projections.groupProperty("longitude"))
                )
                .add(Restrictions.between("timestamp", startDate, endDate))
                .list();
    }
}
