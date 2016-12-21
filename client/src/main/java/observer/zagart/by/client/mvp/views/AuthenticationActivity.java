package observer.zagart.by.client.mvp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.presenters.AccountPresenter;

/**
 * Activity that provides UI for user authorization.
 */
public class AuthenticationActivity
        extends AppCompatActivity implements IMvp.IViewOperations<ObserverAccount> {

    private AccountPresenter mPresenter = new AccountPresenter(this);
    private TextView mLoginView;
    private TextView mPasswordView;

    public void onGuestClick(final View pView) {
        final ArrayList<ObserverAccount> accounts = new ArrayList<>();
        final ObserverAccount guestAccount = ObserverAccount.getGuestAccount();
        accounts.add(guestAccount);
        onDataChanged(accounts);
    }

    public void onLogInClick(final View pView) {
        final CharSequence charLogin = mLoginView.getText();
        final CharSequence charPassword = mPasswordView.getText();
        if (TextUtils.isEmpty(charLogin) || TextUtils.isEmpty(charPassword)) {
            IOUtil.showToast(this, getString(R.string.error_login_fields_empty));
        } else {
            mPresenter.executeAuthentication(charLogin, charPassword);
        }
    }

    public void onSignUpClick(final View pView) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        final ObserverAccount account = pAccounts.get(ObserverAccount.AUTHENTICATED_ACCOUNT_INDEX);
        mPresenter.persistAccount(account);
        App.setAccount(account);
        final Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        mLoginView = (TextView) findViewById(R.id.login_login);
        mPasswordView = (TextView) findViewById(R.id.login_password);
    }
}
