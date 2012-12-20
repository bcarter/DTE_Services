package gov.osha.dteAdmin;

import java.util.List;

public class EducationCenterDao extends Dao {
    public EducationCenterDao() {
        super(EducationCenter.class);
    }

    @SuppressWarnings("unchecked")
    public List<EducationCenter> getAuthorizedEdCenters(DteUser currentUser) {
        return (List<EducationCenter>) this.getSez().createQuery(
                "from EducationCenter as educationCenter where educationCenter.id=:userEdCenter or 'A'=:userAdmin")
                .setBigDecimal("userEdCenter", currentUser.getEducationCenter().getId())
                .setString("userAdmin", currentUser.getUserType()).list();
    }
}