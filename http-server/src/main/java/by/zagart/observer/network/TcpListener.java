package by.zagart.observer.network;

import by.zagart.observer.interfaces.Closeable;
import org.apache.log4j.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Класс, отвечающий за сетевое TCP соединение на стороне
 * сервера. Наследует интерфейс Closeable, который упрощает
 * освобождение ресурсов.
 */
public class TcpListener extends Thread implements Closeable {
    public static final Logger logger = Logger.getLogger(TcpListener.class);
    private static int clientsQuantity = 0;
    private ObjectInputStream input;
    private PrintWriter output;
    private Socket socket;
    private Queue<Object> storage = new ArrayBlockingQueue<>(Byte.MAX_VALUE);

    /**
     * При создании объекта конструктор требует объект класса Socket,
     * являющийся сокетом, запросившим соединение. Так как такой объект
     * можно получить только после установления соединения, сразу получаем
     * входящий/исходящий потоки данных.
     *
     * @param socket Сокет клиента.
     * @throws IOException
     */
    public TcpListener(Socket socket) throws IOException {
        super("TcpListener");
        this.socket = socket;
        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new PrintWriter(socket.getOutputStream(), true);
        clientsQuantity++;
    }

    /**
     * Возвращает статическую целочисленную переменную, которая содержит количество
     * активных соединений.
     *
     * @return Количество соединений (подключенных клиентов).
     */
    public static int getClientsQuantity() {
        return clientsQuantity;
    }

    /**
     * Метод освобождения ресурсов. После его выполнения объект этого класса
     * прекращает выполнять свои функции.
     */
    @Override
    public void close() {
        closeCloseable(input);
        closeCloseable(output);
        closeCloseable(socket);
        clientsQuantity--;
    }

    /**
     * Метод вытягивает объект из FIFO-хранилища объекта и возвращает его, но при этом
     * из хранилища объект также удаляется!
     *
     * @return Первый объект FIFO-хранилища storage (объект класса ArrayBlockingQueue)
     * либо null, если в хранилище пусто.
     */
    public Object pullObject() {
        if (!storage.isEmpty()) {
            return storage.poll();
        }
        return null;
    }

    /**
     * Метод считывает объект с потока данных (потока-объекта класса input).
     *
     * @return Считанный объект либо null, если считывать нечего.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    private Object readObject() throws IOException, ClassNotFoundException, InterruptedException {
        try {
            return input.readObject();
        } catch (EOFException ex) {
            logger.error(String.format("%s: Can't read object, no objects to read.",
                    this.getName()));
            this.wait(1000);
            return false;
        }
    }

    @Override
    public void run() {
        waitInputObject();
    }

    /**
     * Метод ждет получения объекта от сокета клиента. До тех пор, пока поток данных
     * активен, то и метод будет ждать объекты и складывать их в FIFO-хранилище.
     */
    private synchronized void waitInputObject() {
        try {
            output.println("ready");
            while (input != null) {
                Object obj;
                if ((obj = readObject()) != null) {
                    storage.offer(obj);
                    output.println("ready");
                }
                this.wait(10);
            }
        } catch (IOException ex) {
            clientsQuantity--;
            logger.error(String.format("%s: I/O exception -> %s",
                    this.getClass().getSimpleName(),
                    ex.getMessage()));
        } catch (ClassNotFoundException ex1) {
            clientsQuantity--;
            logger.error(String.format("%s: UID serial version not correct! -> %s",
                    this.getClass().getSimpleName(),
                    ex1.getMessage()));
        } catch (InterruptedException ex1) {
            clientsQuantity--;
            logger.error(String.format("%s: Attempt to get monitor when thread waiting -> %s",
                    this.getName(),
                    ex1.getMessage()));
        }
    }
}
