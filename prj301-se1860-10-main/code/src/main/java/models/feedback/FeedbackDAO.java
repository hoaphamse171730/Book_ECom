package models.feedback;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import models.user.UserDAO;
import models.user.UserDTO;
import utils.DBUtils;

public class FeedbackDAO {

	public static @NotNull List<FeedbackDTO> getFeedbacksOfProduct(int productId, @NotNull FeedbackQuery query) {
		List<FeedbackDTO> list = new ArrayList<>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT * FROM (" +
					"  SELECT *, ROW_NUMBER() OVER (" +
					"     ORDER BY [" + query.getSortBy() + "] " + query.getSortOrder() +
					"  ) AS [row_num] FROM [feedback] WHERE 1=1";
			if (query.getKeyword() != null)
				sql += " AND [" + query.getSearchBy() + "] LIKE ? ";
			sql += " AND [product_id] = " + productId;
			sql +=  ") AS [num_tb] " +
					"WHERE [row_num] >= ? AND [row_num] <= ?";
			PreparedStatement stm = con.prepareStatement(sql);
			if (query.getKeyword() != null) {
				stm.setString(1, "%" + query.getSearchBy() + "%");
				stm.setInt(2, query.getStartRow());
				stm.setInt(3, query.getEndRow());
			} else {
				stm.setInt(1, query.getStartRow());
				stm.setInt(2, query.getEndRow());
			}
			ResultSet rs = stm.executeQuery();
			while (rs != null && rs.next()) {
				UserDTO user = UserDAO.getUser(rs.getInt("user_id"));
				FeedbackDTO feedback = new FeedbackDTO(
						rs.getInt("feedback_id"),
						rs.getInt("user_id"),
						rs.getInt("product_id"),
						rs.getInt("rating"),
						rs.getString("content"),
						rs.getDate("created_at"),
						user);
				list.add(feedback);
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Error in getting feedbacks of product. Details: " + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}
	
	public static FeedbackDTO getFeedback(int productId, int userId) {
		FeedbackDTO feedback = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT TOP 1 * FROM [feedback] WHERE user_id = ? AND product_id = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, userId);
			stm.setInt(2, productId);
			ResultSet rs = stm.executeQuery();
			if (rs != null && rs.next()) {
				feedback = new FeedbackDTO(rs.getInt("feedback_id"), rs.getInt("user_id"),
						rs.getInt("product_id"), rs.getInt("rating"), rs.getString("content"), rs.getDate("created_at"));
				return feedback;
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Error in getting feedbacks of product. Details: " + ex.getMessage());
			ex.printStackTrace();
		}
		return feedback;
	}

	public static @Nullable Integer createFeedback(@NotNull FeedbackDTO feedback) {
		Integer result = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "INSERT INTO [feedback] ([product_id], [user_id], "
					+ "[rating], [content], [created_at]) VALUES  (?, ?, ?, ?, ?) ";
			PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, feedback.getProductId());
			stm.setInt(2, feedback.getUserId());
			stm.setInt(3, feedback.getRating());
			stm.setString(4, feedback.getContent());
			stm.setDate(5, new Date(System.currentTimeMillis()));
			if (stm.executeUpdate() > 0) {
				ResultSet rs = stm.getGeneratedKeys();
				if (rs.next())
					result = rs.getInt(1);
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Error in creating feedback. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static boolean updateFeedback(@NotNull FeedbackDTO feedback) {
		boolean ok = false;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "UPDATE [feedback] SET [rating] = ?, [content] = ? WHERE [feedback_id] = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, feedback.getRating());
			stm.setString(2, feedback.getContent());
			stm.setInt(3, feedback.getFeedbackId());
			ok = stm.executeUpdate() > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Error in updating feedback. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return ok;
	}

	public static boolean deleteFeedback(int feedbackId) {
		boolean ok = false;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "DELETE FROM [feedback] WHERE [feedback_id] = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, feedbackId);
			ok = stm.executeUpdate() > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Error in deleting feedback. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return ok;
	}
}
