package by.zagart.observer.database.services;

import by.zagart.observer.database.entities.Module;
import by.zagart.observer.database.entities.Stand;
import by.zagart.observer.database.services.impl.StandServiceImpl;

import java.util.List;

public class MainService {
    private static StandServiceImpl standService = new StandServiceImpl();

    private MainService() {
    }

    public static boolean isAuthorized(String pLogin, String pToken) {
        return true;
    }

    public static String getAllStandsInJson() {
        final List<Stand> stands = standService.getAll();
        final StringBuilder builder = new StringBuilder();
        for (Stand stand : stands) {
            builder.append(stand.toJSONString());
        }
        return stands.toString();
    }

    public static void persistPairStandModule(
            Stand pStand,
            Module pModule,
            GenericService pStandService,
            GenericService pModuleService
    ) {
        pStandService.save(pStand);
        pModuleService.save(pModule);
        pStand.addModule(pModule);
        pStandService.update(pStand);
        pModuleService.update(pModule);
    }
}
