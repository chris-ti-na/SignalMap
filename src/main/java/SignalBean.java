import persistence.EntityService;
import persistence.entity.CellInfo;
import persistence.entity.DeviceInfo;
import persistence.entity.Provider;
import persistence.entity.Signal;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class SignalBean implements Serializable {

    @Inject
    EntityService service;

    public void addDeviceInfo(DeviceInfo deviceInfo){
        service.addDeviceInfo(deviceInfo);
    }

    public void addCellInfo(CellInfo cellInfo){
        service.addCellInfo(cellInfo);
    }

    public void addProvider(Provider provider){
        service.addProvider(provider);
    }

    public void addSignal(Signal signal){
        service.addSignal(signal);
    }

    public void editSignal(Signal signal){
        service.editSignal(signal);
    }

    public void deleteSignal(int id){
        service.deleteSignal(id);
    }

    public List getAllProviders(){
        return service.getAllProviders();
    }

    public List getAllCellInfos(){
        return service.getAllCellInfos();
    }

    public List getAllSignals(){
        return service.getAllSignals();
    }

    public List<Object[]> getAllAvdSignals(Date startDate, Date endDate, CellInfo cellInfo, Provider provider, DeviceInfo deviceInfo){
        return service.getAllAvdSignals(startDate, endDate, cellInfo, provider, deviceInfo);
    }

    public List getProvidersByName(String name){
        return service.getProvidersByNameQuery(name);
    }

    public List getCellInfosByName(String name){
        return service.getCellInfosByNameQuery(name);
    }

    public List getDeviceInfosByName(String model, String producer){
        return service.getDeviceInfosByNameQuery(model, producer);
    }

    public Provider getProviderById(int id){
        return service.getProviderById(id);
    }

    public CellInfo getCellInfoById(int id){
        return service.getCellInfoById(id);
    }

    public DeviceInfo getDeviceInfoById(int id){
        return service.getDeviceInfoById(id);
    }

    @PreDestroy
    private void destroy(){
        service.closeConnection();
    }
}