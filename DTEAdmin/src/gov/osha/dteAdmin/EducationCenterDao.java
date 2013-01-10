package gov.osha.dteAdmin;

import org.hibernate.Session;
import org.hibernate.StatelessSession;

import java.util.List;

public class EducationCenterDao extends Dao {
    public EducationCenterDao() {
        super(EducationCenter.class);
    }

    @SuppressWarnings("unchecked")
    public List<EducationCenter> getAuthorizedEdCenters(DteUser currentUser) {
        List<EducationCenter> retList;
        Session currentSession = HibernateUtil.getSessionFactory().openSession();
        HibernateUtil.beginViewTransaction(currentSession);
        retList = (List<EducationCenter>) this.getSession().createQuery(
                "from EducationCenter as educationCenter where educationCenter.id=:userEdCenter or 'A'=:userAdmin")
                .setBigDecimal("userEdCenter", currentUser.getEducationCenter().getId())
                .setString("userAdmin", currentUser.getUserType()).list();
        HibernateUtil.commitTransaction(currentSession);
        return retList;
    }
}