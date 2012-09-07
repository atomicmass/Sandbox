/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.sql.DataSource;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;
import java.util.ArrayList;


import java.util.Properties;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.naming.*;

/**
 *
 * @author sean
 */
@ManagedBean(name = "welcome")
@SessionScoped
public class WelcomeBean {

    /**
     * Creates a new instance of WelcomeBean
     */
    public WelcomeBean() {

        System.out.println("welcome bean instantiated");
    }

    /**
     * Returns a message to display
     *
     * @return
     */
    public String getWelcomeMessage() {
        return "welcome message from bean";
    }
    private List<Name> cachedData = null;
    
    private Name selectedName;
    public Name getSelectedName() {
        return selectedName;
    }
    public void setSelectedName(Name selectedName) {
        this.selectedName = selectedName;
    }
    
    private int selectedId;
    public int getSelectedId() {
        return this.selectedId;
    }
    public void setSelectedId(int selectedId) {
        System.out.println("Selected Id: " + selectedId);
        this.selectedId = selectedId;
    }

    /**
     * Gets a list of names
     *
     * @return
     */
    public List<Name> getData() {
        if (cachedData != null) {
            return cachedData;
        }

        try {
            System.out.println("getting data...");

            DataSource ds = getDBConnectionString();
            //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sandbox?zeroDateTimeBehavior=convertToNull", "root", "");
            Connection con = ds.getConnection();//DriverManager.getConnection("jdbc:mysql://localhost:3306/sandbox?zeroDateTimeBehavior=convertToNull", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Names");

            cachedData = new ArrayList<Name>();
            while (rs.next()) {
                cachedData.add(new Name(rs.getString("name"), rs.getInt("id")));
            }

            return cachedData;

        } catch (SQLException ex) {
            Logger.getLogger(WelcomeBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private DataSource getDBConnectionString() {
        try {

            Context ctxt = new InitialContext();
            DataSource ds = (DataSource) ctxt.lookup("java:jboss/datasources/mySqlSandbox");

            System.out.println("Retrieved from config: " + ds.getClass());

            return ds;

        } catch (NamingException nnfe) {
            System.out.println("Encountered a naming exception: " + nnfe.getMessage());
        }

        return null;
    }
}
