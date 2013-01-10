package gov.osha.dteAdmin;


import org.hibernate.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/Course")
public class CourseRS {
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
    @Produces({"application/xml", "application/json"})
    public List<Course> getAllCourses() {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        CourseDao courseDao = DaoFactory.getCourseDao();
        List<Course> retList = courseDao.getAuthorizedCourses(currentUser);

        return retList;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    @Path("{courseId}")
    public Course findCourseById(@PathParam("courseId") BigDecimal courseId) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        //Course retCourse = null;
        CourseDao courseDao = DaoFactory.getCourseDao();
        Course retCourse = (Course) courseDao.getById(courseId);

        if (!currentUser.getUserType().equals("A") && !retCourse.getEducationCenter().getId().equals(currentUser.getEducationCenter().getId())) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        return retCourse;
    }

    // This method is called if post
    @POST
    @Produces({"application/xml", "application/json"})
    @Consumes("application/json")
    public Course doPost(Course inCourse) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        if (!currentUser.getUserType().equals("A") && !inCourse.getEducationCenter().getId().equals(currentUser.getEducationCenter().getId())) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        inCourse.setUpdateUser(currentUser.getOshaCn());
        CourseDao courseDao = DaoFactory.getCourseDao();
        courseDao.save(inCourse);

        return inCourse;
    }

    // This method is called if put
    @PUT
    @Consumes("application/json")
    public Response doPut(Course inCourse) {
//TODO check the update date to see if the record has changed
//        Response response;
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        CourseDao currentCourseDao = DaoFactory.getCourseDao();
        Course currentCourse = (Course) currentCourseDao.getById(inCourse.getId());

        if (currentCourse.getUpdateDate().compareTo(inCourse.getUpdateDate()) > 0) {

            Response.ResponseBuilder builder = Response.status(Response.Status.CONFLICT);
            builder.type("text/html");
            builder.entity("<h3>Course Changed by Another User</h3>");
            throw new WebApplicationException(builder.build());

//            throw new WebApplicationException(Response.Status.CONFLICT);
        }

        if (!currentUser.getUserType().equals("A") && !inCourse.getEducationCenter().getId().equals(currentUser.getEducationCenter().getId())) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        inCourse.setUpdateUser(currentUser.getOshaCn());
        CourseDao courseDao = DaoFactory.getCourseDao();
        courseDao.merge(inCourse);

        return Response.ok().build();
    }
}
