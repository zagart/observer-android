package observer.zagart.by.client.mvp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import java.util.List;

import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * MVP pattern interface hierarchy for Observer project.
 *
 * @author zagart
 */

public interface IMvp {

    interface IViewOperations {

        Context getViewContext();

        void onDataChanged(final Bundle pParameters);
    }

    interface IPresenterOperations<Entity> {

        Uri getEntityUri();

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
