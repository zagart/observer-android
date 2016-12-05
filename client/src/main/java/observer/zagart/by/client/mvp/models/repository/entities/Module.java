package observer.zagart.by.client.mvp.models.repository.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import observer.zagart.by.client.application.utils.CursorUtil;
import observer.zagart.by.client.mvp.models.repository.contracts.ModuleContract;

/**
 * Model for module.
 */
public class Module implements IEntity<Module, ContentValues, Long>, Comparable<Module> {

    private Long mId;
    private String mName;
    private Long mStandId;
    private String mStatus;
    private Date mStatusChangeDate;
    private String mValue;

    public Module extractFromJsonObject(final JSONObject pJsonModule) throws JSONException {
        mId = pJsonModule.getLong(ModuleContract.ID);
        mName = pJsonModule.getString(ModuleContract.NAME);
        mStatus = pJsonModule.getString(ModuleContract.STATUS);
        mValue = pJsonModule.getString(ModuleContract.VALUE);
        mStatusChangeDate = new Date(pJsonModule.getInt(ModuleContract.STATUS_CHANGE_DATE));
        mStandId = pJsonModule.getLong(ModuleContract.STAND_ID);
        return this;
    }

    public Module extractFromCursor(final Cursor pCursor) {
        mId = CursorUtil.getLong(pCursor, ModuleContract.ID);
        mName = CursorUtil.getString(pCursor, ModuleContract.NAME);
        mStatus = CursorUtil.getString(pCursor, ModuleContract.STATUS);
        mValue = CursorUtil.getString(pCursor, ModuleContract.VALUE);
        mStatusChangeDate = new Date(CursorUtil
                .getLong(pCursor, ModuleContract.STATUS_CHANGE_DATE));
        mStandId = CursorUtil.getLong(pCursor, ModuleContract.STAND_ID);
        return this;
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

    @Override
    public Module getNewEntity() {
        return new Module();
    }

    @Override
    public Long getId() {
        return mId;
    }

    public Module setId(final Long id) {
        mId = id;
        return this;
    }

    public String getName() {
        return mName;
    }

    public Module setName(final String name) {
        mName = name;
        return this;
    }

    public Long getStandId() {
        return mStandId;
    }

    public Module setStandId(final Long standId) {
        mStandId = standId;
        return this;
    }

    public String getStatus() {
        return mStatus;
    }

    public Module setStatus(final String status) {
        mStatus = status;
        return this;
    }

    public Date getStatusChangeDate() {
        return mStatusChangeDate;
    }

    public Module setStatusChangeDate(final Date statusChangeDate) {
        mStatusChangeDate = statusChangeDate;
        return this;
    }

    public String getValue() {
        return mValue;
    }

    public Module setValue(final String value) {
        mValue = value;
        return this;
    }

    @Override
    public int compareTo(@NonNull final Module pModule) {
        return mName.compareTo(pModule.getName());
    }
}
