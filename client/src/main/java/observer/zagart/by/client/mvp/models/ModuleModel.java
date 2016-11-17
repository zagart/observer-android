package observer.zagart.by.client.mvp.models;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.constants.Constants;
import observer.zagart.by.client.mvp.MVP;
import observer.zagart.by.client.repository.entities.Module;
import observer.zagart.by.client.repository.helper.DbHelper;

/**
 * MVP model implementation for stand.
 *
 * @author zagart
 */

public class ModuleModel implements MVP.IModelOperations<Module> {

    private MVP.IPresenterOperations mPresenter;
    private DbHelper mHelper;

    public ModuleModel(final MVP.IPresenterOperations pPresenter) {
        mPresenter = pPresenter;
        mHelper = App.getState().getDbHelper();
    }

    @Override
    public List<Module> retrieveAll() {
        List<Module> modules = new ArrayList<>();
        Cursor cursor = mHelper.query(Constants.SELECT_ALL_MODULES);
        if (cursor.moveToFirst()) {
            while (!cursor.isLast()) {
                modules.add(Module.parseCursorRow(cursor));
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
