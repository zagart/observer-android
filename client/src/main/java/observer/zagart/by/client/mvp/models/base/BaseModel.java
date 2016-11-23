package observer.zagart.by.client.mvp.models.base;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.application.utils.ReflectionUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.repository.entities.IEntity;

/**
 * Implementation of base model. Uses reflection to get single object
 * of entity and afterwards uses it for universalization common features
 * of model.
 *
 * @author zagart
 */
public abstract class BaseModel<Entity extends IEntity<Entity, ContentValues, Long>>
        implements IMvp.IModelOperations<Entity> {

    final private static int PARAMETER_POSITION = 0; //Position of Entity in parameter

    final private Entity mEmptyEntity;
    final private Uri mEntityTableUri;

    protected BaseModel(final Uri pEntityTableUri, final boolean pUseReflection) {
        mEntityTableUri = pEntityTableUri;
        if (pUseReflection) {
            mEmptyEntity = ReflectionUtil.getGenericObject(this, PARAMETER_POSITION);
        } else {
            mEmptyEntity = null;
        }
    }

    public Uri getUri() {
        return mEntityTableUri;
    }

    public void deleteAll() {
        final ContentResolver resolver = App.getContext().getContentResolver();
        resolver.delete(mEntityTableUri, null, null);
        Log.i(BaseModel.class.getSimpleName(), mEntityTableUri.toString());
        notifyChange();
    }

    protected List<Entity> retrieveAll(final String pEntitiesSelection) {
        final ContentResolver resolver = App.getContext().getContentResolver();
        final List<Entity> entities = new ArrayList<>();
        final Cursor cursor = resolver.query(
                mEntityTableUri,
                null,
                pEntitiesSelection,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                entities.add(mEmptyEntity.getNewEntity().extractFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        IOUtil.close(cursor);
        return entities;
    }

    protected void persistAll(final List<Entity> pEntities, final String pSelectionById) {
        //TODO correct bulk insert
        for (Entity entity : pEntities) {
            persist(entity, pSelectionById);
        }
    }

    protected Long persist(final Entity pEntity, final String pSelectionById) {
        if (!isEntityCached(pEntity, pSelectionById)) {
            final ContentResolver resolver = App.getContext().getContentResolver();
            final Uri entityUri = resolver.insert(mEntityTableUri, pEntity.convert());
            if (entityUri != null) {
                Log.i(BaseModel.class.getSimpleName(), entityUri.toString());
                notifyChange();
                return ContentUris.parseId(entityUri);
            }
        }
        return null;
    }

    protected void notifyChange() {
        Log.i("Notify", mEntityTableUri.toString());
        App.getContext().getContentResolver().notifyChange(mEntityTableUri, null);
    }

    private boolean isEntityCached(final Entity pEntity, final String pEntitySelection) {
        final ContentResolver resolver = App.getContext().getContentResolver();
        final String[] args = {pEntity.getId().toString()};
        final Cursor cursor = resolver.query(
                mEntityTableUri,
                null,
                pEntitySelection,
                args,
                null);
        if (cursor == null) {
            return false;
        } else {
            final boolean cached = cursor.getCount() > 0;
            cursor.close();
            return cached;
        }
    }
}
