package observer.zagart.by.client.application.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Locale;

import observer.zagart.by.client.application.constants.ApplicationConstants;
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
                pType += ApplicationConstants.SPACE_STRING + id.value();
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
    public static String getTableName(Class<?> pClass) {
        final Table table = pClass.getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        } else {
            return null;
        }
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
                    String type = ApplicationConstants.EMPTY_STRING;
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

    @SuppressWarnings("unchecked")
    public static <Entity> Entity getGenericObject(Object pTarget, int parameterPosition) {
        ParameterizedType parameterizedType = (ParameterizedType) pTarget.getClass()
                .getGenericSuperclass();
        Class<?> clazz = (Class<?>) parameterizedType.getActualTypeArguments()[parameterPosition];
        Constructor<?> constructor = clazz.getConstructors()[0];
        Object object;
        if (Build.VERSION.SDK_INT >= 19) {
            object = getObjectApi19(constructor);
        } else {
            object = getObject(constructor);
        }
        return (Entity) object;
    }

    @TargetApi(19)
    private static Object getObjectApi19(final Constructor<?> pConstructor) {
        Object object = null;
        try {
            object = pConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException pEx) {
            Log.e(ReflectionUtil.class.getSimpleName(), pEx.getMessage(), pEx);
        }
        return object;
    }

    private static Object getObject(final Constructor<?> pConstructor) {
        Object object = null;
        try {
            object = pConstructor.newInstance();
        } catch (Exception pEx) {
            Log.e(ReflectionUtil.class.getSimpleName(), pEx.getMessage(), pEx);
        }
        return object;
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
