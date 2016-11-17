package observer.zagart.by.client.utils;

import android.net.Uri;

import java.util.Locale;

import observer.zagart.by.client.constants.Constants;

/**
 * @author zagart
 */

public class URIUtil {

    private static final String URI_FORMAT = "%s://%s/%s/%s";

    public static Uri addId(Uri pUri, long pId) {
        final String stringUri = uriBuilder(
                pUri.getAuthority(),
                getClearUriPath(pUri),
                String.valueOf(pId)
        );
        return Uri.parse(stringUri);
    }

    public static String getClearUriPath(Uri pUri) {
        String path = pUri.getPath();
        return path.replace(Constants.URI_SEPARATOR, Constants.EMPTY_STRING);
    }

    public static Uri getModuleUri() {
        final String stringUri = uriBuilder(
                Constants.AUTHORITY,
                Constants.MODULE,
                Constants.EMPTY_STRING
        );
        return Uri.parse(stringUri);
    }

    public static Uri getStandUri() {
        final String stringUri = uriBuilder(
                Constants.AUTHORITY,
                Constants.STAND,
                Constants.EMPTY_STRING
        );
        return Uri.parse(stringUri);
    }

    private static String uriBuilder(String pAuthority, String pPath, String pId) {
        return String.format(
                Locale.getDefault(),
                URI_FORMAT,
                Constants.CONTENT,
                pAuthority,
                pPath,
                pId
        );
    }
}
