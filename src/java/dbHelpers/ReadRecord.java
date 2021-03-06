
package dbHelpers;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Teams;


public class ReadRecord {
    
     
    private Connection conn;
    private ResultSet results;
    
    private Teams team = new Teams();
    private int trackID;
    
    
    public ReadRecord (int trackID) {
    
    Properties props = new Properties(); 
    InputStream instr = getClass().getResourceAsStream("dbConn.properties");
        try {
            props.load(instr);
        } catch (IOException ex) {
            Logger.getLogger(ReadRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            instr.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    String driver = props.getProperty("driver.name");
    String url = props.getProperty("server.name");
    String username = props.getProperty("user.name");
    String passwd = props.getProperty("user.password");
    
    this.trackID = trackID;
    
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReadRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection(url, username, passwd);
        } catch (SQLException ex) {
            Logger.getLogger(ReadRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void doRead() {
    
    try {
            //set up a string to hold query
            String query = "SELECT * FROM players WHERE trackID = ?";
            
            //create a preparedstatement using our query string
            PreparedStatement ps = conn.prepareStatement(query);
            
            //fill in the prepared statement
            ps.setInt(1, trackID);
            
            //execute the query
            this.results = ps.executeQuery();
            
            this.results.next();
            
            team.setTrackID(this.results.getInt("trackID"));
            team.setTeamName(this.results.getString("teamName"));
            team.setTeamState(this.results.getString("teamState"));
            team.setChampions(this.results.getInt("champions"));
            team.setLeague(this.results.getString("league"));
         
        } catch (SQLException ex) {
            Logger.getLogger(ReadRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Teams getTeam(){
        return this.team;
    }
  
}
    
    
    
    

