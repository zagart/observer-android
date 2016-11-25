package observer.zagart.by.client.mvp;

import android.content.Context;

import java.util.List;

import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * MVP pattern interface hierarchy for Observer project.
 *
 * @author zagart
 */

public interface IMvp {

    interface IViewOperations<Param> {

        Context getViewContext();

        void onDataChanged(final Param pParameters);
    }

    interface IPresenterOperations<Entity> {

        List<Entity> getElementsFromModel();

        void startDataReload();

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
