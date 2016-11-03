package by.zagart.observer.database.entities;

import by.zagart.observer.interfaces.Identifiable;
import by.zagart.observer.utils.DataUtil;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Класс-сущность, описывает объекты типа "информация о стенде" и их свойства.
 * Также предоставляет доступ к полям.
 */
@Entity
@Table(name = "STAND")
public class Stand implements Identifiable<Long>, Serializable {
    public static final String CONVERSION_STRING_TO_PROPERTIES_ERROR
            = "Stand class. Conversion (string-to-properties) error: ";
    public static final String DESCRIPTION = "description";
    public static final String ID = "id";
    public static final String NUMBER = "number";
    public static final String STAND = "stand";
    public static final Logger logger = Logger.getLogger(Stand.class);
    private static final long SERIAL_VERSION_UID = 2L;
    private String mDescription;
    private Long mId;
    private List<Module> mModules;
    private String mNumber;

    public static Stand parseSerialString(String serialData) {
        Stand stand = new Stand();
        try {
            Properties properties = DataUtil.convertStringToProperties(serialData);
            stand.setNumber(properties.getProperty(STAND));
            stand.setDescription(properties.getProperty(DESCRIPTION));
        } catch (IOException ex) {
            logger.error(CONVERSION_STRING_TO_PROPERTIES_ERROR + ex.getStackTrace());
        }
        if (stand.getNumber() == null) {
            throw new NoClassDefFoundError();
        }
        return stand;
    }

    public void addModule(Module module) {
        if (mModules == null) {
            mModules = new ArrayList<>();
        }
        module.setStand(this);
        this.mModules.add(module);
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        this.mId = id;
    }

    @OneToMany(mappedBy = "stand", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    public List<Module> getModules() {
        return mModules;
    }

    public void setModules(List<Module> moduleList) {
        this.mModules = moduleList;
    }

    @Column(name = "NUMBER")
    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        this.mNumber = number;
    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + (mNumber != null ? mNumber.hashCode() : 0);
        result = 31 * result + (mModules != null ? mModules.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stand stand = (Stand) o;
        if (!mId.equals(stand.mId)) return false;
        if (mNumber != null ? !mNumber.equals(stand.mNumber) : stand.mNumber != null) return false;
        return mModules != null ? mModules.equals(stand.mModules) : stand.mModules == null;
    }

    public String toJSONString() {
        JSONObject stand = new JSONObject();
        stand.put(ID, mId);
        stand.put(NUMBER, mNumber);
        stand.put(DESCRIPTION, mDescription);
        return stand.toJSONString();
    }
}
