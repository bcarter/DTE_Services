package gov.osha.dteAdmin;

// Generated Nov 21, 2012 11:24:52 AM by Hibernate Tools 4.0.0

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Course generated by hbm2java
 */
@Entity
@Table(name = "OTI_COURSES", schema = "DTE_COURSE_ADMIN")
@XmlRootElement
public class Course implements java.io.Serializable {

	private static final long serialVersionUID = -8812280335351490515L;
	private BigDecimal id;
	private BigDecimal industryId;
	private StateCode stateCode;
	private EducationCenter educationCenter;
	private CourseTitle courseTitle;
	private CourseLanguage courseLanguage;
	private BigDecimal length;
	private String city;
	private String location;
	private String address;
	private String url;
	private Date startDate;
	private Date endDate;
	private BigDecimal cost;
	private BigDecimal cmPoints;
	private BigDecimal ceuPoints;
	private BigDecimal noOfDays;
	private BigDecimal activeInd = new BigDecimal(1);
    private String updateUser;

	public Course() {
	}

	public Course(BigDecimal id, BigDecimal industryId, StateCode stateCode,
			EducationCenter educationCenter,
			CourseTitle courseTitle,
			CourseLanguage courseLanguage, BigDecimal length,
			String city, String location, String address, String url,
			Date startDate, Date endDate, BigDecimal cost, BigDecimal cmPoints,
			BigDecimal ceuPoints, BigDecimal noOfDays, BigDecimal activeInd, String updateUser) {
		setId(id);
		setStateCode(stateCode);
		setEducationCenter(educationCenter);
		setCourseTitle(courseTitle);
		setCourseLanguage(courseLanguage);
		setLength(length);
		setCity(city);
		setLocation(location);
		setAddress(address);
		setUrl(url);
		setStartDate(startDate);
		setEndDate(endDate);
		setCost(cost);
		setCmPoints(cmPoints);
		setCeuPoints(ceuPoints);
		setNoOfDays(noOfDays);
		setActiveInd(activeInd);
        setIndustryId(industryId);
        setUpdateUser(updateUser);
	}

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "oti_course_seq", allocationSize = 1)
	@Column(name = "ID", unique = true, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "INDUSTRY_ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIndustryId() {
		return industryId;
	}

	public void setIndustryId(BigDecimal industryId) {
		this.industryId = (industryId == null)?new BigDecimal("1"):industryId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STATE_ID", nullable = false)
	public StateCode getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(StateCode stateCode) {
		this.stateCode = stateCode;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ED_CENTER_ID", nullable = false)
	public EducationCenter getEducationCenter() {
		return this.educationCenter;
	}

	public void setEducationCenter(EducationCenter educationCenter) {
		this.educationCenter = educationCenter;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TITLE_ID", nullable = false)
	public CourseTitle getCourseTitle() {
		return this.courseTitle;
	}

	public void setCourseTitle(CourseTitle courseTitle) {
		this.courseTitle = courseTitle;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LANG_ID", nullable = false)
	public CourseLanguage getCourseLanguage() {
		return this.courseLanguage;
	}

	public void setCourseLanguage(CourseLanguage courseLanguage) {
		this.courseLanguage = courseLanguage;
	}

	@Column(name = "LENGTH", nullable = false, precision = 22, scale = 0)
	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	@Column(name = "CITY", nullable = false, length = 100)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "LOCATION", nullable = false, length = 100)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "ADDRESS", nullable = false, length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "URL", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", nullable = false, length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", nullable = false, length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "COST", precision = 10)
	public BigDecimal getCost() {
		return this.cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Column(name = "CM_POINTS", precision = 10)
	public BigDecimal getCmPoints() {
		return this.cmPoints;
	}

	public void setCmPoints(BigDecimal cmPoints) {
		this.cmPoints = cmPoints;
	}

	@Column(name = "CEU_POINTS", precision = 10)
	public BigDecimal getCeuPoints() {
		return this.ceuPoints;
	}

	public void setCeuPoints(BigDecimal ceuPoints) {
		this.ceuPoints = ceuPoints;
	}

	@Column(name = "NO_OF_DAYS", precision = 10)
	public BigDecimal getNoOfDays() {
		return this.noOfDays;
	}

	public void setNoOfDays(BigDecimal noOfDays) {
		this.noOfDays = noOfDays;
	}

	@Column(name = "ACTIVE_IND")
	public BigDecimal getActiveInd() {
		return this.activeInd;
	}

	public void setActiveInd(BigDecimal activeInd) {
		this.activeInd = activeInd;
	}

	@Column(name = "UPDATE_USER", length = 30)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}
