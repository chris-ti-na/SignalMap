import persistence.entity.CellInfo;
import persistence.entity.Provider;
import persistence.entity.Signal;

import java.util.List;

/**
 * Created by Кристина on 29.04.2016.
 */
public class StatisticService {

    private SignalBean bean;

    public SignalBean getStatistics(CellInfo cellInfo, Provider provider, String levelName) {
        List<Signal> signals = bean.getAllSignals();
        for (Signal signal : signals){

        }

        return bean;
    }
}
