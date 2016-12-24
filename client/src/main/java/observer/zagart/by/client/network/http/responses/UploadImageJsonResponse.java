package observer.zagart.by.client.network.http.responses;

import org.json.JSONException;
import org.json.JSONObject;

import observer.zagart.by.client.application.constants.NetworkConstants;

/**
 * JSON data parser for upload results from image server.
 *
 * @author zagart
 */

public class UploadImageJsonResponse {

    final private JSONObject mResponse;

    public UploadImageJsonResponse(final String pResponse) throws JSONException {
        mResponse = new JSONObject(pResponse);
    }

    public String extractImageUrl() throws JSONException {
        return mResponse.get(NetworkConstants.URL).toString();
    }
}
