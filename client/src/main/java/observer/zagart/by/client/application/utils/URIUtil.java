package observer.zagart.by.client.application.utils;

import android.net.Uri;

import java.util.Locale;

import observer.zagart.by.client.application.constants.ApplicationConstants;
import observer.zagart.by.client.application.constants.URIConstants;

/**
 * Utility class with methods to work with URI.
 *
 * @author zagart
 */

public class URIUtil {


    public static String getClearUriPath(final Uri pUri) {
        String path = pUri.getPath();
        return path.replace(URIConstants.URI_SEPARATOR, ApplicationConstants.EMPTY_STRING);
    }

    public static Uri getModuleUri() {
        final String stringUri = uriBuilder(
                URIConstants.AUTHORITY,
                URIConstants.MODULE,
                ApplicationConstants.EMPTY_STRING);
        return Uri.parse(stringUri);
    }

    public static Uri getAccountUri() {
        final String stringUri = uriBuilder(
                URIConstants.AUTHORITY,
                URIConstants.ACCOUNT,
                ApplicationConstants.EMPTY_STRING);
        return Uri.parse(stringUri);
    }

    public static Uri getStandUri() {
        final String stringUri = uriBuilder(
                URIConstants.AUTHORITY,
                URIConstants.STAND,
                ApplicationConstants.EMPTY_STRING);
        return Uri.parse(stringUri);
    }

    private static String uriBuilder(final String pAuthority,
                                     final String pPath,
                                     final String pId) {
        return String.format(
                Locale.getDefault(),
                URIConstants.URI_FORMAT,
                URIConstants.CONTENT,
                pAuthority,
                pPath,
                pId);
    }
}
