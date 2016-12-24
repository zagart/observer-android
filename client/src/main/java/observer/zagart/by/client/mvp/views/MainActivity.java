package observer.zagart.by.client.mvp.views;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import observer.zagart.by.client.App;
import observer.zagart.by.client.R;
import observer.zagart.by.client.application.accounts.ObserverAccount;
import observer.zagart.by.client.application.constants.NetworkConstants;
import observer.zagart.by.client.application.utils.AnimationUtil;
import observer.zagart.by.client.application.utils.IOUtil;
import observer.zagart.by.client.mvp.IMvp;
import observer.zagart.by.client.mvp.views.base.BaseNavigationActivity;

/**
 * Application main activity.
 */
public class MainActivity
        extends BaseNavigationActivity implements IMvp.IViewOperations<ObserverAccount> {

    private final static int SELECT_PICTURE = 1;
    private Button mLogInButton;
    private Button mLogOutButton;
    private TextView mUserLabel;
    private TextView mUserLogin;

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onDataChanged(final List<ObserverAccount> pAccounts) {
        onAccountChanged();
    }

    public void onMenuItemClick(final MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.menu_item_my_account:
                closeDrawers();
                break;
            case R.id.menu_item_content:
                startActivity(new Intent(this, ContentActivity.class));
                break;
            case R.id.menu_item_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        closeDrawers();
    }

    public void onLogInClick(final View pView) {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void onAccountChanged() {
        super.onAccountChanged();
        final Account account = App.getAccount();
        if (account != null) {
            mUserLogin.setText(account.name);
            mLogInButton.setVisibility(View.GONE);
            mLogOutButton.setVisibility(View.VISIBLE);
            mUserLabel.setVisibility(View.VISIBLE);
            mUserLogin.setVisibility(View.VISIBLE);
        } else {
            mUserLogin.setText(getString(R.string.user_login_default));
            AnimationUtil.fadeOut(mLogOutButton);
            mLogOutButton.setVisibility(View.GONE);
            AnimationUtil.makeOut(false, mUserLabel);
            mUserLabel.setVisibility(View.INVISIBLE);
            AnimationUtil.makeOut(true, mUserLogin);
            mUserLogin.setVisibility(View.INVISIBLE);
            mLogInButton.setVisibility(View.VISIBLE);
        }
    }

    public void onLogOutClick(final View pView) {
        App.setAccount(null);
        onAccountChanged();
    }

    public void onUploadClick(final View pView) throws IOException {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @SuppressWarnings("WrongConstant")
    @Override
    protected void onActivityResult(final int pRequestCode,
                                    final int pResultCode,
                                    final Intent pData) {
        super.onActivityResult(pRequestCode, pResultCode, pData);
        if (pRequestCode == SELECT_PICTURE) {
            if (pResultCode == RESULT_OK) {
                Uri selectedImageUri = pData.getData();
                Log.i("Uploading from URI: ", selectedImageUri.getPath());
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(
                        selectedImageUri,
                        filePathColumn,
                        null,
                        null,
                        null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                if (filePath != null) {
                    Log.i("Uploading file: %s", filePath);
                    startUpload(filePath);
                    cursor.close();
                } else {
                    IOUtil.showToast("Failed to upload");
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        onAccountChanged();
        setTitle(R.string.my_account);
    }

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_main);
        addToolbar();
        addNavigationMenu();
        mLogInButton = (Button) findViewById(R.id.button_log_in);
        mLogOutButton = (Button) findViewById(R.id.button_log_out);
        mUserLabel = (TextView) findViewById(R.id.textview_my_account_user_label);
        mUserLogin = (TextView) findViewById(R.id.textview_my_account_user_login);
    }

    private void startUpload(String filePath) {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {

            protected String doInBackground(String... paths) {
                Log.d("Running upload task", "true");
                // Upload to cloudinary
                Cloudinary cloudinary = new Cloudinary();
                File file = new File(paths[0]);
                @SuppressWarnings("rawtypes")
                Map cloudinaryResult;
                try {
                    // Cloudinary: Upload file using the retrieved signature and upload params
                    cloudinaryResult = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                            NetworkConstants.API_KEY, "127689128674168",
                            NetworkConstants.API_SECRET, "hUxY-MQtt632x6oNAoLgS-ZHrzo",
                            NetworkConstants.UPLOAD_PRESET, NetworkConstants.PRESET_KEY,
                            NetworkConstants.CLOUD_NAME, "observerapp"
                    ));
                    IOUtil.showToast("Uploaded... Maybe");
                    Log.i("Uploaded file: %s", cloudinaryResult.toString());
                    return "result ok";
                } catch (RuntimeException e) {
                    Log.e("Error uploading file", e.getMessage());
                    return "Error uploading file: " + e.toString();
                } catch (IOException e) {
                    Log.e("Error uploading file", e.getMessage());
                    return "Error uploading file: " + e.toString();
                }
            }
        };
        task.execute(filePath);
    }
}
