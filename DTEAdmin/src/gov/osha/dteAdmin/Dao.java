package gov.osha.dteAdmin;

import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class Dao {

    private Session session;
    private Class aClass;
    protected static final Logger systemLogger = Logger.getLogger("system");

    /*a new instance of Dao */
    public Dao() {
    }

    public Dao(Class aClass) {
        this.aClass = aClass;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public List getAll() {
        Session currentSession = HibernateUtil.getSessionFactory().openSession();
        HibernateUtil.beginViewTransaction(currentSession);
        Query q = this.session.createQuery("FROM " + aClass.getName());
        HibernateUtil.commitTransaction(currentSession);
        return q.list();
    }

    public void save(Object o) {
        Session currentSession = HibernateUtil.getSessionFactory().openSession();
        HibernateUtil.beginWriteTransaction(currentSession);
        session.save(o);
        HibernateUtil.commitTransaction(currentSession);
    }

    public void merge(Object o) {
        Session currentSession = HibernateUtil.getSessionFactory().openSession();
        HibernateUtil.beginWriteTransaction(currentSession);
        session.merge(o);
        HibernateUtil.commitTransaction(currentSession);
    }

    public void delete(Object o) {
        session.delete(o);
    }

    public void rollback(Object o) {
        session.refresh(o); //.evict(o);
    }

    public Object getById(BigDecimal id) {
        Object retObj;
        Session currentSession = HibernateUtil.getSessionFactory().openSession();
        HibernateUtil.beginViewTransaction(currentSession);
        retObj = session.get(aClass, id);
        HibernateUtil.commitTransaction(currentSession);
        return retObj;
    }
}
