package gov.osha.dteAdmin;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/Language")
public class CourseLanguageRS {
    @GET
    @Produces({ "application/xml", "application/json" })
    public List<CourseLanguage> getCourseLanguages() {
        CourseLanguageDao courseLanguageDao = DaoFactory.getCourseLanguageDao();
        @SuppressWarnings("unchecked")
        List <CourseLanguage>retList = courseLanguageDao.getAll();
        return retList;
    }
}
