package gov.osha.dteAdmin;

import org.hibernate.StatelessSession;

import java.util.List;

public class CourseDao extends Dao {
    public CourseDao() {
        super(Course.class);
    }

    @SuppressWarnings("unchecked")
    public List<Course> getAuthorizedCourses(DteUser currentUser) {
        List<Course> retList;
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        retList = (List<Course>) currentSession.createQuery(
                "from Course as course where course.activeInd = 1 and course.edCenterId=:userEdCenter")
                .setBigDecimal("userEdCenter", currentUser.getCenterId()).list();
        currentSession.getTransaction().commit();
        currentSession.close();
        return retList;
    }
}
