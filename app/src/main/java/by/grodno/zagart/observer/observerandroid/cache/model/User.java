package by.grodno.zagart.observer.observerandroid.cache.model;
import android.accounts.Account;
import android.content.ContentValues;
import android.os.Parcel;

import by.grodno.zagart.observer.observerandroid.cache.model.annotations.Id;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.NotNull;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.Table;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbLong;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbString;
import by.grodno.zagart.observer.observerandroid.interfaces.IConvertible;

/**
 * Model for user. Also can be used as Account for
 * Account Manager.
 *
 * @author zagart
 * @see android.accounts.AccountManager
 */
public class User extends Account implements IConvertible<ContentValues> {
    public static final String TYPE = "by.zagart.observer";
    private Long mId;
    private String mLogin;
    private String mPassword;
    private Long mTimestampOfRegistration;
    private String mToken;
    private Long mTimestampOfLastActivity;

    public User(final Parcel in) {
        super(in);
    }

    public User(final String name) {
        super(name, TYPE);
    }

    @Override
    public ContentValues convert() {
        ContentValues user = new ContentValues();
        user.put(UserContract.ID, mId);
        user.put(UserContract.LOGIN, mLogin);
        user.put(UserContract.TIMESTAMP_OF_REGISTRATION, mTimestampOfRegistration);
        user.put(UserContract.TOKEN, mToken);
        user.put(UserContract.TIMESTAMP_OF_LAST_ACTIVITY, mTimestampOfLastActivity);
        return user;
    }

    public Long getId() {
        return mId;
    }

    public void setId(final Long pId) {
        mId = pId;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(final String pLogin) {
        mLogin = pLogin;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(final String pPassword) {
        mPassword = pPassword;
    }

    public Long getTimestampOfLastActivity() {
        return mTimestampOfLastActivity;
    }

    public void setTimestampOfLastActivity(final Long pTimestampOfLastActivity) {
        mTimestampOfLastActivity = pTimestampOfLastActivity;
    }

    public Long getTimestampOfRegistration() {
        return mTimestampOfRegistration;
    }

    public void setTimestampOfRegistration(final Long pTimestampOfRegistration) {
        mTimestampOfRegistration = pTimestampOfRegistration;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(final String pToken) {
        mToken = pToken;
    }

    @Table(name = "USER")
    public static class UserContract {
        @Id
        @NotNull
        @dbLong
        public static final String ID = "id";
        @NotNull
        @dbString
        public static final String LOGIN = "login";
        @NotNull
        @dbString
        public static final String PASSWORD = "password";
        @NotNull
        @dbLong
        public static final String TIMESTAMP_OF_REGISTRATION = "timestamp_of_registration";
        @NotNull
        @dbString
        public static final String TOKEN = "token";
        @NotNull
        @dbLong
        public static final String TIMESTAMP_OF_LAST_ACTIVITY = "timestamp_of_last_activity";
    }
}
