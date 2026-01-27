package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utill.DBconnection;

public class AdminDAO {

    public int validateAdmin(String email, String password) {

        String sql = """
            SELECT id 
            FROM users 
            WHERE email = ? AND password = ? AND role = 'ADMIN'
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
