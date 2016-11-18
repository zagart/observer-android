package by.zagart.observer.model.entities;

import by.zagart.observer.interfaces.Identifiable;
import by.zagart.observer.utils.DataUtil;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import static by.zagart.observer.model.entities.Module.Fields.*;
import static by.zagart.observer.model.entities.Module.SerialConstants.*;

/**
 * Класс описывает объекты типа "модуль" и их свойства.
 * Также предоставляет доступ к полям.
 */
@Entity
@Table(name = "MODULE")
public class Module implements Identifiable<Long>, Serializable {
    public static final String CONVERSION_STRING_TO_PROPERTIES_ERROR
            = "Module class. Conversion (string-to-properties) error: ";
    public static final Logger logger = Logger.getLogger(Module.class);
    private static final long SERIAL_VERSION_UID = 1L;
    private Long mId;
    private String mName;
    private Stand mStand;
    private String mStatus;
    private Date mStatusChangeDate;
    private String mValue;

    public static Module parseSerialString(String serialData) {
        final Module module = new Module();
        try {
            Properties properties = DataUtil.convertStringToProperties(serialData);
            module.setName(properties.getProperty(SERIAL_MODULE));
            module.setStatus(String.format(STATUS_INIT_STRING,
                    properties.getProperty(SERIAL_EVENT),
                    properties.getProperty(SERIAL_VALUE)));
            module.setStatusChangeDate(new Date());
        } catch (IOException ex) {
            logger.error(CONVERSION_STRING_TO_PROPERTIES_ERROR + ex.getStackTrace());
        }
        if (module.getName() == null || module.getStatus() == null) {
            throw new NoClassDefFoundError();
        }
        return module;
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        this.mId = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @ManyToOne
    @JoinColumn(name = "STAND_ID")
    public Stand getStand() {
        return mStand;
    }

    public void setStand(Stand stand) {
        this.mStand = stand;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STATUS_DATE")
    public Date getStatusChangeDate() {
        return mStatusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.mStatusChangeDate = statusChangeDate;
    }

    @Column(name = "SERIAL_VALUE")
    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mStatus != null ? mStatus.hashCode() : 0);
        result = 31 * result + (mStatusChangeDate != null ? mStatusChangeDate.hashCode() : 0);
        result = 31 * result + (mStand != null ? mStand.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        if (!mId.equals(module.mId)) return false;
        if (mName != null ? !mName.equals(module.mName) : module.mName != null) return false;
        if (mStatus != null ? !mStatus.equals(module.mStatus) : module.mStatus != null) return false;
        if (mStatusChangeDate != null ? !mStatusChangeDate.equals(module.mStatusChangeDate) : module.mStatusChangeDate != null)
            return false;
        return mStand != null ? mStand.equals(module.mStand) : module.mStand == null;
    }

    public JSONObject toJSONObject() {
        JSONObject module = new JSONObject();
        module.put(ID, mId);
        module.put(NAME, mName);
        module.put(STAND_ID, mStand);
        module.put(STATUS, mStatus);
        module.put(STATUS_CHANGE_DATE, mStatusChangeDate);
        module.put(VALUE, mValue);
        return module;
    }

    class SerialConstants {
        public static final String SERIAL_EVENT = "event";
        public static final String SERIAL_MODULE = "module";
        public static final String SERIAL_VALUE = "value";
        public static final String STATUS_INIT_STRING = "%s Новое значение -> %s.";
    }

    public class Fields {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String STAND_ID = "stand_id";
        public static final String STATUS = "status";
        public static final String STATUS_CHANGE_DATE = "status_change_date";
        public static final String VALUE = "value";
    }
}
