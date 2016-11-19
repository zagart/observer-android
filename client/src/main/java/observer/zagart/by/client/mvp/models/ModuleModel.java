package observer.zagart.by.client.mvp.models;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.application.constants.Constants;
import observer.zagart.by.client.application.managers.DatabaseManager;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.models.repository.entities.Module;

/**
 * IMvp model implementation for stand.
 *
 * @author zagart
 */

public class ModuleModel implements IMvp.IModelOperations<Module> {

    private IMvp.IPresenterOperations mPresenter;
    private DatabaseManager mHelper;

    public ModuleModel(final IMvp.IPresenterOperations pPresenter) {
        mPresenter = pPresenter;
        mHelper = App.getState().getDatabaseManager();
    }

    @Override
    public List<Module> retrieveAll() {
        List<Module> modules = new ArrayList<>();
        Cursor cursor = mHelper.query(Constants.SELECT_ALL_MODULES);
        if (cursor.moveToFirst()) {
            while (!cursor.isLast()) {
                modules.add(new Module().extractFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return modules;
    }

    @Override
    public void persistAll(final List<Module> pStands) {
        //TODO persisting modules
    }

    @Override
    public void deleteAll() {
        //TODO delete all modules
    }
}
