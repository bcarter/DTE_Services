package gov.osha.dteAdmin;


import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;

@Path("/Course")
public class CourseRS {
    @Context
    HttpHeaders headers;

    private DteUser getCurrentUser(String oshaCn) {
        DteUserDao dteUserDao = DaoFactory.getDteUserDao();
        HibernateUtil.beginViewTransaction();
        DteUser currentUser = dteUserDao.getUserByOshaCN(oshaCn);
        HibernateUtil.commitTransaction();
        return currentUser;
    }

    // This method is called if TEXT_PLAIN is request
//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public String sayPlainTextHello() {
//        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));
//        String retString = "";
//
//        DteUserDao dteUserDao = DaoFactory.getDteUserDao();
//        HibernateUtil.beginViewTransaction();
//        DteUser altUser = (DteUser) dteUserDao.getById(new BigDecimal(42));
//
//        try {
//            retString += "Current User Ed Center ID: " + currentUser.getEducationCenter().getId() + "<br>";
//            retString += "Current User Email: " + altUser.getExtranetEmail() + "<br>";
//        } catch (Exception e) {
//            retString += headers.getRequestHeader("OSHA_CN").get(0) + "<br>" + e.toString() + "<br>";
//        }
//
//        for (String header : headers.getRequestHeaders().keySet()) {
//            retString += header + ": " + headers.getRequestHeader(header).get(0) + "<br>";
//        }
//
//        CourseDao courseDao = DaoFactory.getCourseDao();
//        Course retCourse = (Course) courseDao.getById(new BigDecimal(109));
//        retString += "retCourse.getEducationCenter().getId(): " + retCourse.getEducationCenter().getId() + "<br>";
//        retString += "currentUser.getEducationCenter().getId(): " + currentUser.getEducationCenter().getId() + "<br>";
//
//        List<Course> list = courseDao.getAuthorizedCourses(currentUser);
//
//        for (Course course : list) {
//            retString += "Source Document: \"" + course.getLocation() + "\", " + course.getUpdateUser() + "\", " + course.getNoOfDays() + "<br>";
//        }
//
//        HibernateUtil.commitTransaction();
//        return retString;
//    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Course> getAllCourses() {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        CourseDao courseDao = DaoFactory.getCourseDao();
        HibernateUtil.beginViewTransaction();
        List<Course> retList = courseDao.getAuthorizedCourses(currentUser);
        HibernateUtil.commitTransaction();

        return retList;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    @Path("{courseId}")
    public Course findCourseById(@PathParam("courseId") BigDecimal courseId) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        //Course retCourse = null;
        CourseDao courseDao = DaoFactory.getCourseDao();
        HibernateUtil.beginViewTransaction();
        Course retCourse = (Course) courseDao.getById(courseId);
        HibernateUtil.commitTransaction();

        if (!currentUser.getUserType().equals("A") && !retCourse.getEducationCenter().getId().equals(currentUser.getEducationCenter().getId())) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        return retCourse;
    }

    // This method is called if post
    @POST
    @Consumes("application/json")
    public Response doPost(Course inCourse) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        if (!currentUser.getUserType().equals("A") && !inCourse.getEducationCenter().getId().equals(currentUser.getEducationCenter().getId())) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        inCourse.setUpdateUser(currentUser.getOshaCn());
        CourseDao courseDao = DaoFactory.getCourseDao();
        HibernateUtil.beginWriteTransaction();
        courseDao.save(inCourse);
        HibernateUtil.commitTransaction();

        return Response.ok().build();
    }

    // This method is called if put
    @PUT
    @Consumes("application/json")
    public Response doPut(Course inCourse) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        if (!currentUser.getUserType().equals("A") && !inCourse.getEducationCenter().getId().equals(currentUser.getEducationCenter().getId())) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        inCourse.setUpdateUser(currentUser.getOshaCn());
        CourseDao courseDao = DaoFactory.getCourseDao();
        HibernateUtil.beginWriteTransaction();
        courseDao.merge(inCourse);
        HibernateUtil.commitTransaction();

        return Response.ok().build();
    }
}
