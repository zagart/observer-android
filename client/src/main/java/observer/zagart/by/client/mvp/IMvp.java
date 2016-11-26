package observer.zagart.by.client.mvp;

import android.content.Context;

import java.util.List;

import observer.zagart.by.client.mvp.models.repository.entities.IEntity;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * MVP pattern interface hierarchy for Observer project.
 *
 * @author zagart
 */

public interface IMvp {

    interface IViewOperations<Entity extends IEntity> {

        Context getViewContext();

        void onDataChanged(final List<Entity> pParameters);
    }

    interface IPresenterOperations<Entity extends IEntity> {

        List<Entity> getElementsFromModel();

        void synchronizeModel(final IHttpClient.IRequest<String> pRequest);

        void clearModel();
    }

    interface IModelOperations<Entity> {

        Long persist(final Entity pEntity);

        List<Entity> retrieveAll();

        void persistAll(final List<Entity> pEntities);

        void deleteAll();
    }
}
