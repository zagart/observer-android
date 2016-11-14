package observer.zagart.by.client.account;

import android.accounts.Account;

/**
 * Account model to Observer HTTP-server.
 *
 * @author zagart
 */
public class ObserverAccount extends Account {

    public static final String EXTRA_TOKEN_TYPE = "by.zagart.observer.extra_token_type";
    static final String AUTH_TOKEN_TYPE = "by.zagart.observer.jwt";
    private static final String TYPE = "by.zagart.observer";

    public ObserverAccount(final String name) {
        super(name, TYPE);
    }
}
