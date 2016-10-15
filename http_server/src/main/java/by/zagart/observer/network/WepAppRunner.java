package by.zagart.observer.network;

import by.zagart.observer.database.services.impl.ModuleServiceImpl;
import by.zagart.observer.database.services.impl.StandServiceImpl;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class WepAppRunner extends Thread {
    public static final Logger logger = Logger.getLogger(WepAppRunner.class);
    private static ModuleServiceImpl moduleService = new ModuleServiceImpl();
    private static int serverSocketPort = 8080;
    private static StandServiceImpl standService = new StandServiceImpl();

    public WepAppRunner() {
        super("WepAppRunner");
    }

    /**
     * Метод обрабатывает данные, накапливающиеся в объекте класса TcpListener,
     * переданного в качестве параметра. Извлекает их и сохраняет в базу данных.
     *
     * @param server Объект класса TcpListener для обработки.
     */
    private void handleTcpData(TcpListener server) {
        (new Thread() {
            //логический блок, отрабатывает при создании объекта класса,
            //в данном случае - при создании потока
            {
                this.setName("TcpDataHandler");
            }

            @Override
            public synchronized void run() {
                try {
                    while (server != null) {
                        Object obj;
                        if ((obj = server.pullObject()) != null) {
                            if (isPackage(obj)) {
                                System.out.println((String.format("\n%s: New package received -> %s",
                                        this.getName(),
                                        obj.toString())));
                                ObserverNetworkPackage pack = (ObserverNetworkPackage) obj;
                                pack.persist(standService, moduleService);
                            }
                        }
                        this.wait(10);
                    }
                } catch (InterruptedException ex) {
                    logger.error(String.format("Error when thread waiting -> %s", ex.getMessage()));
                }
            }
        }).start();
    }

    /**
     * Метод проверяет, является ли объект пакетом данных Observer.
     *
     * @param obj Исследуемый объект.
     * @return Возвращает true, если является, и false - если нет.
     */
    private boolean isPackage(Object obj) {
        if (obj instanceof ObserverNetworkPackage) {
            return true;
        }
        return false;
    }

    /**
     * Метод ожидает подлючений к серверному сокету и как только подключение
     * происходит, запускает поток TcpListener и отдает его методу handleTcpData
     * на обработку.
     */
    private synchronized void waitTcpConnection() {
        System.out.println("Server started. Waiting for connections...");
        try (ServerSocket socket = new ServerSocket(serverSocketPort)) {
            TcpListener server;
            while ((server = new TcpListener(socket.accept())) != null) {
                System.out.println("New connection. Total quantity of connections -> " + TcpListener.getClientsQuantity());
                server.start();
                handleTcpData(server);
            }
        } catch (IOException ex) {
            logger.error(String.format("Error when establishing connection -> %s", ex.getMessage()));
        }
    }

    @Override
    public void run() {
        waitTcpConnection();
    }
}
