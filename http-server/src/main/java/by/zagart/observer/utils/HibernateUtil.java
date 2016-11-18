package by.zagart.observer.utils;

import by.zagart.observer.model.entities.Module;
import by.zagart.observer.model.entities.Stand;
import by.zagart.observer.model.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

/**
 * Утилитный класс Hibernate.
 */
public class HibernateUtil {
    public static final int BATCH_SIZE = 20;
    private static final SessionFactory factory;
    private static int batch = 0;
    private static Session currentSession;
    private static Transaction currentTransaction;

    static {
        ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(Stand.class)
                .addAnnotatedClass(Module.class)
                .addAnnotatedClass(User.class)
                .buildMetadata();
        factory = metadata.buildSessionFactory();
    }

    private HibernateUtil() {
    }

    public static Session getCurrentSession() {
        return currentSession;
    }

    public static Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public static Session openCurrentSession() {
        currentSession = factory.openSession();
        return currentSession;
    }

    public static Transaction openCurrentSessionWithTransaction() {
        currentSession = factory.openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentTransaction;
    }

    public static void closeCurrentSession() {
        currentSession.close();
    }

    public static void closeCurrentSessionWithTransaction() {
        if (++batch % BATCH_SIZE == 0) {
            currentSession.flush();
            currentSession.clear();
            batch = 1;
        }
        currentTransaction.commit();
        currentSession.close();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static void closeFactory() {
        factory.close();
    }
}
