package gov.osha.dteAdmin;

import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
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
//    public static void beginViewTransaction(StatelessSession currentSession) {
//        currentSession.beginTransaction();
////        currentSession.setFlushMode(FlushMode.NEVER);
//    }

    public static String showMode(StatelessSession currentSession) {
        if (transactionIsActive()) {
//            return currentSession.getFlushMode().toString();
        }
        return "NOT-ACTIVE";
    }

    /**
     * Starts a read/write hibernate transaction (FlushMode.AUTO)
     *
     */
//    public static void beginWriteTransaction(StatelessSession currentSession) {
//        currentSession.beginTransaction();
////        currentSession.setFlushMode(FlushMode.AUTO);
//    }


    // Determines whether the current transaction is active

    public static boolean transactionIsActive() {
        return sessionFactory.getCurrentSession().getTransaction().isActive();
    }


    // Commits the hibernate transaction (if active).

//    public static void commitTransaction(StatelessSession currentSession) throws HibernateException {
//        if (transactionIsActive()) {
//            currentSession.getTransaction().commit();
//        }
//    }


    // Rolls back current hibernate transaction (if active).

    public static void rollbackTransaction(StatelessSession currentSession) {
        if (transactionIsActive()) {
            currentSession.getTransaction().rollback();
        }
    }


    // Get's the current transaction's hashCode

    public static long transactionHashcode(StatelessSession currentSession) {
        return currentSession.getTransaction().hashCode();
    }
}