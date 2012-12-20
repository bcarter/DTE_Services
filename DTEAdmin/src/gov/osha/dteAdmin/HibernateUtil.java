package gov.osha.dteAdmin;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml

            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

// new stuff
    public static void beginViewTransaction() {
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().setFlushMode(FlushMode.NEVER);
    }

    public static String showMode() {
        if (transactionIsActive()) {
            return sessionFactory.getCurrentSession().getFlushMode().toString();
        }
        return "NOT-ACTIVE";
    }

    /**
     * Starts a read/write hibernate transaction (FlushMode.AUTO)
     *
     */
    public static void beginWriteTransaction() {
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().setFlushMode(FlushMode.AUTO);
    }


    // Determines whether the current transaction is active

    public static boolean transactionIsActive() {
        return sessionFactory.getCurrentSession().getTransaction().isActive();
    }


    // Commits the hibernate transaction (if active).

    public static void commitTransaction() throws HibernateException {
        if (transactionIsActive()) {
            sessionFactory.getCurrentSession().getTransaction().commit();
        }
    }


    // Rolls back current hibernate transaction (if active).

    public static void rollbackTransaction() {
        if (transactionIsActive()) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }


    // Get's the current transaction's hashCode

    public static long transactionHashcode() {
        return sessionFactory.getCurrentSession().getTransaction().hashCode();
    }
}