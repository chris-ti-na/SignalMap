package persistence.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by christina on 29.03.16.
 */

@MappedSuperclass
public class SignalLevel implements Serializable{
    @Id
    @GeneratedValue
    private int id;
    private int level;

    public SignalLevel() {
    }

    public SignalLevel(int id, int level) {
        this.id = id;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
