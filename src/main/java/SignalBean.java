import persistence.EntityService;
import persistence.entity.Signal;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by christina on 21.04.16.
 */

@ApplicationScoped
public class SignalBean implements Serializable {

    @Inject
    EntityService service;

    public void addSignal(Signal signal){
        service.addSignal(signal);
    }

    public void editSignal(Signal signal){
        service.editSignal(signal);
    }

    public void deleteSignal(int id){
        service.deleteSignal(id);
    }

    public List<Signal> getAllSignals(){
        return service.getAllSignals();
    }

    public List<Signal> getAllAvdSignals(){
        return service.getAllAvdSignals();
    }

    public List<Signal> getAvdSignalsByDate(Date startDate, Date endDate){
        return service.getAvdSignalsByDate(startDate, endDate);
    }

    @PreDestroy
    private void destroy(){
        service.closeConnection();
    }
}