package observer.zagart.by.client.mvp.models.repository.entities;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import observer.zagart.by.client.application.interfaces.IConvertible;
import observer.zagart.by.client.mvp.models.repository.contracts.ModuleContract;
import observer.zagart.by.client.application.utils.CursorUtil;

/**
 * Model for module.
 */
public class Module implements IConvertible<ContentValues> {

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
