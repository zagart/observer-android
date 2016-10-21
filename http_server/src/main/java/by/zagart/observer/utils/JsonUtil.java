package by.zagart.observer.utils;

import by.zagart.observer.database.entities.Module;
import by.zagart.observer.database.entities.Stand;
import by.zagart.observer.database.services.impl.ModuleServiceImpl;
import by.zagart.observer.database.services.impl.StandServiceImpl;
import org.json.simple.JSONObject;

/**
 * Utility class for work with JSON format.
 */
public class JsonUtil {
    private static ModuleServiceImpl sModuleService = new ModuleServiceImpl();
    private static StandServiceImpl sStandService = new StandServiceImpl();

    public static JSONObject moduleToJsonObject(Module pModule) {
        JSONObject module = new JSONObject();
        module.put("stand_id", pModule.getStand().getId());
        module.put("name", pModule.getName());
        module.put("status", pModule.getStatus());
        module.put("date", pModule.getStatusChangeDate());
        return module;
    }

    public static JSONObject moduleToJsonObject(Long pId) {
        Module module = sModuleService.getById(pId);
        return moduleToJsonObject(module);
    }

    public static JSONObject standToJsonObject(Stand pStand) {
        JSONObject stand = new JSONObject();
        stand.put("id", pStand.getId());
        stand.put("description", pStand.getDescription());
        return stand;
    }

    public static JSONObject standToJsonObject(Long pId) {
        Stand stand = sStandService.getById(pId);
        return standToJsonObject(stand);
    }

    public static String getStaticJsonString() {
        final String json = (JsonUtil.moduleToJsonObject(sModuleService.getById(1L))).toJSONString();
        System.out.println(json);
        return json;
    }
}
