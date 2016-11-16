package observer.zagart.by.client.account;

import android.accounts.Account;

/**
 * Account model to Observer HTTP-server.
 *
 * @author zagart
 */
public class ObserverAccount extends Account {

    static final String EXTRA_TOKEN_TYPE = "by.zagart.observer.EXTRA_TOKEN_TYPE";
    static final String AUTH_TOKEN_TYPE = "by.zagart.observer.JWT";
    private static final String TYPE = "by.zagart.observer";

    public ObserverAccount(final String name) {
        super(name, TYPE);
    }
}
