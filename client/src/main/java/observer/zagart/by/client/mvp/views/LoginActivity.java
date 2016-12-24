package observer.zagart.by.client.mvp.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.constants.ApplicationConstants;
import observer.zagart.by.client.application.utils.AnimationUtil;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.presenters.AccountPresenter;
import observer.zagart.by.client.mvp.views.base.BaseAccountActivity;

/**
 * Activity for user authentication/registration with improved design.
 *
 * @author zagart
 */

public class LoginActivity
        extends BaseAccountActivity implements IMvp.IViewOperations<ObserverAccount> {

    private AccountPresenter mPresenter = new AccountPresenter(this);
    private TextInputEditText mLoginView;
    private TextInputEditText mPasswordView;
    private TextInputEditText mPasswordConfirmView;
    private View mAuthenticationButton;
    private View mRegistrationButton;
    private Drawable mShadowed;
    private Drawable mActive;

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        final ObserverAccount account = pAccounts.get(ObserverAccount.AUTHENTICATED_ACCOUNT_INDEX);
        if (account != null) {
            mPresenter.persistAccount(account);
            App.setAccount(account);
            final Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable final Bundle pBundle) {
        super.onCreate(pBundle);
        setContentView(R.layout.activity_login);
        mShadowed = ContextCompat.getDrawable(this, R.drawable.page_button_background_shadowed);
        mActive = ContextCompat.getDrawable(this, R.drawable.page_button_background_active);
        mLoginView = (TextInputEditText) findViewById(R.id.login_page_login);
        mPasswordView = (TextInputEditText) findViewById(R.id.login_page_password);
        mPasswordConfirmView = (TextInputEditText) findViewById(R.id.login_page_password_confirm);
        mAuthenticationButton = findViewById(R.id.button_login_page_authentication);
        mRegistrationButton = findViewById(R.id.button_login_page_registration);
        setUpListeners();
    }

    private void setUpListeners() {
        //TODO find cause of magical animation disappearing
        mAuthenticationButton.setOnClickListener((pEvent) -> onAuthenticationClick());
        mRegistrationButton.setOnClickListener((pEvent) -> onRegistrationClick());
        (findViewById(R.id.login_page_ok)).setOnClickListener((pEvent) -> onOkClick());
    }

    private void onRegistrationClick() {
        mAuthenticationButton.setBackground(mShadowed);
        mRegistrationButton.setBackground(mActive);
        mPasswordConfirmView.setVisibility(View.VISIBLE);
        AnimationUtil.makeIn(false, mPasswordConfirmView);
        clearTextFields();
    }

    private void onAuthenticationClick() {
        mRegistrationButton.setBackground(mShadowed);
        mAuthenticationButton.setBackground(mActive);
        AnimationUtil.makeOut(true, mPasswordConfirmView);
        mPasswordConfirmView.setVisibility(View.INVISIBLE);
        clearTextFields();
    }

    private void onOkClick() {
        final CharSequence login = mLoginView.getText();
        final CharSequence password = mPasswordView.getText();
        final CharSequence confirmedPassword = mPasswordConfirmView.getText();
        if (mPasswordConfirmView.getVisibility() != View.VISIBLE) {
            if (TextUtils.isEmpty(login) || TextUtils.isEmpty(password)) {
                IOUtil.showToast(this, getString(R.string.error_login_fields_empty));
            } else {
                mPresenter.executeAuthentication(login, password);
            }
        } else {
            if (TextUtils.isEmpty(login) ||
                    TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(confirmedPassword)) {
                IOUtil.showToast(this, getString(R.string.error_login_fields_empty));
            } else {
                if (password.toString().equals(confirmedPassword.toString())) {
                    mPresenter.executeRegistration(login, password);
                } else {
                    IOUtil.showToast(this, getString(R.string.error_different_passwords));
                }
            }
        }
    }

    private void clearTextFields() {
        mLoginView.setText(ApplicationConstants.EMPTY_STRING);
        mPasswordView.setText(ApplicationConstants.EMPTY_STRING);
        mPasswordConfirmView.setText(ApplicationConstants.EMPTY_STRING);
    }
}
