package by.zagart.observer;

import by.zagart.observer.utils.HibernateUtil;

public class WebAppMain {
    /**
     * Точка входа веб-приложения.
     */
    public static void main(String[] args) {
        if (HibernateUtil.getSessionFactory() != null) {
            HibernateUtil.closeFactory();
        }
    }
}
