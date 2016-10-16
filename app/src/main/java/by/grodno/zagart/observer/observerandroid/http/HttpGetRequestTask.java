package by.grodno.zagart.observer.observerandroid.http;
import android.os.AsyncTask;
import android.widget.Toast;

import by.grodno.zagart.observer.observerandroid.singleton.ContextHolder;

public class HttpGetRequestTask extends AsyncTask<String, Void, String> {
    private String mUrl;

    public HttpGetRequestTask(String pUrl) {
        mUrl = pUrl;
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = HttpClientFactory.getDefaultClient().get(
                new IHttpClient.IRequest() {
                    @Override
                    public String getUrl() {
                        return mUrl;
                    }
                }
        );
        return response;
    }

    @Override
    protected void onPostExecute(final String pResponse) {
        super.onPostExecute(pResponse);
        Toast.makeText(ContextHolder.get(), pResponse, Toast.LENGTH_SHORT).show();
    }
}
