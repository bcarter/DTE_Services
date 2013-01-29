package gov.osha.dteAdmin;

import org.hibernate.StatelessSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bcarter
 * Date: 1/29/13
 * Time: 10:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class BatchLoadErrorDao extends Dao {
    public BatchLoadErrorDao() {
        super(BatchLoadError.class);
    }

    @SuppressWarnings("unchecked")
    public List<BatchLoadError> getBatchLoadErrors() {
        List<BatchLoadError> retList;
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        retList = (List<BatchLoadError>) currentSession.createQuery(
                "from BatchLoadError as batchLoadError").list();
        currentSession.getTransaction().commit();
        currentSession.close();
        return retList;
    }
}

