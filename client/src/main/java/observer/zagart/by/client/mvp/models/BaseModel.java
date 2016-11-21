package observer.zagart.by.client.mvp.models;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.utils.ReflectionUtil;
import observer.zagart.by.client.mvp.models.repository.entities.IEntity;

/**
 * Implementation of base model. Uses reflection to get single object
 * of entity and afterwards uses it for universalization common features
 * of model.
 *
 * @author zagart
 */
abstract class BaseModel<Entity extends IEntity<Entity, ContentValues, Long>> {

    final private Entity mEmptyEntity = ReflectionUtil.getGenericObject(this, 0);
    final private Uri mEntityTableUri;

    BaseModel(final Uri pEntityTableUri) {
        mEntityTableUri = pEntityTableUri;
    }

    List<Entity> retrieveAll(final String pEntitiesSelection) {
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
            cursor.close();
        }
        return entities;
    }

    void persistAll(final List<Entity> pEntities, final String pSelectionById) {
        for (Entity entity : pEntities) {
            if (!isEntityCached(entity, pSelectionById)) {
                final ContentResolver resolver = App.getContext().getContentResolver();
                resolver.insert(mEntityTableUri, entity.convert());
            }
        }
    }

    void deleteAll() {
        final ContentResolver resolver = App.getContext().getContentResolver();
        resolver.delete(mEntityTableUri, null, null);
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