package gov.osha.dteAdmin;

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
        HibernateUtil.beginViewTransaction();
        DteUser currentUser = dteUserDao.getUserByOshaCN(oshaCn);
        HibernateUtil.commitTransaction();
        return currentUser;
    }

    @SuppressWarnings("unchecked")
    @GET
    @Produces({"application/xml", "application/json"})
    public List<DteUser> getAllDteUsers() {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        DteUserDao dteUserDao = DaoFactory.getDteUserDao();
        HibernateUtil.beginViewTransaction();
        List<DteUser> retList = null;

        if (currentUser.getUserType().equals("A")) {
            retList = dteUserDao.getAll();
        } else {
            HibernateUtil.commitTransaction();
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        HibernateUtil.commitTransaction();
        return retList;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    @Path("{dteUser}")
    public DteUser findDteUserById(@PathParam("dteUser") BigDecimal dteUserId) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        DteUserDao dteUserDao = DaoFactory.getDteUserDao();
        HibernateUtil.beginViewTransaction();
        DteUser retUser = null;

        if (currentUser.getUserType().equals("A")) {
            retUser = (DteUser) dteUserDao.getById(dteUserId);
        } else {
            HibernateUtil.commitTransaction();
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        HibernateUtil.commitTransaction();
        return retUser;
    }

    // This method is called if post
    @POST
    @Consumes("application/json")
    public Response doPost(DteUser inUser) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        DteUserDao dteUserDao = DaoFactory.getDteUserDao();
        HibernateUtil.beginWriteTransaction();
        DteUser retUser = null;

        if (currentUser.getUserType().equals("A")) {
            inUser.setUpdateUser(currentUser.getOshaCn());
            dteUserDao.save(inUser);
        } else {
            HibernateUtil.commitTransaction();
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        HibernateUtil.commitTransaction();
        return Response.ok().build();
    }

    // This method is called if post
    @PUT
    @Consumes("application/json")
    public Response doPut(DteUser inUser) {
        DteUser currentUser = getCurrentUser(headers.getRequestHeader("OSHA_CN").get(0));

        DteUserDao dteUserDao = DaoFactory.getDteUserDao();
        HibernateUtil.beginWriteTransaction();
        DteUser retUser = null;

        if (currentUser.getUserType().equals("A")) {
            inUser.setUpdateUser(currentUser.getOshaCn());
            dteUserDao.merge(inUser);
        } else {
            HibernateUtil.commitTransaction();
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        HibernateUtil.commitTransaction();
        return Response.ok().build();
    }
}
