package observer.zagart.by.client.mvp.models.repository.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for model-class that will be mapped on DatabaseManager
 * database as table.
 *
 * @author zagart
 */
@Target(ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Table {

    String name() default "Table";
}
