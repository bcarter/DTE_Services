package gov.osha.dteAdmin;


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
        HibernateUtil.beginViewTransaction();
        DteUser currentUser = dteUserDao.getUserByOshaCN(oshaCn);
        HibernateUtil.commitTransaction();
        return currentUser;
    }

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
