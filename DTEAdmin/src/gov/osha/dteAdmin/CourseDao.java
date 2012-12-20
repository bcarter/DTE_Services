package gov.osha.dteAdmin;

import java.util.List;

public class CourseDao extends Dao {
    public CourseDao() {
        super(Course.class);
    }

    @SuppressWarnings("unchecked")
    public List<Course> getAuthorizedCourses(DteUser currentUser) {
        return (List<Course>) this.getSez().createQuery(
                "from Course as course where course.activeInd = 1 and (course.educationCenter.id=:userEdCenter or 'A'=:userAdmin)")
                .setBigDecimal("userEdCenter", currentUser.getEducationCenter().getId())
                .setString("userAdmin", currentUser.getUserType()).list();
    }
}
