package observer.zagart.by.client.http;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;

import observer.zagart.by.client.http.interfaces.IHttpClient;
import observer.zagart.by.client.singletons.ContextHolder;

public class HttpRequestTask extends AsyncTask<String, Void, String> {
    private IHttpClient.IRequest mRequest;
    private String mResult;

    public HttpRequestTask(String pUrl,
                           IHttpClient.IRequest pRequest) {
        mRequest = pRequest;
    }

    @Override
    @Nullable
    protected String doInBackground(String... urls) {
        try {
            return (new HttpClient()).executeRequest(mRequest);
        } catch (IOException pE) {
            pE.printStackTrace();
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
