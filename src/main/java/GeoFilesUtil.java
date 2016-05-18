import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GeoFilesUtil {

    public static void writeData2Shapefiles(Map<String, Map<String, List<Object[]>>> data){
        for (String cellInfo : data.keySet()){
            for (String provider : data.get(cellInfo).keySet()){
                Map<String, List<GeoStatisticItem>> providerStatisticMap = StatisticUtil.getAllStatistics(data.get(cellInfo).get(provider));
                File dir = getDirectory(getDirectoryPath() + File.separator + cellInfo + File.separator + provider);
                GeoFilesUtil.generateShapefile(providerStatisticMap, dir);
            }
        }
    }

    private static String getDirectoryPath(){
        return System.getProperty("glassfish.embedded.tmpdir") + File.separator + "shapefiles";
    }

    private static File getDirectory(String path){
        File dir = new File(path);
        dir.mkdirs();
        return dir;
    }

    private static File getDirectory(){
        File dir = new File(getDirectoryPath());
        dir.mkdirs();
        return dir;
    }

    private static void generateShapefile(Map<String, List<GeoStatisticItem>> allStatisticMap, File directory){
        for(String key : allStatisticMap.keySet()) {
            SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
            builder.setName("Location");
            builder.setSRS("EPSG:4326");
            builder.add("the_geom", Point.class);
            builder.add("number", Integer.class);

            final SimpleFeatureType TYPE = builder.buildFeatureType();

            List<SimpleFeature> features = new ArrayList<SimpleFeature>();
            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);

            for (GeoStatisticItem item : allStatisticMap.get(key)) {
                Point point = geometryFactory.createPoint(new Coordinate(item.getLongitude(), item.getLatitude()));

                featureBuilder.add(point);
                featureBuilder.add(item.getNumber());
                SimpleFeature feature = featureBuilder.buildFeature(null);
                features.add(feature);
            }

            String fileName = key + ".shp";
            File shapeFile = new File(directory + File.separator + fileName);

            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

            try {
                Map<String, Serializable> params = new HashMap<String, Serializable>();
                params.put("url", shapeFile.toURI().toURL());
                params.put("create spatial index", Boolean.TRUE);

                ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
                newDataStore.createSchema(TYPE);

                Transaction transaction = new DefaultTransaction("create");

                String typeName = newDataStore.getTypeNames()[0];
                SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);

                if (featureSource instanceof SimpleFeatureStore) {
                    SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                    SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
                    featureStore.setTransaction(transaction);
                    try {
                        featureStore.addFeatures(collection);
                        transaction.commit();
                    } catch (Exception problem) {
                        problem.printStackTrace();
                        transaction.rollback();
                    } finally {
                        transaction.close();
                    }
                } else {
                    System.out.println(typeName + " does not support read/write access");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addToZipFile(String fileName, ZipOutputStream zos) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

    private static void zipShapefiles(Set<String> keys) throws IOException {
        for(String key : keys) {
            FileOutputStream fos = new FileOutputStream(getDirectoryPath() + File.separator + key + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            addToZipFile(getDirectoryPath() + File.separator + key + ".shp", zos);
            addToZipFile(getDirectoryPath() + File.separator + key + ".shx", zos);
            addToZipFile(getDirectoryPath() + File.separator + key + ".prj", zos);
            addToZipFile(getDirectoryPath() + File.separator + key + ".dbf", zos);

            zos.close();
            fos.close();
        }
    }
}
