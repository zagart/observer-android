package observer.zagart.by.client.repository.entities.annotations;

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
public @interface dbNotNull {

    boolean value() default true;
}
