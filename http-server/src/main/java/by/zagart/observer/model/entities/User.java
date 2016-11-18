package by.zagart.observer.model.entities;

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
    private static final long SERIAL_VERSION_UID = 3L; //TODO correct UID for models
    private String mAvatar;
    private Long mId;
    private String mInfo;
    private String mLogin;
    private String mPassword;
    private Role mRole;
    private String mToken;

    @Column(name = "AVATAR")
    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        this.mAvatar = avatar;
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

    @Column(name = "INFO")
    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        this.mInfo = info;
    }

    @Column(name = "LOGIN")
    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        this.mLogin = login;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    @Column(name = "ROLE")
    public Role getRole() {
        return mRole;
    }

    public void setRole(Role pRole) {
        if (pRole == null) {
            mRole = Role.USER;
        } else {
            this.mRole = pRole;
        }
    }

    @Column(name = "TOKEN")
    public String getToken() {
        return mToken;
    }

    public void setToken(String pToken) {
        mToken = pToken;
    }

    public enum Role {
        GUEST, USER, ADMIN
    }

    public class Fields {
        public static final String AVATAR = "avatar";
        public static final String ID = "id";
        public static final String INFO = "info";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String ROLE = "role";
        public static final String TOKEN = "token";
    }
}
