package observer.zagart.by.client.network.http.responses;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.application.constants.JSONConstants;
import observer.zagart.by.client.mvp.models.repository.entities.Module;
import observer.zagart.by.client.mvp.models.repository.entities.Stand;
import observer.zagart.by.client.network.Criteria;
import observer.zagart.by.client.network.http.requests.observer.base.BaseObserverRequest;

/**
 * JSON data parser for results from Observer's server requests.
 *
 * @author zagart
 * @see BaseObserverRequest
 */
public class ObserverJsonResponse {

    final private String mResponse;

    public ObserverJsonResponse(final String pResponse) {
        mResponse = pResponse;
    }

    @Nullable
    public String extractToken() throws JSONException {
        if (mResponse == null) {
            return null;
        }
        JSONObject jsonResponse = new JSONObject(mResponse);
        if (jsonResponse.has(JSONConstants.TOKEN)) {
            return jsonResponse.getString(JSONConstants.TOKEN);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <Entity> List<Entity> extractEntities() throws JSONException {
        if (mResponse == null) {
            return null;
        }
        final JSONObject jsonObjectResponse = new JSONObject(mResponse);
        final String reflectedAction = jsonObjectResponse.getString(JSONConstants.REFLECTION);
        if (reflectedAction == null) {
            return null;
        }
        switch (reflectedAction) {
            case Criteria.GET_STANDS:
                return (List<Entity>) parseStandsResponse(jsonObjectResponse);
            case Criteria.GET_MODULES:
                return (List<Entity>) parseModulesResponse(jsonObjectResponse);
            default:
                return null;
        }
    }

    @NonNull
    private List<Module> parseModulesResponse(final JSONObject pJSONObject) throws JSONException {
        final List<Module> moduleList = new ArrayList<>();
        if (pJSONObject.has(JSONConstants.MODULES_KEY)) {
            final JSONArray modules = pJSONObject.getJSONArray(JSONConstants.MODULES_KEY);
            for (int i = 0; i < modules.length(); i++) {
                final JSONObject jsonStand = modules.getJSONObject(i);
                moduleList.add(new Module().extractFromJsonObject(jsonStand));
            }
        }
        return moduleList;
    }

    @NonNull
    private List<Stand> parseStandsResponse(final JSONObject pJSONObject) throws JSONException {
        final List<Stand> standList = new ArrayList<>();
        if (pJSONObject.has(JSONConstants.STANDS_KEY)) {
            final JSONArray stands = pJSONObject.getJSONArray(JSONConstants.STANDS_KEY);
            for (int i = 0; i < stands.length(); i++) {
                final JSONObject jsonStand = stands.getJSONObject(i);
                standList.add(new Stand().extractFromJsonObject(jsonStand));
            }
        }
        return standList;
    }
}
