package models.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import utils.DBUtils;

public class UserDAO {

	public static int countUsers(@NotNull UserQuery query) {
		int total = 0;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT count(1) FROM [user]";
			if (query.getKeyword() != null)
				sql += " WHERE [" + query.getSearchBy() + "] LIKE ?";

			PreparedStatement stmt = con.prepareStatement(sql);
			if (query.getKeyword() != null)
				stmt.setString(1, "%" + query.getKeyword() + "%");

			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				total = rs.getInt(1);

			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to count users. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return total;
	}

	public static @NotNull List<UserDTO> getUsers(@NotNull UserQuery query) {
		List<UserDTO> list = new ArrayList<>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT * FROM (" +
					"  SELECT *, ROW_NUMBER() OVER (" +
					"     ORDER BY [" + query.getSortBy() + "] " + query.getSortOrder() +
					"  ) AS [row_num] FROM [user]";
			if (query.getKeyword() != null)
				sql += " WHERE [" + query.getSearchBy() + "] LIKE ?";
			sql +=  ") AS [num_tb]" +
					"WHERE [row_num] >= ? AND [row_num] <= ?";

			PreparedStatement stmt = con.prepareStatement(sql);
			if (query.getKeyword() != null) {
				stmt.setString(1, "%" + query.getKeyword() + "%");
				stmt.setInt(2, query.getStartRow());
				stmt.setInt(3, query.getEndRow());
			} else {
				stmt.setInt(1, query.getStartRow());
				stmt.setInt(2, query.getEndRow());
			}

			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					int id = rs.getInt("user_id");
					String email = rs.getString("email");
					String name = rs.getString("fullname");
					Gender gender = EnumUtils.getEnum(Gender.class, rs.getString("gender"), Gender.UNKNOWN);
					String address = rs.getString("address");
					String phoneNumber = rs.getString("phone_number");
					Date createAt = rs.getDate("created_at");
					Role role = EnumUtils.getEnum(Role.class, rs.getString("role"), Role.USER);

					UserDTO user = new UserDTO(id, email, name, gender, address, phoneNumber, createAt, role);
					list.add(user);
				}
			}

			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get users. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}

	public static @Nullable UserDTO getUser(int id) {
		UserDTO result = null;

		try {
			Connection con = DBUtils.getConnection();
			String sql = "select * from [user] where [user_id] = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					String email = rs.getString("email");
					String name = rs.getString("fullname");
					Gender gender = EnumUtils.getEnum(Gender.class, rs.getString("gender"), Gender.UNKNOWN);
					String address = rs.getString("address");
					String phoneNumber = rs.getString("phone_number");
					Date createAt = rs.getDate("created_at");
					Role role = EnumUtils.getEnum(Role.class, rs.getString("role"), Role.USER);

					result = new UserDTO(id, email, name, gender, address, phoneNumber, createAt, role);
				}
			}

			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get user. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static boolean register(@NotNull String email, @NotNull String password, @NotNull String fullname,
			@NotNull Gender gender, @NotNull String address, @NotNull String phoneNumber, @NotNull Role role) {
		password = DigestUtils.sha256Hex(password);
		boolean result = false;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "insert into [user] ([email], [password], [fullname], [gender], [address], "
					+ "[phone_number], [role], [created_at]) values (?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			stmt.setString(3, fullname);
			stmt.setString(4, gender.toString());
			stmt.setString(5, address);
			stmt.setString(6, phoneNumber);
			stmt.setString(7, role.toString());
			stmt.setDate(8, new Date(System.currentTimeMillis()));
			result = stmt.executeUpdate() > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to register user. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static @Nullable Integer login(@NotNull String email, @NotNull String password) {
		password = DigestUtils.sha256Hex(password);
		Integer id = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "select [user_id] from [user] where [email] = ? and [password] = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs != null) {
				if (rs.next()) {
					id = rs.getInt("user_id");
				}
			}

			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to login user. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return id;
	}

	public static boolean update(@NotNull UserDTO user) {
		boolean result = false;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "update [user] set [fullname] = ?, [gender] = ?, [address] = ?, [phone_number] = ?, [role] = ? where user_id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getGender().toString());
			stmt.setString(3, user.getAddress());
			stmt.setString(4, user.getPhoneNumber());
			stmt.setString(5, user.getRole().toString());
			stmt.setInt(6, user.getId());
			int rs = stmt.executeUpdate();
			result = rs > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to update user. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static boolean updatePassword(int userId, @NotNull String newPassword) {
		boolean result = false;
		newPassword = DigestUtils.sha256Hex(newPassword);
		try {
			Connection con = DBUtils.getConnection();
			String sql = "UPDATE [user] SET [password] = ? WHERE [user_id] = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, newPassword);
			stm.setInt(2, userId);
			int rs = stm.executeUpdate();
			result = rs > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to update user password. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}
}
