package gov.osha.dteAdmin;

import java.util.List;

public class EducationCenterDao extends Dao {
    public EducationCenterDao() {
        super(EducationCenter.class);
    }

    public List<EducationCenter> getAuthorizedEdCenters(DteUser currentUser) {
        @SuppressWarnings("unchecked")
        List<EducationCenter> educationCenter = this.getSez().createQuery(
                "from EducationCenter as educationCenter where educationCenter.id=:userEdCenter or 'A'=:userAdmin")
                .setBigDecimal("userEdCenter", currentUser.getEducationCenter().getId())
                .setString("userAdmin", currentUser.getUserType()).list();

        return  educationCenter;
    }
}