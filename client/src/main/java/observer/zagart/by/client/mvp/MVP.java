package observer.zagart.by.client.mvp;

import android.accounts.AccountAuthenticatorActivity;
import android.content.Context;

import java.util.List;

import observer.zagart.by.client.repository.entities.Module;
import observer.zagart.by.client.repository.entities.Stand;

/**
 * @author zagart
 */

public interface MVP {

    interface IViewOperations {

        Context getViewContext();
    }

    interface IPresenterOperations {

        List<Stand> getStandsFromModel();

        List<Module> getModulesFromModel();

        void downloadAllStands();

        void executeRegistration(
                final AccountAuthenticatorActivity pActivity,
                final CharSequence pLogin,
                final CharSequence pPassword
        );

        void executeAuthentication(
                AccountAuthenticatorActivity pActivity,
                CharSequence pLogin,
                CharSequence pPassword
        );

        void clearStandModel();

        void clearModuleModel();
    }

    interface IModelOperations<Entity> {

        List<Entity> retrieveAll();

        void persistAll(List<Entity> pEntities);

        void deleteAll();
    }
}
