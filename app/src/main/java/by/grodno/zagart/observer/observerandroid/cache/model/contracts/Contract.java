package by.grodno.zagart.observer.observerandroid.cache.model.contracts;
import by.grodno.zagart.observer.observerandroid.cache.model.Module;
import by.grodno.zagart.observer.observerandroid.cache.model.Stand;
import by.grodno.zagart.observer.observerandroid.cache.model.User;

/**
 * Class that contains all available model contracts.
 *
 * @author zagart
 */
public class Contract {
    public static final Class<?>[] MODELS = {
            Module.ModuleContract.class,
            Stand.StandContract.class,
            User.UserContract.class
    };
}
