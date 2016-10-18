package by.grodno.zagart.observer.observerandroid.http;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Properties;

import by.grodno.zagart.observer.observerandroid.singletons.ContextHolder;

public class HttpRequestTask extends AsyncTask<String, Void, String> {
    private String mUrl;
    private IHttpClient.IRequest.Method mMethod;
    private Properties mProperties;
    private String mResult;

    public HttpRequestTask(String pUrl,
                           IHttpClient.IRequest.Method pMethod,
                           Properties pProperties) {
        mUrl = pUrl;
        mMethod = pMethod;
        mProperties = pProperties;
    }

    @Override
    @Nullable
    protected String doInBackground(String... urls) {
        final IHttpClient.IRequest request = new IHttpClient.IRequest() {
            @Override
            public Properties getProperties() {
                return mProperties;
            }

            @Override
            public String getUrl() {
                return mUrl;
            }
        };
        if (mMethod == IHttpClient.IRequest.Method.GET) {
            return HttpClientFactory.getDefaultClient().get(request);
        } else if (mMethod == IHttpClient.IRequest.Method.POST) {
            return HttpClientFactory.getDefaultClient().post(request);
        }
        return null;
    }

    @Override
    protected void onPostExecute(final String pResponse) {
        super.onPostExecute(pResponse);
        Toast.makeText(ContextHolder.get(), pResponse, Toast.LENGTH_SHORT).show();
        mResult = pResponse;
    }

    @Nullable
    public String getResult() {
        return mResult;
    }
}
