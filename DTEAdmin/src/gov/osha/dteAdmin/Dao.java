package gov.osha.dteAdmin;

import org.hibernate.Query;
import org.hibernate.StatelessSession;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class Dao {

//    private Session session;
    private Class aClass;
    protected static final Logger systemLogger = Logger.getLogger("system");

    /*a new instance of Dao */
    public Dao() {
    }

    public Dao(Class aClass) {
        this.aClass = aClass;
    }

//    public Session getSession() {
//        return session;
//    }
//
//    public void setSession(Session session) {
//        this.session = session;
//    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public List getAll() {
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        List retList = currentSession.createQuery("FROM " + aClass.getName()).list();
        currentSession.getTransaction().commit();
        currentSession.close();
        return retList;
    }

    public void save(Object o) {
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        currentSession.insert(o); //.save(o);
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    public void merge(Object o) {
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        currentSession.update(o); //.merge(o);
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    public void delete(Object o) {
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        currentSession.delete(o);
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    public void rollback(Object o) {
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        currentSession.refresh(o); //.evict(o);
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    public Object getById(BigDecimal id) {
        Object retObj;
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        retObj = currentSession.get(aClass, id);
        currentSession.getTransaction().commit();
        currentSession.close();
        return retObj;
    }
}
