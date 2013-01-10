package gov.osha.dteAdmin;
import org.hibernate.Session;

public class DaoFactory {
    //returns courseDAO
    public static CourseDao getCourseDao()
    {
        return (CourseDao)getDAOByClass(CourseDao.class);
    }

    //returns DteUserDao
    public static DteUserDao getDteUserDao()
    {
        return (DteUserDao)getDAOByClass(DteUserDao.class);
    }

    //returns EducationCenterDao
    public static EducationCenterDao getEducationCenterDao()
    {
        return (EducationCenterDao)getDAOByClass(EducationCenterDao.class);
    }

    //returns CourseLanguageDao
    public static CourseLanguageDao getCourseLanguageDao()
    {
        return (CourseLanguageDao)getDAOByClass(CourseLanguageDao.class);
    }

    //returns StateCodeDao
    public static StateCodeDao getStateCodeDao()
    {
        return (StateCodeDao)getDAOByClass(StateCodeDao.class);
    }

    //returns CourseTitleDao
    public static CourseTitleDao getCourseTitleDao()
    {
        return (CourseTitleDao)getDAOByClass(CourseTitleDao.class);
    }

    //returns the DAO and configures it with current session

    public static Dao getDAOByClass(Class c)
    {
        try
        {
            Session s = getCurrentSession();
            Dao d = (Dao)c.newInstance();
            d.setSession(s);
            return d;
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Session getCurrentSession()
    {
        return HibernateUtil.getSessionFactory().openSession();//  .getCurrentSession();
    }
}
