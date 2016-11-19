package observer.zagart.by.client.application.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Locale;

import observer.zagart.by.client.application.constants.Constants;
import observer.zagart.by.client.application.constants.DatabaseConstants;
import observer.zagart.by.client.mvp.models.repository.annotations.Table;
import observer.zagart.by.client.mvp.models.repository.annotations.dbId;
import observer.zagart.by.client.mvp.models.repository.annotations.dbInteger;
import observer.zagart.by.client.mvp.models.repository.annotations.dbNotNull;
import observer.zagart.by.client.mvp.models.repository.annotations.dbString;

/**
 * @author zagart
 */

public class ReflectionUtil {

    private static String findSecondaryAnnotations(final Annotation[] pAnnotations, String pType) {
        for (final Annotation secondaryAnnotation : pAnnotations) {
            if (secondaryAnnotation instanceof dbId) {
                final dbId id = (dbId) (secondaryAnnotation);
                pType += Constants.SPACE_STRING + id.value();
                if (id.autoincrement()) {
                    pType += DatabaseConstants.AUTOINCREMENT;
                }
            }
            if (secondaryAnnotation instanceof dbNotNull) {
                final dbNotNull id = (dbNotNull) (secondaryAnnotation);
                if (id.value()) {
                    pType += DatabaseConstants.NOT_NULL;
                }
            }
        }
        return pType;
    }

    @Nullable
    public static String getTableCreateQuery(final Class<?> pClass) {
        final Table table = pClass.getAnnotation(Table.class);
        if (table != null) {
            final StringBuilder builder = new StringBuilder();
            try {
                final String name = table.name();
                final Field[] fields = pClass.getFields();
                for (int i = 0, hits = 0; i < fields.length; i++) {
                    final Field field = fields[i];
                    final Annotation[] annotations = field.getAnnotations();
                    String type = Constants.EMPTY_STRING;
                    for (final Annotation annotation : annotations) {
                        type = addIfDatabaseType(annotations, type, annotation);
                    }
                    if (!TextUtils.isEmpty(type)) {
                        if (hits > 0) {
                            builder.append(DatabaseConstants.TABLE_FIELDS_SEPARATOR);
                        }
                        final String value = (String) field.get(null);
                        builder.append(getFieldCreateTemplate(value, type));
                        hits++;
                    }
                }
                return getTableCreateTemplate(name, builder);
            } catch (ExceptionInInitializerError |
                    IllegalAccessException |
                    IllegalArgumentException pEx) {
                return null;
            }
        }
        return null;
    }

    private static String addIfDatabaseType(final Annotation[] pAnnotations,
                                            String pType,
                                            final Annotation annotation) {
        if (annotation instanceof dbInteger) {
            pType += ((dbInteger) (annotation)).value();
            pType = findSecondaryAnnotations(pAnnotations, pType);
        } else if (annotation instanceof dbString) {
            pType += ((dbString) (annotation)).value();
            pType = findSecondaryAnnotations(pAnnotations, pType);
        }
        return pType;
    }

    private static String getTableCreateTemplate(final String pName, final StringBuilder pBuilder) {
        return String.format(
                Locale.US,
                DatabaseConstants.TABLE_CREATE_TEMPLATE,
                pName,
                pBuilder);
    }

    private static String getFieldCreateTemplate(final String pValue, final String pType) {
        return String.format(Locale.US, "%s %s", pValue, pType);
    }
}
