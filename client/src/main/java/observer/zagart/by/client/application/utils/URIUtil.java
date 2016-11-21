package observer.zagart.by.client.application.utils;

import android.net.Uri;

import java.util.Locale;

import observer.zagart.by.client.application.constants.Constants;

/**
 * @author zagart
 */

public class URIUtil {

    private static final String URI_FORMAT = "%s://%s/%s/%s";

    public static String getClearUriPath(final Uri pUri) {
        String path = pUri.getPath();
        return path.replace(Constants.URI_SEPARATOR, Constants.EMPTY_STRING);
    }

    public static Uri getModuleUri() {
        final String stringUri = uriBuilder(
                Constants.AUTHORITY,
                Constants.MODULE,
                Constants.EMPTY_STRING);
        return Uri.parse(stringUri);
    }

    public static Uri getStandUri() {
        final String stringUri = uriBuilder(
                Constants.AUTHORITY,
                Constants.STAND,
                Constants.EMPTY_STRING);
        return Uri.parse(stringUri);
    }

    private static String uriBuilder(final String pAuthority,
                                     final String pPath,
                                     final String pId) {
        return String.format(
                Locale.getDefault(),
                URI_FORMAT,
                Constants.CONTENT,
                pAuthority,
                pPath,
                pId);
    }
}
