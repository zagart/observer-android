package by.zagart.observer.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Utility class for HTTP-layer.
 */
public class HttpUtil {
    public static void printRequestInfo(HttpServletRequest pRequest) {
        final Enumeration<String> headerNames = pRequest.getHeaderNames();
        final String method = pRequest.getMethod();
        final String requestUrl = pRequest.getRequestURL().toString();
        final Enumeration<String> attributeNames = pRequest.getAttributeNames();
        final String contentType = pRequest.getContentType();
        final Enumeration<String> parameterNames = pRequest.getParameterNames();
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(Locale.getDefault(), "Request method: %s\n", method));
        builder.append(String.format(Locale.getDefault(), "Request URL: %s\n", requestUrl));
        builder.append(String.format(Locale.getDefault(), "Content type: %s\n", contentType));
        iterateToBuilder(builder, "Header: %s\n", headerNames);
        iterateToBuilder(builder, "Attribute: %s\n", attributeNames);
        iterateToBuilder(builder, "Parameter: %s\n", parameterNames);
        System.out.println(builder.toString());
    }

    private static void iterateToBuilder(StringBuilder pBuilder, String pFormat, Enumeration pEnumeration) {
        while (pEnumeration.hasMoreElements()) {
            pBuilder.append(String.format(Locale.getDefault(), pFormat, pEnumeration.nextElement()));
        }
    }
}
