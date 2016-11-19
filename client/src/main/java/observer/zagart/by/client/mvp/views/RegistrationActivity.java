package observer.zagart.by.client.mvp.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import observer.zagart.by.client.R;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.presenters.AccountPresenter;
import observer.zagart.by.client.mvp.views.base.BaseAuthenticatorActivity;

/**
 * Activity with UI for executing process of registration
 * for users.
 *
 * @author zagart
 */
public class RegistrationActivity extends BaseAuthenticatorActivity {

    private AccountPresenter mPresenter = new AccountPresenter(this);
    private EditText mPasswordView;
    private EditText mLoginView;

    public void onConfirmClick(View pView) {
        final CharSequence charLogin = mLoginView.getText();
        final CharSequence charPassword = mPasswordView.getText();
        if (TextUtils.isEmpty(charLogin) || TextUtils.isEmpty(charPassword)) {
            IOUtil.showToast(this, getString(R.string.error_login_fields_empty));
        } else {
            mPresenter.executeRegistration(this, charLogin, charPassword);
        }
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mLoginView = (EditText) findViewById(R.id.registration_login);
        mPasswordView = (EditText) findViewById(R.id.registration_password);
    }
}
