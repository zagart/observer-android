package observer.zagart.by.client.mvp;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import observer.zagart.by.client.http.interfaces.IHttpClient;

/**
 * @author zagart
 */

public interface MVP {

    interface IViewOperations {

        Context getViewContext();

        void onViewsVisibilityCheck();

        void onDataChanged();
    }

    interface IPresenterOperations<Entity> {

        void onCreate(final IModelOperations<Entity> pModel);

        List<Entity> getElementsFromModel(final Uri pUri);

        void synchronizeModel(final Uri pUri, final IHttpClient.IRequest<String> pRequest);

        void clearModel(final Uri pUri);
    }

    interface IModelOperations<Entity> {

        List<Entity> retrieveAll();

        void persistAll(final List<Entity> pEntities);

        void deleteAll();
    }
}
