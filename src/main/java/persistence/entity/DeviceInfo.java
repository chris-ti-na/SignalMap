package persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by christina on 20.04.16.
 */
@Entity
public class DeviceInfo {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String osVersion;
    private String gpsSensor;
    private String radioVersion;

    public DeviceInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getGpsSensor() {
        return gpsSensor;
    }

    public void setGpsSensor(String gpsSensor) {
        this.gpsSensor = gpsSensor;
    }

    public String getRadioVersion() {
        return radioVersion;
    }

    public void setRadioVersion(String radioVersion) {
        this.radioVersion = radioVersion;
    }
}
