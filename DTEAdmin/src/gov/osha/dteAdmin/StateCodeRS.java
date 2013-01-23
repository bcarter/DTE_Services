package gov.osha.dteAdmin;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/StateCode")
public class StateCodeRS {
    @GET
    @Produces({"application/xml", "application/json"})
    public List<StateCode> getStateCodes() {
        StateCodeDao stateCodeDao = DaoFactory.getStateCodeDao();
        @SuppressWarnings("unchecked")
        List<StateCode> retList = stateCodeDao.getAll();
        return retList;
    }
}
