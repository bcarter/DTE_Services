package gov.osha.dteAdmin;

// Generated Nov 21, 2012 11:24:52 AM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Hashtable;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DteUser generated by hbm2java
 */
@Entity
@Table(name = "DTE_USERS", schema = "DTE_COURSE_ADMIN", uniqueConstraints = @UniqueConstraint(columnNames = "OSHA_CN"))
@XmlRootElement
public class DteUser implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3875959503053404845L;
    private BigDecimal id;
    private EducationCenter educationCenter;
    private String extranetEmail;
    private String oshaCn;
    private String userType;
    private String updateUser;

    public DteUser() {
    }

    public DteUser(BigDecimal id, EducationCenter educationCenter,
                   String extranetEmail, String oshaCn, String userType) {
        this.id = id;
        this.educationCenter = educationCenter;
        this.extranetEmail = extranetEmail;
        this.oshaCn = oshaCn;
        this.userType = userType;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    public BigDecimal getId() {
        return this.id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CENTER_ID", nullable = false)
    public EducationCenter getEducationCenter() {
        return this.educationCenter;
    }

    public void setEducationCenter(EducationCenter educationCenter) {
        this.educationCenter = educationCenter;
    }

    @Column(name = "EXTRANET_EMAIL", nullable = false, length = 256)
    public String getExtranetEmail() {
        return this.extranetEmail;
    }

    public void setExtranetEmail(String extranetEmail) {
        this.extranetEmail = extranetEmail;
    }

    @Column(name = "OSHA_CN", unique = true, nullable = false, length = 100)
    public String getOshaCn() {
        return this.oshaCn;
    }

    public void setOshaCn(String oshaCn) {
        this.oshaCn = oshaCn;
    }

    @Column(name = "USER_TYPE", nullable = false, length = 1)
    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Column(name = "UPDATE_USER", length = 30)
    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}