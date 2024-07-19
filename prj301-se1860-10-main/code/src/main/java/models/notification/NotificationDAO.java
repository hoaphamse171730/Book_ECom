package models.notification;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import utils.DBUtils;

public class NotificationDAO {
	@NotNull
	public static List<NotificationDTO> getNotificationsOfUser(int userId, @NotNull Duration recent) {
		List<NotificationDTO> list = new ArrayList<>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "select * from [notification] where [user_id] = ? "
					+ "and [created_at] >= ? order by [created_at] desc";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setDate(2, new Date(System.currentTimeMillis() - recent.toMillis()));
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					int notiId = rs.getInt("notification_id");
					String url = rs.getString("message");
					String type = rs.getString("type");
					boolean read = rs.getBoolean("read");
					Date createdAt = rs.getDate("created_at");
					NotificationDTO noti = new NotificationDTO(notiId, userId, url, type, read, createdAt);
					list.add(noti);
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get notifications of user. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}

	public static @Nullable Integer addNotification(@NotNull NotificationDTO noti) {
		Integer result = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "insert into [notification] ([user_id], [message], [type], [read], [created_at]) "
					+ "values (?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, noti.getUserId());
			stmt.setString(2, noti.getMessage());
			stmt.setString(3, noti.getType());
			stmt.setBoolean(4, noti.isRead());
			stmt.setObject(5, Timestamp.valueOf(LocalDateTime.now()));
			if (stmt.executeUpdate() > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next())
					result = rs.getInt(1);
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to add notification. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static boolean markNotificationRead(int notificationId) {
		boolean result = false;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "update [notification] set [read] = ? where [notification_id] = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setBoolean(1, true);
			stmt.setInt(2, notificationId);
			result = stmt.executeUpdate() > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to mark notification. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}
}
