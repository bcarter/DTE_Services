package gov.osha.dteAdmin;

import org.hibernate.StatelessSession;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/User")
public class DteUserRS {
    @Context
    HttpHeaders headers;

    private DteUser getCurrentUser(String oshaCn) {
        DteUserDao dteUserDao = DaoFactory.getDteUserDao();
        StatelessSession currentSession = HibernateUtil.getSessionFactory().openStatelessSession();
        currentSession.beginTransaction();
        DteUser currentUser = dteUserDao.getUserByOshaCN(oshaCn);
        currentSession.getTransaction().commit();
        currentSession.close();
        return currentUser;
    }

    @SuppressWarnings("unchecked")
    @GET
    @Produces({"application/xml", "application/json"})
    public List<DteUser> getAllDteUsers() {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        DteUserDao dteUserDao = DaoFactory.getDteUserDao();

        List<DteUser> retList;

        if (currentUser.getUserType().equals("S")) {
            retList = dteUserDao.getAll();
        } else {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        return retList;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    @Path("/current")
    public DteUser findCurrentUser() {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));
        return (currentUser);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    @Path("{dteUser}")
    public DteUser findDteUserById(@PathParam("dteUser") BigDecimal dteUserId) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        DteUserDao dteUserDao = DaoFactory.getDteUserDao();
        DteUser retUser;

        if (currentUser.getUserType().equals("S")) {
            retUser = (DteUser) dteUserDao.getById(dteUserId);
        } else {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        return retUser;
    }

    // This method is called if post
    @POST
    @Consumes("application/json")
    public Response doPost(DteUser inUser) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        DteUserDao dteUserDao = DaoFactory.getDteUserDao();

        if (currentUser.getUserType().equals("S")) {
            inUser.setUpdateUser(currentUser.getOshaCn());
            dteUserDao.save(inUser);
        } else {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        return Response.ok().build();
    }

    // This method is called if post
    @PUT
    @Consumes("application/json")
    public Response doPut(DteUser inUser) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        DteUser existingUser = findDteUserById(inUser.getId());

        if (existingUser == null) {
            Response.ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);
            throw new WebApplicationException(builder.build());
        }

        if (!existingUser.getOshaCn().equals(inUser.getOshaCn())) {
            Response.ResponseBuilder builder = Response.status(Response.Status.PRECONDITION_FAILED);
            builder.type("application/json");
            builder.entity(existingUser);

            throw new WebApplicationException(builder.build());
        }

        if (existingUser.getUpdateDate().compareTo(inUser.getUpdateDate()) > 0) {
            Response.ResponseBuilder builder = Response.status(Response.Status.CONFLICT);
            builder.type("application/json");
            builder.entity(existingUser);

            throw new WebApplicationException(builder.build());
        }

        DteUserDao dteUserDao = DaoFactory.getDteUserDao();

        if (currentUser.getUserType().equals("S")) {
            inUser.setUpdateUser(currentUser.getOshaCn());
            dteUserDao.merge(inUser);
        } else {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        return Response.ok().build();
    }
}
