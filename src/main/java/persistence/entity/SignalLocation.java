package persistence.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by christina on 18.04.16.
 */
@Entity
public class SignalLocation implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "signal_id",
            foreignKey = @ForeignKey(name = "SIGNAL_ID_FK")
    )
    private Signal signal;
    @ManyToOne
    @JoinColumn(name = "location_id",
            foreignKey = @ForeignKey(name = "LOCATION_ID_FK")
    )
    private Location location;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public SignalLocation() {
    }

    public SignalLocation(int id, Signal signal, Location location, Date timestamp) {
        this.id = id;
        this.signal = signal;
        this.location = location;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
