package gov.osha.dteAdmin;

import org.hibernate.Session;

import java.util.List;

public class CourseDao extends Dao {
    public CourseDao() {
        super(Course.class);
    }

    @SuppressWarnings("unchecked")
    public List<Course> getAuthorizedCourses(DteUser currentUser) {
        List<Course> retList;
        Session currentSession = HibernateUtil.getSessionFactory().openSession();
        HibernateUtil.beginViewTransaction(currentSession);
        retList = (List<Course>) this.getSession().createQuery(
                "from Course as course where course.activeInd = 1 and (course.educationCenter.id=:userEdCenter or 'A'=:userAdmin)")
                .setBigDecimal("userEdCenter", currentUser.getEducationCenter().getId())
                .setString("userAdmin", currentUser.getUserType()).list();
        HibernateUtil.commitTransaction(currentSession);
        return retList;
    }
}
