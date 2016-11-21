package observer.zagart.by.client.network.api;

import android.support.annotation.Keep;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import observer.zagart.by.client.application.constants.Constants;

/**
 * @author zagart
 */
//TODO it's not callback
//TODO move working with json to another class
//TODO ObserverCallback shouldn't work with accountManager
@Keep
public class ObserverCallback {

    @SuppressWarnings("unchecked")
    @Nullable
    public static <Entity> List<Entity> onResponseReceived(final String pServerResponse)
            throws JSONException {
        if (pServerResponse == null) {
            return null;
        }
        final JSONObject jsonObjectResponse = new JSONObject(pServerResponse);
        final String reflectedAction = jsonObjectResponse.getString(Constants.REFLECTION);
        if (reflectedAction == null) {
            return null;
        }
        switch (reflectedAction) {
            case Criteria.GET_STANDS:
                return (List<Entity>) ObserverJsonParser.parseStandsResponse(jsonObjectResponse);
            case Criteria.GET_MODULES:
                return (List<Entity>) ObserverJsonParser.parseModulesResponse(jsonObjectResponse);
            default:
                return null;
        }
    }
}
