package observer.zagart.by.client.application.interfaces;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Implementations if this interface can extract themselves from
 * different types of data.
 *
 * @author zagart
 */

public interface IExtractable<Entity> {

    Entity extractFromJsonObject(final JSONObject pJsonModule) throws JSONException;

    Entity extractFromCursor(final Cursor pCursor);
}
