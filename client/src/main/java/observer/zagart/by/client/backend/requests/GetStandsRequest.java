package observer.zagart.by.client.backend.requests;

import java.net.HttpURLConnection;

import observer.zagart.by.client.backend.Criteria;
import observer.zagart.by.client.http.interfaces.IHttpClient;

/**
 * AbstractObserverRequest implementation that is responsible for getting
 * all stands data that satisfy criteria from HTTP-server ObserverApi.
 *
 * @author zagart
 */
public class GetStandsRequest extends AbstractObserverRequest {

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
                Criteria.getStandsCriteria());
    }
}
