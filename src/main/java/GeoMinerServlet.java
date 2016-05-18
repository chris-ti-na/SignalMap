import org.json.JSONArray;
import org.json.JSONObject;
import persistence.entity.*;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GeoMinerServlet extends HttpServlet {

    @Inject
    SignalBean bean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("servlet ulalala");
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String line = null;
        while((line = reader.readLine())!=null){
            System.out.println(line);

            JSONObject jsonSignal = new JSONObject(line);
            JSONArray jsonArray = jsonSignal.getJSONArray("signals");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Provider provider;
                String providerName = jsonObject.getString("networkProvider");
                List<Provider> providers = bean.getProvidersByName(providerName);
                if (!providers.isEmpty()) {
                    provider = providers.get(0);
                } else {
                    provider = new Provider();
                    provider.setName(providerName);
                }


                CellInfo cellInfo;
                String cellInfoName = jsonObject.getString("cellInfo");
                List<CellInfo> cellInfos = bean.getCellInfosByName(cellInfoName);
                if (!cellInfos.isEmpty()) {
                    cellInfo = cellInfos.get(0);
                } else {
                    cellInfo = new CellInfo();
                    cellInfo.setName(cellInfoName);
                }

                DeviceInfo deviceInfo;
                String model = jsonObject.getString("model");
                String producer = jsonObject.getString("producer");
                int osVesion = jsonObject.getInt("osVesion");
                String radioVersion = jsonObject.getString("radioVersion");
                List<DeviceInfo> deviceInfos = bean.getDeviceInfosByName(model, producer);
                if (!deviceInfos.isEmpty()) {
                    deviceInfo = deviceInfos.get(0);
                } else {
                    deviceInfo = new DeviceInfo();
                    deviceInfo.setModel(model);
                    deviceInfo.setProducer(producer);
                    deviceInfo.setOsVersion(osVesion);
                    deviceInfo.setRadioVersion(radioVersion);
                }

                Signal signal = new Signal();
                signal.setDeviceInfo(deviceInfo);
                signal.setCellInfo(cellInfo);
                signal.setProvider(provider);
                signal.setAsuLevel(jsonObject.getInt("asuSignalStrength"));
                signal.setBarLevel(jsonObject.getInt("barSignalStrength"));
                signal.setDbmLevel(jsonObject.getInt("dbmSignalStrength"));
                signal.setTimestamp(new Date(jsonObject.getLong("time")));
                signal.setLatitude(jsonObject.getDouble("latitude"));
                signal.setLongitude(jsonObject.getDouble("longitude"));
                bean.addSignal(signal);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("servlet ulalala");
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String line = null;
        while((line = reader.readLine())!=null){
            System.out.println(line);

            JsonReader jsonReader = Json.createReader(new StringReader(line));
            JsonObject jsonObject = jsonReader.readObject();
            Provider provider;
            String providerName = jsonObject.getString("networkProvider");
            List<Provider> providers = bean.getProvidersByName(providerName);
            if(!providers.isEmpty()){
                provider = providers.get(0);
            } else {
                provider = new Provider();
                provider.setName(providerName);
            }


            CellInfo cellInfo;
            String cellInfoName = jsonObject.getString("cellInfo");
            List<CellInfo> cellInfos = bean.getCellInfosByName(cellInfoName);
            if(!cellInfos.isEmpty()){
                cellInfo = cellInfos.get(0);
            } else {
                cellInfo = new CellInfo();
                cellInfo.setName(cellInfoName);
            }

            DeviceInfo deviceInfo;
            String model = jsonObject.getString("model");
            String producer = jsonObject.getString("producer");
            String osVesion = jsonObject.getString("osVesion");
            String radioVersion = jsonObject.getString("radioVersion");
            List<DeviceInfo> deviceInfos = bean.getDeviceInfosByName(model, producer);
            if(!deviceInfos.isEmpty()){
                deviceInfo = deviceInfos.get(0);
            } else {
                deviceInfo = new DeviceInfo();
                deviceInfo.setModel(model);
                deviceInfo.setProducer(producer);
//                deviceInfo.setOsVersion(osVesion);
                deviceInfo.setRadioVersion(radioVersion);
            }

            Signal signal = new Signal();
            signal.setDeviceInfo(deviceInfo);
            signal.setCellInfo(cellInfo);
            signal.setProvider(provider);
            signal.setAsuLevel(Integer.parseInt(jsonObject.getString("asuLevel")));
            signal.setBarLevel(Integer.parseInt(jsonObject.getString("barLevel")));
            signal.setDbmLevel(Integer.parseInt(jsonObject.getString("dbmLevel")));
            signal.setTimestamp(new Date(Integer.parseInt(jsonObject.getString("time"))));
            signal.setLatitude(Double.parseDouble(jsonObject.getString("latitude")));
            signal.setLongitude(Double.parseDouble(jsonObject.getString("longitude")));
            bean.addSignal(signal);
        }
    }
}
