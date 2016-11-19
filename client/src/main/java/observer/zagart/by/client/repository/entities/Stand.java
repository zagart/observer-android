package observer.zagart.by.client.repository.entities;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import observer.zagart.by.client.interfaces.IConvertible;
import observer.zagart.by.client.repository.entities.contracts.StandContract;
import observer.zagart.by.client.utils.CursorUtil;

/**
 * Model for stand.
 */
public class Stand implements IConvertible<ContentValues> {

    private Long mId;
    private String mNumber;
    private String mDescription;

    public Stand extractFromCursor(Cursor pCursor) {
        mId = CursorUtil.getLong(pCursor, StandContract.ID);
        mDescription = CursorUtil.getString(pCursor, StandContract.DESCRIPTION);
        mNumber = CursorUtil.getString(pCursor, StandContract.NUMBER);
        return this;
    }

    public Stand extractFromJsonObject(JSONObject pJSONStand) throws JSONException {
        mId = pJSONStand.getLong(StandContract.ID);
        mDescription = pJSONStand.getString(StandContract.DESCRIPTION);
        mNumber = pJSONStand.getString(StandContract.NUMBER);
        return this;
    }

    @Override
    public ContentValues convert() {
        ContentValues stand = new ContentValues();
        stand.put(StandContract.ID, mId);
        stand.put(StandContract.DESCRIPTION, mDescription);
        stand.put(StandContract.NUMBER, mNumber);
        return stand;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(final String description) {
        mDescription = description;
    }

    public Long getId() {
        return mId;
    }

    public void setId(final Long id) {
        mId = id;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(final String number) {
        mNumber = number;
    }
}
