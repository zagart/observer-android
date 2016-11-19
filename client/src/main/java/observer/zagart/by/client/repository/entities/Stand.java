package observer.zagart.by.client.repository.entities;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import observer.zagart.by.client.interfaces.IConvertible;
import observer.zagart.by.client.repository.entities.contracts.StandContract;

/**
 * StandModel for stand.
 */
public class Stand implements IConvertible<ContentValues> {

    private Long mId;
    private String mNumber;
    private String mDescription;

    public static Stand parseJsonObject(JSONObject pJsonStand) throws JSONException {
        final Stand stand = new Stand();
        stand.setId(pJsonStand.getLong(StandContract.ID));
        stand.setDescription(pJsonStand.getString(StandContract.DESCRIPTION));
        stand.setNumber(pJsonStand.getString(StandContract.NUMBER));
        return stand;
    }

    public static Stand parseCursorRow(Cursor pCursor) {
        Stand stand = new Stand();
        int idIndex = pCursor.getColumnIndex(StandContract.ID);
        int numberIndex = pCursor.getColumnIndex(StandContract.NUMBER);
        int descriptionIndex = pCursor.getColumnIndex(StandContract.DESCRIPTION);
        Long id = pCursor.getLong(idIndex);
        String number = pCursor.getString(numberIndex);
        String description = pCursor.getString(descriptionIndex);
        stand.setId(id);
        stand.setNumber(number);
        stand.setDescription(description);
        return stand;
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
