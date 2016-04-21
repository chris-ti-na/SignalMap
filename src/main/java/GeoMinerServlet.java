import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import persistence.EntityService;
import persistence.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

public class GeoMinerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EntityService entityService = new EntityService();

        Provider provider;
        String providerName = "MTS";
        List<Provider> providers = entityService.getProvidersByNameQuery(providerName);
        if(!providers.isEmpty()){
            provider = providers.get(0);
        } else {
            provider = new Provider();
            provider.setName(providerName);
        }

        CellInfo cellInfo;
        String cellInfoName = "LTE";
        List<CellInfo> cellInfos = entityService.getCellInfosByNameQuery(cellInfoName);
        if(!cellInfos.isEmpty()){
            cellInfo = cellInfos.get(0);
        } else {
            cellInfo = new CellInfo();
            cellInfo.setName(cellInfoName);
        }

        DeviceInfo deviceInfo;
        String deviceInfoName = "WoW";
        List<DeviceInfo> deviceInfos = entityService.getDeviceInfosByNameQuery(deviceInfoName);
        if(!deviceInfos.isEmpty()){
            deviceInfo = deviceInfos.get(0);
        } else {
            deviceInfo = new DeviceInfo();
            deviceInfo.setName(deviceInfoName);
        }

        Signal signal = new Signal();
        signal.setDeviceInfo(deviceInfo);
        signal.setCellInfo(cellInfo);
        signal.setProvider(provider);
        signal.setAsuLevel(10);
        signal.setBarLevel(2);
        signal.setDbmLevel(70);
        signal.setTimestamp(new Date());
        signal.setLatitude(100.500);
        signal.setLongitude(100.500);

        entityService.addSignal(signal);


        signal = new Signal();
        signal.setDeviceInfo(deviceInfo);
        signal.setCellInfo(cellInfo);
        signal.setProvider(provider);
        signal.setAsuLevel(20);
        signal.setBarLevel(4);
        signal.setDbmLevel(50);
        signal.setTimestamp(new Date());
        signal.setLatitude(100.500);
        signal.setLongitude(100.500);

        entityService.addSignal(signal);

        signal = new Signal();
        signal.setDeviceInfo(deviceInfo);
        signal.setCellInfo(cellInfo);
        signal.setProvider(provider);
        signal.setAsuLevel(30);
        signal.setBarLevel(3);
        signal.setDbmLevel(60);
        signal.setTimestamp(new Date());
        signal.setLatitude(100.500);
        signal.setLongitude(100.500);

        entityService.addSignal(signal);

        signal = new Signal();
        signal.setDeviceInfo(deviceInfo);
        signal.setCellInfo(cellInfo);
        signal.setProvider(provider);
        signal.setAsuLevel(60);
        signal.setBarLevel(0);
        signal.setDbmLevel(20);
        signal.setTimestamp(new Date());
        signal.setLatitude(200.500);
        signal.setLongitude(200.500);

        entityService.addSignal(signal);


        signal = new Signal();
        signal.setDeviceInfo(deviceInfo);
        signal.setCellInfo(cellInfo);
        signal.setProvider(provider);
        signal.setAsuLevel(20);
        signal.setBarLevel(4);
        signal.setDbmLevel(50);
        signal.setTimestamp(new Date());
        signal.setLatitude(200.500);
        signal.setLongitude(200.500);

        entityService.addSignal(signal);

        signal = new Signal();
        signal.setDeviceInfo(deviceInfo);
        signal.setCellInfo(cellInfo);
        signal.setProvider(provider);
        signal.setAsuLevel(30);
        signal.setBarLevel(4);
        signal.setDbmLevel(-20);
        signal.setTimestamp(new Date());
        signal.setLatitude(300.500);
        signal.setLongitude(300.500);

        entityService.addSignal(signal);

        signal = new Signal();
        signal.setDeviceInfo(deviceInfo);
        signal.setCellInfo(cellInfo);
        signal.setProvider(provider);
        signal.setAsuLevel(20);
        signal.setBarLevel(1);
        signal.setDbmLevel(30);
        signal.setTimestamp(new Date());
        signal.setLatitude(300.500);
        signal.setLongitude(300.500);

        entityService.addSignal(signal);

        entityService.closeConnection();

//        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
//        String line = null;
//        while((line = reader.readLine())!=null){
//            System.out.println(line);
//
//            JSONParser parser = new JSONParser();
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = (JSONObject) parser.parse(line);
//
//                EntityService entityService = new EntityService();
//
//                Provider provider;
//                String providerName = (String) jsonObject.get("networkProvider");
//                List<Provider> providers = entityService.getProvidersByNameQuery(providerName);
//                if(!providers.isEmpty()){
//                    provider = providers.get(0);
//                } else {
//                    provider = new Provider();
//                    provider.setName(providerName);
//                }
//
//                CellInfo cellInfo;
//                String cellInfoName = (String) jsonObject.get("cellInfo");
//                List<CellInfo> cellInfos = entityService.getCellInfosByNameQuery(cellInfoName);
//                if(!cellInfos.isEmpty()){
//                    cellInfo = cellInfos.get(0);
//                } else {
//                    cellInfo = new CellInfo();
//                    cellInfo.setName(cellInfoName);
//                }
//
//                DeviceInfo deviceInfo;
//                String deviceInfoName = (String) jsonObject.get("deviceInfo");
//                List<DeviceInfo> deviceInfos = entityService.getDeviceInfosByNameQuery(deviceInfoName);
//                if(!deviceInfos.isEmpty()){
//                    deviceInfo = deviceInfos.get(0);
//                } else {
//                    deviceInfo = new DeviceInfo();
//                    deviceInfo.setName(deviceInfoName);
//                }
//
//                Signal signal = new Signal();
//                signal.setDeviceInfo(deviceInfo);
//                signal.setCellInfo(cellInfo);
//                signal.setProvider(provider);
//                signal.setAsuLevel(((Long) jsonObject.get("asuLevel")).intValue());
//                signal.setBarLevel(((Long) jsonObject.get("barLevel")).intValue());
//                signal.setDbmLevel(((Long) jsonObject.get("dbmLevel")).intValue());
//                signal.setTimestamp(new Date((Long) jsonObject.get("time")));
//                signal.setLatitude((Double) jsonObject.get("latitude"));
//                signal.setLongitude((Double) jsonObject.get("longitude"));
//
//                entityService.addSignal(signal);
//                entityService.closeConnection();
//
//                System.out.println("__________________New data received________________");
//                System.out.println("latitude: " + signal.getLatitude());
//                System.out.println("longitude: " + signal.getLongitude());
//                System.out.println("time: " + signal.getTimestamp());
//                System.out.println("asuLevel: " + signal.getAsuLevel());
//                System.out.println("barLevel: " + signal.getBarLevel());
//                System.out.println("dbmLevel: " + signal.getDbmLevel());
//                System.out.println("cellInfo: " + signal.getCellInfo().getName());
//                System.out.println("networkProvider: " + signal.getProvider().getName());
//                System.out.println("deviceInfo: " + signal.getDeviceInfo().getName());
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
