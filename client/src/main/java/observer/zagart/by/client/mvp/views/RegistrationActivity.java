package observer.zagart.by.client.mvp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.presenters.AccountPresenter;
import observer.zagart.by.client.mvp.views.base.BaseAccountActivity;

/**
 * Activity with UI for executing process of registration
 * for users.
 *
 * @author zagart
 */
public class RegistrationActivity
        extends BaseAccountActivity implements IMvp.IViewOperations<ObserverAccount> {

    private AccountPresenter mPresenter = new AccountPresenter(this);
    private EditText mPasswordView;
    private EditText mLoginView;

    public void onConfirmClick(View pView) {
        final CharSequence charLogin = mLoginView.getText();
        final CharSequence charPassword = mPasswordView.getText();
        if (TextUtils.isEmpty(charLogin) || TextUtils.isEmpty(charPassword)) {
            IOUtil.showToast(this, getString(R.string.error_login_fields_empty));
        } else {
            mPresenter.executeRegistration(charLogin, charPassword);
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pParameters) {
        final ObserverAccount account = pParameters
                .get(ObserverAccount.AUTHENTICATED_ACCOUNT_INDEX);
        mPresenter.persistAccount(account);
        App.setAccount(account);
        final Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mLoginView = (EditText) findViewById(R.id.registration_login);
        mPasswordView = (EditText) findViewById(R.id.registration_password);
    }
}
