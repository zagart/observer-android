package observer.zagart.by.client.cache.model.contracts;
import observer.zagart.by.client.cache.model.Module;
import observer.zagart.by.client.cache.model.Stand;
import observer.zagart.by.client.cache.model.User;

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
