package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Bus;
import utill.DBconnection;

public class BusDAO 
{
	public List<Bus> getAllBuses()
	{
		List<Bus> busList = new ArrayList<>();
		
		try
		{
			Connection con = DBconnection.getconnection();
			String sql = "SELECT * FROM bus";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				Bus bus = new Bus();
				bus.setBusid(rs.getInt("bus_id"));
				bus.setBusnumber(rs.getString("bus_number"));
				bus.setTotalSeats(rs.getInt("total_seats"));
				bus.setBusType(rs.getString("bus_type"));
				
				busList.add(bus);
			}
			
			rs.close();
			ps.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return busList;
	}
	
	
	public boolean addBus(Bus bus)
	{
		String sql = "INSERT INTO bus (bus_number, total_seats, bus_type) VALUES (?, ?, ?)";
		
		try
		{
			Connection con = DBconnection.getconnection();
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, bus.getBusnumber());
			ps.setInt(2, bus.getTotalSeats());
			ps.setString(3, bus.getBusType());
			
			ps.executeUpdate();
			
			 return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	
}
