package by.zagart.observer.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Locale;

import static by.zagart.observer.utils.HttpUtil.HttpRequestContentType.*;

/**
 * Utility class for HTTP-layer.
 */
public class HttpUtil {
    public static String getRequestInfo(HttpServletRequest pRequest) {
        final Enumeration<String> headerNames = pRequest.getHeaderNames();
        final String method = pRequest.getMethod();
        final String requestUrl = pRequest.getRequestURL().toString();
        final Enumeration<String> attributeNames = pRequest.getAttributeNames();
        final String contentType = pRequest.getContentType();
        final Enumeration<String> parameterNames = pRequest.getParameterNames();
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n--------------------REQUEST INFO------------------------\n\n");
        builder.append(String.format(Locale.getDefault(), "Request method: %s;\n", method));
        builder.append(String.format(Locale.getDefault(), "Request URL: %s;\n", requestUrl));
        builder.append(String.format(Locale.getDefault(), "Content type: %s;\n", contentType));
        iterateToBuilder(pRequest, builder, "Header: %s: ", headerNames, HEADER);
        iterateToBuilder(pRequest, builder, "Attribute: %s: ", attributeNames, ATTRIBUTE);
        iterateToBuilder(pRequest, builder, "Parameter: %s: ", parameterNames, PARAMETER);
        return builder.toString();
    }

    private static void iterateToBuilder(
            HttpServletRequest pRequest,
            StringBuilder pBuilder,
            String pFormat,
            Enumeration pEnumeration,
            HttpRequestContentType pContentType
    ) {
        while (pEnumeration.hasMoreElements()) {
            final String key = pEnumeration.nextElement().toString();
            pBuilder.append(String.format(Locale.getDefault(), pFormat, key));
            if (HEADER == pContentType) {
                pBuilder.append(pRequest.getHeader(key)).append(";\n");
            }
            if (ATTRIBUTE == pContentType) {
                pBuilder.append(pRequest.getAttribute(key)).append(";\n");
            }
            if (PARAMETER == pContentType) {
                pBuilder.append(pRequest.getParameter(key)).append(";\n");
            }
        }
    }

    public enum HttpRequestContentType {
        HEADER, ATTRIBUTE, PARAMETER
    }
}
