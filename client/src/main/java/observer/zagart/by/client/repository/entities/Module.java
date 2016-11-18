package observer.zagart.by.client.repository.entities;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import observer.zagart.by.client.interfaces.IConvertible;
import observer.zagart.by.client.repository.entities.contracts.ModuleContract;

/**
 * StandModel for module.
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
    //TODO remove
    public static List<Module> createModuleList(int pSize) {
        List<Module> stands = new ArrayList<>();
        while (pSize > 0) {
            stands.add(createRandomModule());
            pSize--;
        }
        return stands;
    }

    public static Module createRandomModule() {
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
    //TODO static is evil.
    public static Module parseCursorRow(final Cursor pCursor) {
        Module module = new Module();
        int idIndex = pCursor.getColumnIndex(ModuleContract.ID);
        int nameIndex = pCursor.getColumnIndex(ModuleContract.NAME);
        int statusIndex = pCursor.getColumnIndex(ModuleContract.STATUS);
        int valueIndex = pCursor.getColumnIndex(ModuleContract.VALUE);
        int statusChangeDateIndex = pCursor.getColumnIndex(ModuleContract.STATUS_CHANGE_DATE);
        int standIdIndex = pCursor.getColumnIndex(ModuleContract.STAND_ID);
        Long id = pCursor.getLong(idIndex);
        String name = pCursor.getString(nameIndex);
        String status = pCursor.getString(statusIndex);
        String value = pCursor.getString(valueIndex);
        Long statusChangeDate = pCursor.getLong(statusChangeDateIndex);
        Long standId = pCursor.getLong(standIdIndex);
        module.setId(id);
        module.setName(name);
        module.setStatus(status);
        module.setValue(value);
        module.setStatusChangeDate(new Date(statusChangeDate));
        module.setStandId(standId);
        return module;
    }

    public static Module parseJsonObject(final JSONObject pJsonModule) throws JSONException {
        final Module module = new Module();
        module.setId(pJsonModule.getLong(ModuleContract.ID));
        module.setName(pJsonModule.getString(ModuleContract.NAME));
        module.setStatus(pJsonModule.getString(ModuleContract.STATUS));
        module.setValue(pJsonModule.getString(ModuleContract.VALUE));
        module.setStatusChangeDate(new Date(pJsonModule.getInt(ModuleContract.STATUS_CHANGE_DATE)));
        module.setStandId(pJsonModule.getLong(ModuleContract.STAND_ID));
        return module;
    }

    @Override
    public ContentValues convert() {
        ContentValues module = new ContentValues();
        module.put(ModuleContract.ID, mId);
        module.put(ModuleContract.NAME, mName);
        module.put(ModuleContract.STAND_ID, mStandId);
        module.put(ModuleContract.STATUS, mStatus);
        module.put(ModuleContract.VALUE, mValue);
        module.put(ModuleContract.STATUS_CHANGE_DATE, mStatusChangeDate.getTime());
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
}
