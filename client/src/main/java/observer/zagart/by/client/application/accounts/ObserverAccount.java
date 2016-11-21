package observer.zagart.by.client.application.accounts;

import android.accounts.Account;

/**
 * Account entity for Observer HTTP-server.
 *
 * @author zagart
 */
public class ObserverAccount extends Account {

    public static final String TYPE = "by.zagart.observer";
    public static final String NAME = "by.zagart.observer.NAME";
    public static final String PASSWORD = "by.zagart.observer.PASSWORD";
    public static final String TOKEN = "by.zagart.observer.JWT";
    static final String EXTRA_TOKEN_TYPE = "by.zagart.observer.EXTRA_TOKEN_TYPE";
    private String mPassword;
    private String mToken;

    public ObserverAccount(final String name) {
        super(name, TYPE);
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
}
