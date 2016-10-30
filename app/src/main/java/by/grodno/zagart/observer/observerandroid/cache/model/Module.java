package by.grodno.zagart.observer.observerandroid.cache.model;
import android.content.ContentValues;

import java.util.Date;
import java.util.Random;

import by.grodno.zagart.observer.observerandroid.cache.model.annotations.Table;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbLong;
import by.grodno.zagart.observer.observerandroid.cache.model.annotations.dbString;
import by.grodno.zagart.observer.observerandroid.interfaces.IConvertible;

/**
 * Model for module.
 */
public class Module implements IConvertible<ContentValues> {
    public static final int MODULE_VALUE_LIMIT = 99;
    public static final int MODULE_ID_LIMIT = 9;
    private Long mId;
    private String mName;
    private Long mStandId;
    private String mStatus;
    private Date mStatusChangeDate;
    private String mValue;

    public static Module getRandomModule() {
        Random random = new Random();
        Module module = new Module();
        module.setId((long) random.nextInt(MODULE_ID_LIMIT));
        module.setName("Module" + module.getId());
        module.setStandId((long) random.nextInt(Stand.STAND_ID_LIMIT));
        module.setStatus("Status" + module.getName());
        module.setValue(String.valueOf(random.nextInt(MODULE_VALUE_LIMIT)));
        module.setStatusChangeDate(new Date());
        return module;
    }

    @Override
    public ContentValues convert() {
        ContentValues module = new ContentValues();
        module.put(ModuleContract.ID, mId);
        module.put(ModuleContract.NAME, mName);
        module.put(ModuleContract.STAND_ID, mStandId);
        module.put(ModuleContract.STATUS, mStatus);
        module.put(ModuleContract.STATUS_CHANGE_DATE, mStatusChangeDate.getTime());
        module.put(ModuleContract.VALUE, mValue);
        return module;
    }

    public Long getId() {
        return mId;
    }

    public void setId(final Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public Long getStandId() {
        return mStandId;
    }

    public void setStandId(final Long standId) {
        mStandId = standId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(final String status) {
        mStatus = status;
    }

    public Date getStatusChangeDate() {
        return mStatusChangeDate;
    }

    public void setStatusChangeDate(final Date statusChangeDate) {
        mStatusChangeDate = statusChangeDate;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(final String value) {
        mValue = value;
    }

    @Table(name = "MODULE")
    public static class ModuleContract {
        @dbLong
        public static final String ID = "id";
        @dbString
        public static final String NAME = "name";
        @dbLong
        public static final String STAND_ID = "stand_id";
        @dbString
        public static final String STATUS = "status";
        @dbLong
        public static final String STATUS_CHANGE_DATE = "status_change_date";
        @dbString
        public static final String VALUE = "value";
    }
}
