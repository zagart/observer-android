package observer.zagart.by.client.network.http.requests;

import java.net.HttpURLConnection;

import observer.zagart.by.client.network.api.Criteria;
import observer.zagart.by.client.network.http.interfaces.IHttpClient;

/**
 * AbstractObserverRequest implementation that is responsible for getting
 * all modules data that satisfy criteria from HTTP-server Observer.
 *
 * @author zagart
 */
public class GetModulesRequest extends AbstractObserverRequest {

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
}