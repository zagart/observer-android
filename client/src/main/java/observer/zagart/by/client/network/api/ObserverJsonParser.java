package observer.zagart.by.client.network.api;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.application.constants.Constants;
import observer.zagart.by.client.mvp.models.repository.entities.Module;
import observer.zagart.by.client.mvp.models.repository.entities.Stand;

/**
 * JSON data parser for responses from Observer server.
 *
 * @author zagart
 */

class ObserverJsonParser {

    static List<Module> parseModulesResponse(final JSONObject pJSONObject)
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
    static List<Stand> parseStandsResponse(final JSONObject pJSONObject)
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
