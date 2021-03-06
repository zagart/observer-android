package observer.zagart.by.client.mvp.models.repository.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for primary key fields.
 *
 * @author zagart
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface dbId {

    boolean autoincrement() default true;

    String value() default "PRIMARY KEY";
}
