package by.zagart.observer.interfaces;

import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Интерфейс расширяет аналогичный интерфейс из пакета java.io.*
 */
public interface Closeable extends java.io.Closeable {
    Logger logger = Logger.getLogger(Closeable.class);

    default void closeCloseable(java.io.Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ex) {
                logger.error(String.format("%s: I/O exception when closing %s - > %s",
                        this.getClass().getSimpleName(),
                        closeable.getClass().getSimpleName(),
                        ex.getMessage()));
            }
        }
    }
}
