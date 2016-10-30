package by.grodno.zagart.observer.observerandroid.db.annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for Java-types that are compatible with
 * SQLite INTEGER type.
 *
 * @author zagart
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface dbInteger {
    String value() default "INTEGER";
}
