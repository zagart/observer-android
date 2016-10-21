package by.zagart.observer.database.services;

import by.zagart.observer.database.entities.Module;
import by.zagart.observer.database.entities.Stand;

public class MainService {
    private MainService() {
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
