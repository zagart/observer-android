package observer.zagart.by.client.mvp.views;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import observer.zagart.by.client.R;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.presenters.AccountPresenter;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.base.BaseView;

/**
 * Activity that provides UI for user authorization.
 */
public class AuthenticationActivity extends BaseView {

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
            mPresenter.executeAuthentication(charLogin, charPassword);
        }
    }

    public void onSignUpClick(View pView) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void onDataChanged(final Bundle pParameters) {
        mPresenter.persistAccount(pParameters);
        final Intent intent = new Intent(this, MyAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar bar = getActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.activity_authentication);
        mLoginView = (TextView) findViewById(R.id.login_login);
        mPasswordView = (TextView) findViewById(R.id.login_password);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }
}
