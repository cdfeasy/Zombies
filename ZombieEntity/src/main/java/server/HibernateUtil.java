package server;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 29.12.12
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml)
            // config file.
            //  org.hibernate.cfg.
            AnnotationConfiguration cfg=new AnnotationConfiguration().configure(HibernateUtil.class.getResource("/hibernate.cfg.xml"));
            sessionFactory = cfg.buildSessionFactory();


        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Initial SessionFactory creation failed. " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
