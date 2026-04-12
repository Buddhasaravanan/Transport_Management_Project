package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;
import utill.DBconnection;

public class AdminDAO {

//    public int validateAdmin(String email, String password) {
//
//        String sql = """
//            SELECT id 
//            FROM users 
//            WHERE email = ? AND password = ? AND role = 'ADMIN'
//        """;
//
//        try (Connection con = DBconnection.getconnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, email);
//            ps.setString(2, password);
//
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                return rs.getInt("id"); 
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
 
    public User login(String email, String password) {

        String sql = "SELECT * FROM users WHERE email=? AND password=?";

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(User u) {

        String sql = "INSERT INTO users(name,email,password,role) VALUES (?,?,?,?)";

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, "USER");

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
   

    
}
