package observer.zagart.by.client.application.interfaces;

/**
 * Interface implementations have method to covert themselves
 * into {@link Target} object.
 *
 * @author zagart
 */
public interface IConvertible<Target> {

    Target convert();
}
