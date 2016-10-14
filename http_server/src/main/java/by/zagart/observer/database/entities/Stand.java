package by.zagart.observer.database.entities;

import by.zagart.observer.interfaces.Identifiable;
import by.zagart.observer.utils.DataUtil;
import org.apache.log4j.Logger;

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
    public static final Logger logger = Logger.getLogger(Stand.class);
    private static final long SERIAL_VERSION_UID = 2L;
    private String description;
    private Long id;
    private List<Module> moduleList;
    private String number;

    public static Stand parseSerialString(String serialData) {
        Stand stand = new Stand();
        try {
            Properties properties = DataUtil.convertStringToProperties(serialData);
            stand.setNumber(properties.getProperty("stand"));
            stand.setDescription(properties.getProperty("description"));
        } catch (IOException ex) {
            logger.error("Stand class. Convertion (string-to-properties) error: " + ex.getStackTrace());
        }
        if (stand.getNumber() == null) {
            throw new NoClassDefFoundError();
        }
        return stand;
    }

    public void addModule(Module module) {
        if (moduleList == null) {
            moduleList = new ArrayList<>();
        }
        module.setStand(this);
        this.moduleList.add(module);
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "stand", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    @Column(name = "NUMBER")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (moduleList != null ? moduleList.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stand stand = (Stand) o;
        if (!id.equals(stand.id)) return false;
        if (number != null ? !number.equals(stand.number) : stand.number != null) return false;
        return moduleList != null ? moduleList.equals(stand.moduleList) : stand.moduleList == null;
    }
}
