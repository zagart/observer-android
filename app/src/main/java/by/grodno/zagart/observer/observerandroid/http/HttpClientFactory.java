package by.grodno.zagart.observer.observerandroid.http;
import android.support.annotation.Nullable;

import by.grodno.zagart.observer.observerandroid.http.interfaces.IHttpClient;

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

    private enum Type {
        HTTP_PURE
    }
}
