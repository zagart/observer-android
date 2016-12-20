package observer.zagart.by.client.mvp.models.repository.entities;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import observer.zagart.by.client.application.utils.CursorUtil;
import observer.zagart.by.client.mvp.models.repository.contracts.StandContract;

/**
 * Model for stand.
 */
public class Stand implements IEntity<Stand, ContentValues, Long> {

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

    public Stand setDescription(final String description) {
        mDescription = description;
        return this;
    }

    @Override
    public Stand getNewEntity() {
        return new Stand();
    }

    @Override
    public Long getId() {
        return mId;
    }

    public Stand setId(final Long id) {
        mId = id;
        return this;
    }

    public String getNumber() {
        return mNumber;
    }

    public Stand setNumber(final String number) {
        mNumber = number;
        return this;
    }

    @Override
    public boolean equals(final Object pO) {
        if (this == pO) {
            return true;
        }
        if (pO == null || getClass() != pO.getClass()) {
            return false;
        }
        final Stand stand = (Stand) pO;
        return mId.equals(stand.mId);
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }
}
