import persistence.entity.CellInfo;
import persistence.entity.Provider;
import persistence.entity.Signal;

import java.util.*;

public class StatisticUtil {

    public static Map<String, List<GeoStatisticItem>> getAllStatistics(List<Object[]> signals ){
        Map<String, List<GeoStatisticItem>> result = new HashMap<String, List<GeoStatisticItem>>();

        List<GeoStatisticItem> barResult = new ArrayList<GeoStatisticItem>();
        List<GeoStatisticItem> asuResult = new ArrayList<GeoStatisticItem>();
        List<GeoStatisticItem> dbmResult = new ArrayList<GeoStatisticItem>();

        for (Object[] object : signals){
            asuResult.add(new GeoStatisticItem((Double)object[3], (Double)object[4], (Double)object[0]));
            barResult.add(new GeoStatisticItem((Double)object[3], (Double)object[4], (Double)object[1]));
            dbmResult.add(new GeoStatisticItem((Double)object[3], (Double)object[4], (Double)object[2]));
        }

        result.put("bar", barResult);
        result.put("asu", asuResult);
        result.put("dbm", dbmResult);

        return result;
    }
}