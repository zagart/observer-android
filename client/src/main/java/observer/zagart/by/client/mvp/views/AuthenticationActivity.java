package observer.zagart.by.client.mvp.views;

import android.accounts.AccountAuthenticatorActivity;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import observer.zagart.by.client.R;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.presenters.AccountPresenter;
import observer.zagart.by.client.application.utils.IOUtil;

/**
 * Activity that provides UI for user authorization.
 */
public class AuthenticationActivity
        extends AccountAuthenticatorActivity implements IMvp.IViewOperations {

    private AccountPresenter mPresenter = new AccountPresenter(this);
    private TextView mLoginView;
    private TextView mPasswordView;

    public void onGuestClick(View view) {
        //TODO guest log in
    }

    public void onLogInClick(View view) {
        final CharSequence charLogin = mLoginView.getText();
        final CharSequence charPassword = mPasswordView.getText();
        if (TextUtils.isEmpty(charLogin) || TextUtils.isEmpty(charPassword)) {
            IOUtil.showToast(this, getString(R.string.error_login_fields_empty));
        } else {
            mPresenter.executeAuthentication(this, charLogin, charPassword);
        }
    }

    public void onSignUpClick(View pView) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onViewsVisibilityCheck() {
    }

    @Override
    public void onDataChanged() {
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.login_activity);
        mLoginView = (TextView) findViewById(R.id.login_login);
        mPasswordView = (TextView) findViewById(R.id.login_password);
    }
}
