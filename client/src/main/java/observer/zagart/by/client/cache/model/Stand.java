package observer.zagart.by.client.cache.model;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import observer.zagart.by.client.cache.model.contracts.StandContract;
import observer.zagart.by.client.interfaces.IConvertible;

/**
 * Model for stand.
 */
public class Stand implements IConvertible<ContentValues> {
    public static final int STAND_ID_LIMIT = 100;
    public static final int STAND_NUMBER_LIMIT = 1000;
    private Long mId;
    private String mNumber;
    private String mDescription;

    public static Stand createRandomStand() {
        Random random = new Random();
        Stand stand = new Stand();
        stand.setId((long) random.nextInt(STAND_ID_LIMIT));
        stand.setNumber(String.valueOf(random.nextInt(STAND_NUMBER_LIMIT)));
        stand.setDescription(stand.getNumber() + stand.getId());
        return stand;
    }

    public static List<Stand> createStandList(int pSize) {
        List<Stand> stands = new ArrayList<>();
        while (pSize > 0) {
            stands.add(createRandomStand());
            pSize--;
        }
        return stands;
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
