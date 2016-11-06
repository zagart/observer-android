package observer.zagart.by.client.http;
import android.support.annotation.Nullable;

import observer.zagart.by.client.http.interfaces.IHttpClient;

/**
 * Factory of HTTP-clients.
 */
public class HttpClientFactory {
    @Nullable
    public static IHttpClient get(final Type pType) {
        if (pType == Type.HTTP_PURE) {
            return new HttpClient();
        } else {
            return null;
        }
    }

    public static IHttpClient getDefaultClient() {
        return get(Type.HTTP_PURE);
    }

    public enum Type {
        HTTP_PURE
    }
}
