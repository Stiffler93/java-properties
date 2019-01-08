package sonntag.properties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Properties annotation can be used to bind properties to
 * an instance as an aggregation. Every instance of the
 * annotated class will then possess all of the properties quoted
 * with these annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Properties {
    Class<? extends Property>[] properties();
}
