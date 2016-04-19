package persistence.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by christina on 18.04.16.
 */

@Entity
public class Signal implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Enumerated (EnumType.STRING)
    private CellInfo cellInfo;
    @ManyToOne
    @JoinColumn(name = "asuLevel_id",
            foreignKey = @ForeignKey(name = "ASU_LEVEL_ID_FK")
    )
    private AsuLevel asuLevel;
    @ManyToOne
    @JoinColumn(name = "barLevel_id",
            foreignKey = @ForeignKey(name = "BAR_LEVEL_ID_FK")
    )
    private BarLevel barLevel;
    @ManyToOne
    @JoinColumn(name = "dbmLevel_id",
            foreignKey = @ForeignKey(name = "DBM_LEVEL_ID_FK")
    )
    private DbmLevel dbmLevel;
    @Enumerated (EnumType.STRING)
    private Provider provider;

    public static enum Provider {
        MTS,
        MEGAFON,
        BEELINE,
        TELE2,
        YOTA,
        ROSTELECOM
    }

    public static enum CellInfo {
        GSM,
        LTE,
        CDMA,
        WCDMA
    }

    public Signal() {
    }

    public Signal(int id, CellInfo cellInfo, AsuLevel asuLevel, BarLevel barLevel, DbmLevel dbmLevel, Provider provider) {
        this.id = id;
        this.cellInfo = cellInfo;
        this.asuLevel = asuLevel;
        this.barLevel = barLevel;
        this.dbmLevel = dbmLevel;
        this.provider = provider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CellInfo getCellInfo() {
        return cellInfo;
    }

    public void setCellInfo(CellInfo cellInfo) {
        this.cellInfo = cellInfo;
    }

    public AsuLevel getAsuLevel() {
        return asuLevel;
    }

    public void setAsuLevel(AsuLevel asuLevel) {
        this.asuLevel = asuLevel;
    }

    public BarLevel getBarLevel() {
        return barLevel;
    }

    public void setBarLevel(BarLevel barLevel) {
        this.barLevel = barLevel;
    }

    public DbmLevel getDbmLevel() {
        return dbmLevel;
    }

    public void setDbmLevel(DbmLevel dbmLevel) {
        this.dbmLevel = dbmLevel;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
