package observer.zagart.by.client.network.http.requests.observer;

import java.net.HttpURLConnection;

import observer.zagart.by.client.network.Criteria;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;
import observer.zagart.by.client.network.http.requests.observer.base.BaseObserverRequest;

/**
 * BaseObserverRequest implementation that is responsible for getting
 * all modules data (that satisfy criteria) from HTTP-server Observer.
 *
 * @author zagart
 */
public class GetModulesRequest extends BaseObserverRequest {

    @Override
    public IHttpClient.Method getMethodType() {
        return IHttpClient.Method.GET;
    }

    @Override
    public void handleRequestConnection(final HttpURLConnection pConnection) {
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.ACTION,
                IHttpClient.IHttpData.Actions.GET_DATA);
        pConnection.addRequestProperty(
                IHttpClient.IHttpData.Header.CRITERIA,
                Criteria.getModulesCriteria());
    }

    @Override
    public void onTimeoutException(final String... pParameters) {
    }

    @Override
    public void onIOException(final String... pParameters) {
    }
}
