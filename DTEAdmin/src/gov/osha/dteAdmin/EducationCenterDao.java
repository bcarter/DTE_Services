package gov.osha.dteAdmin;

import org.hibernate.StatelessSession;

import java.util.List;

public class EducationCenterDao extends Dao {
    public EducationCenterDao() {
        super(EducationCenter.class);
    }

    @SuppressWarnings("unchecked")
    public List<EducationCenter> getAuthorizedEdCenters(DteUser currentUser) {
        List<EducationCenter> retList;
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        retList = (List<EducationCenter>) currentSession.createQuery(
                "from EducationCenter as educationCenter where educationCenter.id=:userEdCenter or 'A'=:userAdmin")
                .setBigDecimal("userEdCenter", currentUser.getEducationCenter().getId())
                .setString("userAdmin", currentUser.getUserType()).list();
        currentSession.getTransaction().commit();
        currentSession.close();
        return retList;
    }
}