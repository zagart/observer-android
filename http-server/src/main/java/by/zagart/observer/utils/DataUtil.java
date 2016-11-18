package by.zagart.observer.utils;

import by.zagart.observer.model.entities.Module;
import by.zagart.observer.model.entities.Stand;
import by.zagart.observer.model.services.GenericService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * Утилитный класс с методами для более удобного манипулирования
 * данными.
 */
public class DataUtil {
    public static final Logger logger = Logger.getLogger(DataUtil.class);

    /**
     * Метод генерирует случайный объект класса Module.
     *
     * @return Cлучайный объект класса Module.
     */
    public static Module getNewModule() {
        Module module = new Module();
        module.setName(RandomStringUtils.randomAlphabetic(10));
        module.setStatus(RandomStringUtils.randomAlphabetic(20));
        module.setStatusChangeDate(new Date());
        return module;
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

    /**
     * Метод генерирует случайный объект класса Stand.
     *
     * @return Cлучайный объект класса Stand.
     */
    public static Stand getNewStand() {
        Stand stand = new Stand();
        stand.setNumber("1");
        stand.setDescription(RandomStringUtils.randomAlphabetic(20));
        stand.setModules(new ArrayList<>());
        return stand;
    }

    /**
     * Если текстовая строка, переданная параметром в метод, была сгенерирована
     * методом toString класса Properties либо соответствует ей по формату, то
     * метод сгенерирует из нее объект класса Properties с соответствующими
     * данными. Текстовые значения не должны содержать запятых, будет нарушен
     * формат и метод отработает неправильно.
     *
     * @param strProperties Текстовая строка формата Properties.
     * @return Объект класса Properties.
     * @throws IOException
     */
    public static Properties convertStringToProperties(final String strProperties) throws IOException {
        Properties properties = new Properties();
        String key;
        String value;
        int firstSeparator = -1;
        int secondSeparator;
        int endSeparator = strProperties.lastIndexOf("}");
        try {
            while (true) {
                secondSeparator = strProperties.indexOf('=', firstSeparator);
                key = strProperties.substring(2 + firstSeparator, secondSeparator);
                firstSeparator = secondSeparator;
                secondSeparator = strProperties.indexOf(',', firstSeparator);
                if (secondSeparator < 0) {
                    value = strProperties.substring(1 + firstSeparator, endSeparator);
                    properties.put(key, value);
                    break;
                }
                value = strProperties.substring(1 + firstSeparator, secondSeparator);
                firstSeparator = secondSeparator;
                properties.put(key, value);
            }
        } catch (StringIndexOutOfBoundsException ex) {
            logger.error(String.format("Wrong data format for converting properties! -> %s", ex.getMessage()));
            throw new NoClassDefFoundError();
        }
        return properties;
    }
}
