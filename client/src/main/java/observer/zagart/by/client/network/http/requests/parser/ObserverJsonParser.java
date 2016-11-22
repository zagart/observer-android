package observer.zagart.by.client.network.http.requests.parser;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.application.constants.Constants;
import observer.zagart.by.client.mvp.models.repository.entities.Module;
import observer.zagart.by.client.mvp.models.repository.entities.Stand;
import observer.zagart.by.client.network.Criteria;

/**
 * JSON data parser for responses from Observer server.
 *
 * @author zagart
 */
public class ObserverJsonParser {

    @Nullable
    public static String getTokenFromResponseString(final String pResponse) {
        if (pResponse == null) {
            return null;
        }
        try {
            JSONObject jsonResponse = new JSONObject(pResponse);
            if (jsonResponse.has(Constants.TOKEN)) {
                return jsonResponse.getString(Constants.TOKEN);
            }
            return null;
        } catch (JSONException pEx) {
            //TODO remove all logs by proguard
            Log.e(ObserverJsonParser.class.getSimpleName(), pEx.getMessage(), pEx);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <Entity> List<Entity> parseServerResponse(final String pServerResponse)
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

    @NonNull
    private static List<Module> parseModulesResponse(final JSONObject pJSONObject)
            throws JSONException {
        final List<Module> moduleList = new ArrayList<>();
        if (pJSONObject.has(Constants.MODULES_KEY)) {
            final JSONArray modules = pJSONObject.getJSONArray(Constants.STANDS_KEY);
            for (int i = 0; i < modules.length(); i++) {
                final JSONObject jsonStand = modules.getJSONObject(i);
                moduleList.add(new Module().extractFromJsonObject(jsonStand));
            }
        }
        return moduleList;
    }

    @NonNull
    private static List<Stand> parseStandsResponse(final JSONObject pJSONObject)
            throws JSONException {
        final List<Stand> standList = new ArrayList<>();
        if (pJSONObject.has(Constants.STANDS_KEY)) {
            final JSONArray stands = pJSONObject.getJSONArray(Constants.STANDS_KEY);
            for (int i = 0; i < stands.length(); i++) {
                final JSONObject jsonStand = stands.getJSONObject(i);
                standList.add(new Stand().extractFromJsonObject(jsonStand));
            }
        }
        return standList;
    }
}
