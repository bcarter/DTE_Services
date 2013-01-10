package gov.osha.dteAdmin;

import org.hibernate.Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;

@Path("/EducationCenter")
public class EducationCenterRS {
    @Context
    HttpHeaders headers;


    private DteUser getCurrentUser(String oshaCn) {
        DteUserDao dteUserDao = DaoFactory.getDteUserDao();
        Session currentSession = HibernateUtil.getSessionFactory().openSession();
        HibernateUtil.beginViewTransaction(currentSession);
        DteUser currentUser = dteUserDao.getUserByOshaCN(oshaCn);
        HibernateUtil.commitTransaction(currentSession);
        return currentUser;
    }

    @GET
    @Produces({ "application/xml", "application/json" })
    public List<EducationCenter> getEducationCenters() {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        EducationCenterDao educationCenterDao = DaoFactory.getEducationCenterDao();
        List<EducationCenter> retList = educationCenterDao.getAuthorizedEdCenters(currentUser);

        return retList;
    }
}
