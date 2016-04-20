package persistence.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by christina on 18.04.16.
 */
@Entity
public class Signal implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private double longitude;
    private double latitude;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    private int asuLevel;
    private int barLevel;
    private int dbmLevel;

    @ManyToOne
    @JoinColumn(name = "cellInfo_id",
            foreignKey = @ForeignKey(name = "CELLINFO_ID_FK")
    )
    private CellInfo cellInfo;

    @ManyToOne
    @JoinColumn(name = "provider_id",
            foreignKey = @ForeignKey(name = "PROVIDER_ID_FK")
    )
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "provider_id",
            foreignKey = @ForeignKey(name = "PROVIDER_ID_FK")
    )
    private DeviceInfo deviceInfo;

    public Signal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getAsuLevel() {
        return asuLevel;
    }

    public void setAsuLevel(int asuLevel) {
        this.asuLevel = asuLevel;
    }

    public int getBarLevel() {
        return barLevel;
    }

    public void setBarLevel(int barLevel) {
        this.barLevel = barLevel;
    }

    public int getDbmLevel() {
        return dbmLevel;
    }

    public void setDbmLevel(int dbmLevel) {
        this.dbmLevel = dbmLevel;
    }

    public CellInfo getCellInfo() {
        return cellInfo;
    }

    public void setCellInfo(CellInfo cellInfo) {
        this.cellInfo = cellInfo;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
