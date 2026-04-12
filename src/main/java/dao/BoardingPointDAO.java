package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import utill.DBconnection;

public class BoardingPointDAO {

    public List<Object[]> getByRoute(int routeId) {

        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT boarding_id, location, boarding_time
            FROM boarding_points
            WHERE route_id = ?
        """;

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getTime(3)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public String getNameById(int id) {

        String sql = "SELECT location FROM boarding_points WHERE boarding_id = ?";

        try (Connection con = DBconnection.getconnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("location");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
