package by.grodno.zagart.observer.observerandroid.cache.model;
import android.content.ContentValues;

import by.grodno.zagart.observer.observerandroid.cache.model.annotations.Table;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbLong;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbString;
import by.grodno.zagart.observer.observerandroid.interfaces.IConvertible;

/**
 * Model for user.
 *
 * @author zagart
 */
public class User implements IConvertible<ContentValues> {
    private Long mId;
    private String mLogin;
    private Long mTimestampOfRegistration;
    private String mToken;
    private Long mTimestampOfLastActivity;

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
        @dbLong
        public static final String ID = "id";
        @dbString
        public static final String LOGIN = "LOGIN";
        @dbLong
        public static final String TIMESTAMP_OF_REGISTRATION = "timestamp_of_registration";
        @dbString
        public static final String TOKEN = "token";
        @dbLong
        public static final String TIMESTAMP_OF_LAST_ACTIVITY = "timestamp_of_last_activity";
    }
}
