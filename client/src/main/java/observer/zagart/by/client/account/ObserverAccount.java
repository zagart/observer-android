package observer.zagart.by.client.account;
import android.accounts.Account;
import android.os.Parcel;

/**
 * Account model to Observer HTTP-server.
 *
 * @author zagart
 */
public class ObserverAccount extends Account {
    public static final String TYPE = "by.zagart.observer";

    public ObserverAccount(final String name) {
        super(name, TYPE);
    }

    public ObserverAccount(final Parcel in) {
        super(in);
    }
}
