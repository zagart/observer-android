package by.grodno.zagart.observer.observerandroid.cache.model;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import by.grodno.zagart.observer.observerandroid.cache.model.annotations.Table;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbLong;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbString;
import by.grodno.zagart.observer.observerandroid.interfaces.IConvertible;

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

    @Table(name = "STAND")
    public static class StandContract {
        @dbLong
        public static final String ID = "id";
        @dbString
        public static final String NUMBER = "number";
        @dbString
        public static final String DESCRIPTION = "description";
    }
}
