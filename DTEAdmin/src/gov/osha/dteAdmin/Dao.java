package gov.osha.dteAdmin;

import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class Dao {

    private Session sez;
    private Class clz;
    protected static final Logger systemLogger = Logger.getLogger("system");

    /*a new instance of AbstractDAO */
    public Dao() {
    }

    public Dao(Class clz) {
        this.clz = clz;
    }

    public Session getSez() {
        return sez;
    }

    public void setSez(Session sez) {
        this.sez = sez;
    }

    public Class getClz() {
        return clz;
    }

    public void setClz(Class clz) {
        this.clz = clz;
    }

    public List getAll() {
        Query q = this.sez.createQuery("FROM " + clz.getName());
        return q.list();
    }

    public void save(Object o) {
        sez.save(o);
    }

    public void merge(Object o) {
        sez.merge(o);
    }

    public void delete(Object o) {
        sez.delete(o);
    }

    public void rollback(Object o) {
        sez.evict(o);
    }

    public Object getById(BigDecimal id) {
        return sez.get(clz, id);
    }
}
