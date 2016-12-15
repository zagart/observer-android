package observer.zagart.by.client.mvp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.utils.AccountUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.presenters.base.BasePresenter;
import observer.zagart.by.client.mvp.views.AuthenticationActivity;
import observer.zagart.by.client.mvp.views.MainActivity;
import observer.zagart.by.client.mvp.views.base.BaseView;

import static observer.zagart.by.client.application.constants.ApplicationConstants.EMPTY_STRING;

/**
 * Fragment for user account.
 *
 * @author zagart
 */
public class MyAccountFragment extends BaseView implements IMvp.IViewOperations<ObserverAccount> {

    private Button mLogInView;
    private Button mLogOutView;
    private TextView mUserLabel;
    private TextView mUserLogin;

    public void onLogInClick() {
        Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void onLogoutClick() {
        AccountUtil.setCurrentAccount(null);
        onAccountCheck();
    }

    @Override
    public void onAccountCheck() {
        final MainActivity activity = (MainActivity) getActivity();
        if (App.getAccount() != null) {
            activity.updateMenuItemsVisibility(null, true);
            mLogInView.setVisibility(View.GONE);
            mLogOutView.setVisibility(View.VISIBLE);
            mUserLabel.setVisibility(View.VISIBLE);
            mUserLogin.setVisibility(View.VISIBLE);
            mUserLogin.setText(App.getAccount().name);
        } else {
            activity.updateMenuItemsVisibility(null, false);
            mLogInView.setVisibility(View.VISIBLE);
            mLogOutView.setVisibility(View.GONE);
            mUserLabel.setVisibility(View.GONE);
            mUserLogin.setVisibility(View.GONE);
            mUserLogin.setText(EMPTY_STRING);
        }
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        onAccountCheck();
    }

    @Override
    public void onStart() {
        super.onStart();
        onAccountCheck();
        getActivity().setTitle(R.string.my_account);
    }

    @Override
    public void onActivityCreated(final Bundle pSavedInstanceState) {
        super.onActivityCreated(pSavedInstanceState);
        mLogInView = (Button) getActivity().findViewById(R.id.my_account_btn_log_in);
        mLogInView.setOnClickListener(pView -> onLogInClick());
        mLogOutView = (Button) getActivity().findViewById(R.id.my_account_btn_log_out);
        mLogOutView.setOnClickListener(pView -> onLogoutClick());
        mUserLabel = (TextView) getActivity().findViewById(R.id.my_account_login_label);
        mUserLogin = (TextView) getActivity().findViewById(R.id.my_account_login);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater pInflater,
                             final ViewGroup pContainer,
                             final Bundle pSavedInstanceState) {
        super.onCreateView(pInflater, pContainer, pSavedInstanceState);
        return pInflater.inflate(R.layout.activity_my_account, pContainer, false);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
}
