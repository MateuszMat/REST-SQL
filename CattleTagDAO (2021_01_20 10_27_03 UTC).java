// SK
package dao.repeat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CattleTagDAO {

    private Connection con = null;

    public static void main(String[] args) {
        CattleTagDAO dao = new CattleTagDAO(); // connect to db
//        System.out.println(dao.getNextTagId());

//        CattleTag tag = dao.getTagDetails(900);
//        System.out.println(tag);
        
        // add a CattleTag
//        dao.addCattleTag(new CattleTag(700, "Male", "Cherly", 1));

        // delete one tag
//        System.out.println(dao.deleteOneTag(700));

        // delete all tags
        dao.deleteAllTags();
        
//        // get all tags
//        List<CattleTag> tagList = dao.getAllTags();
//        System.out.println(tagList.size());
//        for (CattleTag tag : tagList) {
//            System.out.println(tag);
//        }
        // update a tag
//        CattleTag tag = dao.getTagDetails(900);
//        System.out.println(tag);
//        
//        tag.setAge(6);
//        dao.updateCattleTag(tag);
    }

    public CattleTagDAO() {
        try {
            System.out.println("Loading db driver...");
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("Driver loaded...");
            con = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/CD_WD3_DB",
                    "sean",
                    "sean");
        } catch (SQLException ex) {
            System.out.println("Exception!");
            ex.printStackTrace();
            Logger.getLogger(CattleTagDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CattleTag getTagDetails(int tagId) {
        CattleTag car = null;
        try {
            PreparedStatement pstmt = con.prepareStatement(
                    "SELECT TAG_ID, GENDER, BREED, AGE FROM "
                    + "APP.CATTLETAGS WHERE (TAG_ID = ?)");
            pstmt.setInt(1, tagId);

            ResultSet rs = pstmt.executeQuery();

            // move the cursor to the start
            if (!rs.next()) {
                return null;
            }
            // create a CattleTag based on the result of the db query
            car = new CattleTag(rs.getInt("tag_id"), rs.getString("gender"),
                    rs.getString("breed"), rs.getInt("age"));
        } catch (SQLException ex) {
            Logger.getLogger(CattleTagDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }

        return car;
    }

    public List<CattleTag> getAllTags() {
        List<CattleTag> tagList = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(
                    "SELECT * FROM APP.CATTLETAGS");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CattleTag car = new CattleTag(rs.getInt("tag_id"), rs.getString("gender"),
                        rs.getString("breed"), rs.getInt("age"));
                //Car car = new CattleTag(rs.getString(1), rs.getString(2), rs.getString(3));
                tagList.add(car);
            }
        } catch (SQLException ex) {
//            Logger.getLogger(CattleTagDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
        return tagList;
    }

    public int addCattleTag(CattleTag cattleTag) {
        int numRowsInserted = 0;
        try {
            PreparedStatement ps
                    = con.prepareStatement(
                            "INSERT INTO APP.CATTLETAGS "
                            + "(TAG_ID, GENDER, BREED, AGE) "
                            + "VALUES (?,?,?,?)"
                    );
            ps.setInt(1, cattleTag.getTag_id());
            ps.setString(2, cattleTag.getGender());
            ps.setString(3, cattleTag.getBreed());
            ps.setInt(4, cattleTag.getAge());

            numRowsInserted = ps.executeUpdate();   // 1==success; 0==failure
        } catch (SQLException ex) {
//            Logger.getLogger(CattleTagDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("\nException in addCattleTag()");
            ex.printStackTrace();
        }
        return numRowsInserted;     // 1==success; 0==failure
    }

    public int updateCattleTag(CattleTag cattleTag) {
        int returnCode = -1;

        try {
            PreparedStatement ps
                    = con.prepareStatement(
                            "UPDATE APP.CATTLETAGS "
                            + "SET GENDER=?, BREED=?, AGE=? WHERE "
                            + "(TAG_ID=?)"
                    );
            ps.setString(1, cattleTag.getGender());
            ps.setString(2, cattleTag.getBreed());
            ps.setInt(3, cattleTag.getAge());
            ps.setInt(4, cattleTag.getTag_id());

            ps.executeUpdate();
            returnCode = 1; // success
        } catch (SQLException ex) {
//            Logger.getLogger(CattleTagDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("\nException in updateCattleTag()");
            ex.printStackTrace();
        }
        return returnCode;// success
    }

    public int deleteOneTag(int tagId) {
        int numRowsDeleted = 0;

        try {
            // setup the SQL statement
            PreparedStatement ps
                    = con.prepareStatement(
                            "DELETE FROM APP.CATTLETAGS WHERE TAG_ID=?"
                    );
            ps.setInt(1, tagId);
            numRowsDeleted = ps.executeUpdate();   // 1==success; 0==failure
        } catch (Exception ex) {
            System.err.println("\nException in deleteById()");
            ex.printStackTrace();
        }
        return numRowsDeleted;  // 1==success; 0==failure
    }

    public int deleteAllTags() {
        int result = 0;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM APP.CATTLETAGS");
            result = ps.executeUpdate();    // >=1 is success; 0 is failure
            System.out.println("result == " + result);
        } catch (Exception ex) {
            System.err.println("\nException in deleteAllTags()");
            ex.printStackTrace();
        }
        return result;
    }

    public int getNextTagId() {
        int nextCarId = -1;

        try {
            PreparedStatement pstmt = con.prepareStatement(
                    "SELECT MAX(TAG_ID) AS MAX_TAG_ID FROM APP.CATTLETAGS ");
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return -1;
            }

            nextCarId = rs.getInt("MAX_TAG_ID") + 1;
        } catch (SQLException ex) {
            //Logger.getLogger(BankServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
        return nextCarId;
    }

}
