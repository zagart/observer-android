package by.zagart.observer.database.entities;

import by.zagart.observer.interfaces.Identifiable;
import by.zagart.observer.utils.DataUtil;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

/**
 * Класс описывает объекты типа "модуль" и их свойства.
 * Также предоставляет доступ к полям.
 */
@Entity
@Table(name = "MODULE")
public class Module implements Identifiable<Long>, Serializable {
    public static final String CONVERSION_STRING_TO_PROPERTIES_ERROR
            = "Module class. Conversion (string-to-properties) error: ";
    public static final String EVENT = "event";
    public static final String MODULE = "module";
    public static final String STATUS_INIT_STRING = "%s Новое значение -> %s.";
    public static final String VALUE = "value";
    public static final Logger logger = Logger.getLogger(Module.class);
    private static final long SERIAL_VERSION_UID = 1L;
    private Long id;
    private String name;
    private Stand stand;
    private String status;
    private Date statusChangeDate;
    private String value;

    public static Module parseSerialString(String serialData) {
        Module module = new Module();
        try {
            Properties properties = DataUtil.convertStringToProperties(serialData);
            module.setName(properties.getProperty(MODULE));
            module.setStatus(String.format(STATUS_INIT_STRING,
                    properties.getProperty(EVENT),
                    properties.getProperty(VALUE)));
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
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "STAND_ID")
    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STATUS_DATE")
    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    @Column(name = "VALUE")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (statusChangeDate != null ? statusChangeDate.hashCode() : 0);
        result = 31 * result + (stand != null ? stand.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        if (!id.equals(module.id)) return false;
        if (name != null ? !name.equals(module.name) : module.name != null) return false;
        if (status != null ? !status.equals(module.status) : module.status != null) return false;
        if (statusChangeDate != null ? !statusChangeDate.equals(module.statusChangeDate) : module.statusChangeDate != null)
            return false;
        return stand != null ? stand.equals(module.stand) : module.stand == null;
    }
}
