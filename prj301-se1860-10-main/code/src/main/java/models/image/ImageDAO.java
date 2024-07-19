package models.image;

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

import utils.DBUtils;

public class ImageDAO {

	public static @NotNull List<ImageDTO> getImagesOfProduct(int productId) {
		List<ImageDTO> list = new ArrayList<>();

		try {
			Connection con = DBUtils.getConnection();
			String sql = "select * from [image] where [product_id] = ? order by [created_at] desc";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, productId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int imageId = rs.getInt("image_id");
				String url = rs.getString("url");
				Date createdAt = rs.getDate("created_at");
				ImageDTO image = new ImageDTO(imageId, productId, url, createdAt);
				list.add(image);
			}

			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get images of product. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}

	public static @Nullable Integer createImage(@NotNull ImageDTO image) {
		Integer result = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "insert into [image] ([product_id], [url], [created_at]) values (?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, image.getProductId());
			stmt.setString(2, image.getUrl());
			stmt.setDate(3, new Date(System.currentTimeMillis()));
			if (stmt.executeUpdate() > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
				}
			}

			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to create image. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static boolean deleteImage(int imageId) {
		boolean result = false;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "delete from [image] where [image_id] = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, imageId);
			result = stmt.executeUpdate() > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to delete image. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}
}
