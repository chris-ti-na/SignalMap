import persistence.EntityService;
import persistence.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class GeoMinerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EntityService entityService = new EntityService();

        AsuLevel asuLevel = new AsuLevel();
        asuLevel.setLevel(25);

        BarLevel barLevel = new BarLevel();
        barLevel.setLevel(2);

        DbmLevel dbmLevel = new DbmLevel();
        dbmLevel.setLevel(30);

        Location location = new Location();
        location.setLongitude(100.500);
        location.setLatitude(100.600);

        Signal signal = new Signal();
        signal.setAsuLevel(asuLevel);
        signal.setBarLevel(barLevel);
        signal.setDbmLevel(dbmLevel);
        signal.setCellInfo(Signal.CellInfo.CDMA);
        signal.setProvider(Signal.Provider.MEGAFON);

        SignalLocation signalLocation = new SignalLocation();
        signalLocation.setLocation(location);
        signalLocation.setSignal(signal);
        signalLocation.setTimestamp(new Date());

        entityService.addSignalLocation(signalLocation);

        entityService.closeConnection();


//        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
//        String line = null;
//        while((line = reader.readLine())!=null){
//            System.out.println(line);

//            JSONParser parser = new JSONParser();
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = (JSONObject) parser.parse(line);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            persistence.entity.persistence.entity.Frame frame = new persistence.entity.persistence.entity.Frame((Double) jsonObject.get("latitude"), (Double) jsonObject.get("longitude"),
//                    (Long) jsonObject.get("time"), ((Long)jsonObject.get("signalStrength")).intValue(),
//                    (String) jsonObject.get("cellInfo"), (String) jsonObject.get("networkProvider"));
//
//            System.out.println("__________________New data received________________");
//            System.out.println("latitude: " + frame._latitude);
//            System.out.println("longitude: " + frame._longitude);
//            System.out.println("time: " + new Date(frame._time));
//            System.out.println("signalStrength: " + frame._signalStrength);
//            System.out.println("cellInfo: " + frame._cellInfo);
//            System.out.println("networkProvider: " + frame._networkProvider);
//        }
    }
}
