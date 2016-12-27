package observer.zagart.by.client.mvp.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
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
    private AppCompatButton mAuthenticationButton;
    private AppCompatButton mRegistrationButton;
    private AppCompatButton mOkButton;
    private Drawable mShadowed;
    private Drawable mActive;

    @Override
    public void showSnackBar(final int pResID) {
        super.showSnackBar(pResID);
    }

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
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable final Bundle pBundle) {
        super.onCreate(pBundle);
        setContentView(R.layout.activity_login);
        findCoordinatorLayout();
        mShadowed = ContextCompat.getDrawable(this, R.drawable.page_button_background_shadowed);
        mActive = ContextCompat.getDrawable(this, R.drawable.page_button_background_active);
        mLoginView = (TextInputEditText) findViewById(R.id.login_page_login);
        mPasswordView = (TextInputEditText) findViewById(R.id.login_page_password);
        mPasswordConfirmView = (TextInputEditText) findViewById(R.id
                .login_page_password_confirm);
        mAuthenticationButton = (AppCompatButton)
                findViewById(R.id.button_login_page_authentication);
        mRegistrationButton = (AppCompatButton) findViewById(R.id
                .button_login_page_registration);
        mOkButton = (AppCompatButton) findViewById(R.id.login_page_ok);
        setUpListeners();
    }

    private void setUpListeners() {
        //TODO find cause of magical animation disappearing
        mAuthenticationButton.setOnClickListener((pEvent) -> onAuthenticationClick());
        mRegistrationButton.setOnClickListener((pEvent) -> onRegistrationClick());
        mOkButton.setOnClickListener((pEvent) -> onOkClick());
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
        handleEnteredCredentials(
                mLoginView.getText(),
                mPasswordView.getText(),
                mPasswordConfirmView.getText());
    }

    private void handleEnteredCredentials(final CharSequence pLogin,
                                          final CharSequence pPassword,
                                          final CharSequence pConfirmedPassword) {
        if (mPasswordConfirmView.getVisibility() != View.VISIBLE) {
            if (TextUtils.isEmpty(pLogin) || TextUtils.isEmpty(pPassword)) {
                IOUtil.showToast(this, getString(R.string.error_login_fields_empty));
            } else {
                mPresenter.executeAuthentication(pLogin, pPassword);
            }
        } else {
            if (TextUtils.isEmpty(pLogin) ||
                    TextUtils.isEmpty(pPassword) ||
                    TextUtils.isEmpty(pConfirmedPassword)) {
                IOUtil.showToast(this, getString(R.string.error_login_fields_empty));
            } else {
                if (pPassword.toString().equals(pConfirmedPassword.toString())) {
                    mPresenter.executeRegistration(pLogin, pPassword);
                } else {
                    IOUtil.showToast(this, getString(R.string.error_different_passwords));
                }
            }
        }
    }

    private void clearTextFields() {
        mLoginView.requestFocus();
        mLoginView.setText(ApplicationConstants.EMPTY_STRING);
        mPasswordView.setText(ApplicationConstants.EMPTY_STRING);
        mPasswordConfirmView.setText(ApplicationConstants.EMPTY_STRING);
    }
}
