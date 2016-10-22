package by.grodno.zagart.observer.observerandroid.cache.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model for stand.
 */
public class Stand {
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
