package gov.osha.dteAdmin;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/Title")
public class CourseTitleRS {
    @GET
    @Produces({ "application/xml", "application/json" })
    public List<CourseTitle> getCourseTitles() {
        CourseTitleDao courseTitleDao = DaoFactory.getCourseTitleDao();
        HibernateUtil.beginViewTransaction();
        @SuppressWarnings("unchecked")
        List <CourseTitle>retList = courseTitleDao.getAll();
        HibernateUtil.commitTransaction();
        return retList;
    }
}
