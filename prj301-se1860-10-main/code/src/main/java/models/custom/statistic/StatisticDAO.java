package models.custom.statistic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import utils.DBUtils;

public class StatisticDAO {

	public static @NotNull List<Map<Object, Object>> getTotalIncomeStatistic() {
		Map<Object, Object> map = null;
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "	SELECT \r\n" + "    YEAR(created_at) AS year_created, \r\n"
					+ "    SUM(CAST(total_price AS DECIMAL(18, 2))) AS total_price\r\n" + "FROM \r\n"
					+ "    [order] where [order].status = 'COMPLETED'\r\n" + "GROUP BY \r\n" + "    YEAR(created_at)\r\n" + "ORDER BY \r\n"
					+ "    YEAR(created_at);";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Double totalPrice = rs.getDouble("total_price");
					String year = rs.getString("year_created");
					map = new HashMap<Object, Object>();
					map.put("label", year);
					map.put("y", totalPrice);
					list.add(map);
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get categories. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}
	
	public static @NotNull List<Map<Object, Object>> getIncomeByCategory() {
		Map<Object, Object> map = null;
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT category.name, SUM(order_detail.price*order_detail.amount) as 'total_price' FROM order_detail\r\n"
					+ "join [order] on order_detail.order_id = [order].order_id\r\n"
					+ "join product on product.product_id = order_detail.product_id\r\n"
					+ "join [category] on category.category_id = product.category_id\r\n"
					+ "where [order].status = 'COMPLETED'\r\n"
					+ "group by category.name";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Integer totalPrice = rs.getInt("total_price");
					String name = rs.getString("name");
					map = new HashMap<Object, Object>();
					map.put("label", name);
					map.put("y", totalPrice);
					list.add(map);
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get categories. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}
	
	public static double getTongDoanhThu() {
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT SUM(CAST(total_price AS DECIMAL(18, 2))) as 'tongDoanhThu' FROM [order] WHERE [order].status = 'COMPLETED';";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					return rs.getDouble("tongDoanhThu");
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get categories. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return 0;
	}
	
	public static Integer getTongDonHang() {
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT COUNT([order].order_id) as 'tongDonHang' FROM [order] WHERE [order].status = 'COMPLETED';";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					return rs.getInt("tongDonHang");
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get categories. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	public static Integer getTongSanPham() {
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT SUM(order_detail.amount) as 'tongSanPham' FROM order_detail\r\n"
					+ "join [order] on order_detail.order_id = [order].order_id\r\n"
					+ "join product on product.product_id = order_detail.product_id\r\n"
					+ "join [category] on category.category_id = product.category_id\r\n"
					+ "where [order].status = 'COMPLETED'";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					return rs.getInt("tongSanPham");
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get categories. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
	
	public static Integer getTonKho() {
		try {
			Connection con = DBUtils.getConnection();
			String sql = "select sum(remain) as 'tonKho' from product";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					return rs.getInt("tonKho");
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get categories. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
}
