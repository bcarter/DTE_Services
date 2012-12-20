package gov.osha.dteAdmin;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

public class DteUserDao extends Dao {
    public DteUserDao() {
        super(DteUser.class);
    }

    public DteUser getUserByOshaCN(String oshaCN) {
        return (DteUser) this.getSez().createQuery(
                "from DteUser as dteUser where dteUser.oshaCn=:oshaCN")
                .setString("oshaCN", oshaCN).uniqueResult();
    }

    public void save(DteUser dteUser) {
        dteUser.setOshaCn(getLdapCn(dteUser.getExtranetEmail()));
        this.getSez().save(dteUser);
    }

    @SuppressWarnings("unchecked")
    private String getLdapCn(String email) {
        Properties configFile = new Properties();
        StringBuffer ldapUrl = new StringBuffer("ldap://");
        try {
            InputStream xmlStream = getClass().getResourceAsStream("/properties.xml");
            if( xmlStream == null ) {
                ldapUrl.append("155.103.63.232:389");
            }  else {
                configFile.loadFromXML(xmlStream);
                ldapUrl.append(configFile.getProperty("ldapUrl"));
            }
        } catch (IOException exception) {
            // throw exception
        }

        StringBuffer retString= new StringBuffer();
        Hashtable env = new Hashtable(11);
        env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(javax.naming.Context.PROVIDER_URL, ldapUrl.toString());
        try {
            LdapContext ctx = new InitialLdapContext(env, null);
            ctx.setRequestControls(null);

            String searchFilter = "(&(oshamail={0}))";
            Object[] searchArguments = new Object[]{email};

            NamingEnumeration<?> namingEnum = ctx.search("cn=users,dc=osha,dc=gov", searchFilter, searchArguments, getSimpleSearchControls());
            while (namingEnum.hasMore()) {
                SearchResult result = (SearchResult) namingEnum.next();
                Attributes attrs = result.getAttributes();
                retString.append(attrs.get("cn").toString().substring(4));
            }
            namingEnum.close();
        } catch (Exception e) {
            retString = new StringBuffer(e.toString());
        }
        return retString.toString();
    }

    private SearchControls getSimpleSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(30000);
        //String[] attrIDs = {"objectGUID"};
        //searchControls.setReturningAttributes(attrIDs);
        return searchControls;
    }
}