package gov.osha.dteAdmin;


import org.hibernate.StatelessSession;

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
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        DteUser currentUser = dteUserDao.getUserByOshaCN(oshaCn);
        currentSession.close();
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

        if (!currentUser.getUserType().equals("S") && !retCourse.getEdCenterId().equals(currentUser.getCenterId())) {
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

        if (!currentUser.getUserType().equals("S") && !inCourse.getEdCenterId().equals(currentUser.getCenterId())) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        inCourse.setUpdateUser(currentUser.getOshaCn());
        CourseDao courseDao = DaoFactory.getCourseDao();
        courseDao.save(inCourse);

        return findCourseById(inCourse.getId());
    }

    // This method is called if put
    @PUT
    @Consumes("application/json")
    public Response doPut(Course inCourse) {
//TODO check the update date to see if the record has changed
//        Response response;
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        Course currentCourse = findCourseById(inCourse.getId());

        if (currentCourse.getUpdateDate().compareTo(inCourse.getUpdateDate()) > 0) {

            Response.ResponseBuilder builder = Response.status(Response.Status.CONFLICT);
            builder.type("application/json");
            builder.entity(currentCourse);

            throw new WebApplicationException(builder.build());
        }

        if (!currentUser.getUserType().equals("S") && !inCourse.getEdCenterId().equals(currentUser.getCenterId())) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        inCourse.setUpdateUser(currentUser.getOshaCn());
        CourseDao courseDao = DaoFactory.getCourseDao();
        courseDao.merge(inCourse);

        return Response.ok().build();
    }
}
