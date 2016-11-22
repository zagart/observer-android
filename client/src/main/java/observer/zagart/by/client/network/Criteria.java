package observer.zagart.by.client.network;

/**
 * Class that describes rules for retrieving data from Observer server.
 *
 * @author zagart
 */
public class Criteria {

    public static final String GET_STANDS = "get_stands";
    public static final String GET_MODULES = "get_modules";

    public static String getStandsCriteria() {
        return GET_STANDS;
    }

    public static String getModulesCriteria() {
        return GET_MODULES;
    }
}
