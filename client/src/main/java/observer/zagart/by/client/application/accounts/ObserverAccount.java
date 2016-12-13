package observer.zagart.by.client.application.accounts;

import android.accounts.Account;
import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import observer.zagart.by.client.mvp.models.repository.entities.IEntity;

/**
 * Account entity for Android application.
 *
 * @author zagart
 */
public class ObserverAccount
        extends Account implements IEntity<ObserverAccount, ContentValues, Long> {

    public static final int AUTHENTICATED_ACCOUNT_INDEX = 0;
    public static final String TYPE = "by.zagart.observer";
    public static final String NAME = "by.zagart.observer.NAME";
    public static final String PASSWORD = "by.zagart.observer.PASSWORD";
    static final String TOKEN = "by.zagart.observer.JWT";
    static final String EXTRA_TOKEN_TYPE = "by.zagart.observer.EXTRA_TOKEN_TYPE";
    private static final String GUEST_LOGIN = "guest";
    private static final String GUEST = "guest";
    private String mPassword;
    private String mToken;

    public ObserverAccount(final String name) {
        super(name, TYPE);
    }

    public static ObserverAccount getGuestAccount() {
        return new ObserverAccount(GUEST_LOGIN);
    }

    public String getPassword() {
        return mPassword;
    }

    public ObserverAccount setPassword(final String pPassword) {
        mPassword = pPassword;
        return this;
    }

    public String getToken() {
        return mToken;
    }

    public ObserverAccount setToken(final String pToken) {
        mToken = pToken;
        return this;
    }

    @Override
    public ObserverAccount getNewEntity() {
        return new ObserverAccount(GUEST_LOGIN);
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public ContentValues convert() {
        return null;
    }

    @Override
    public ObserverAccount extractFromJsonObject(final JSONObject pJsonModule) throws
            JSONException {
        return null;
    }

    @Override
    public ObserverAccount extractFromCursor(final Cursor pCursor) {
        return null;
    }
}
