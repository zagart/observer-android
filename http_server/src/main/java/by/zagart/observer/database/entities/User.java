package by.zagart.observer.database.entities;

import by.zagart.observer.interfaces.Identifiable;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс-сущность, описывает объекты типа "пользователь" и их свойства.
 * Также предоставляет доступ к полям.
 */
@Entity
@Table(name = "USER")
public class User implements Identifiable<Long>, Serializable {
    public static final Logger logger = Logger.getLogger(Stand.class);
    private static final long SERIAL_VERSION_UID = 3L;
    private String avatar;
    private Long id;
    private String info;
    private String login;
    private String password;
    private Role role;

    @Column(name = "AVATAR")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    @Column(name = "INFO")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column(name = "LOGIN")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "ROLE")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        GUEST, USER, ADMIN
    }
}
