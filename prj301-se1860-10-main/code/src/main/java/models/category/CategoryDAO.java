package models.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import utils.DBUtils;

public class CategoryDAO {

	public static @NotNull List<CategoryDTO> getCategories() {
		List<CategoryDTO> list = new ArrayList<>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "select * from [category]";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					int categoryId = rs.getInt("category_id");
					String name = rs.getString("name");
					CategoryDTO category = new CategoryDTO(categoryId, name);
					list.add(category);
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get categories. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}

	public static @NotNull CategoryDTO getCategory(int id) {
		CategoryDTO category = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "select * from [category] where category_id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					int categoryId = rs.getInt("category_id");
					String name = rs.getString("name");
					category = new CategoryDTO(categoryId, name);
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get categories. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return category;
	}

	public static @Nullable Integer create(@NotNull CategoryDTO category) {
		Integer result = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "insert into [category] ([name]) values (?)";
			PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, category.getName());
			if (stmt.executeUpdate() > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next())
					result = rs.getInt(1);
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to insert category. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static Integer update(@NotNull CategoryDTO category) {
		Integer result = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "update [category] set [name] = ? where [category_id] = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, category.getName());
			stmt.setInt(2, category.getId());
			stmt.executeUpdate();
			result = category.getId();
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to update category. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static boolean delete(int categoryId) {
		boolean result = false;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "delete from [category] where [category_id] = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, categoryId);
			result = stmt.executeUpdate() > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to delete category. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static boolean deleteCategory(int categoryId) {
		boolean result = false;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBUtils.getConnection();
			// First, delete or update related records in the product table
			String updateProductsSql = "DELETE FROM product WHERE category_id = ?";
			stmt = con.prepareStatement(updateProductsSql);
			stmt.setInt(1, categoryId);
			int updatedRecords = stmt.executeUpdate();

			// After updating related records, delete the category
			String deleteCategorySql = "DELETE FROM category WHERE category_id = ?";
			stmt = con.prepareStatement(deleteCategorySql);
			stmt.setInt(1, categoryId);
			int deletedRecords = stmt.executeUpdate();

			// If at least one record was deleted, set result to true
			result = deletedRecords > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to delete category. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

}
