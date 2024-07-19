package models.product;

import models.author.AuthorDAO;
import models.author.AuthorDTO;
import models.image.ImageDAO;
import models.image.ImageDTO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

	public static int countProducts(@NotNull ProductQuery query) {
		return countProducts(null, query);
	}

	public static int countProducts(@Nullable Integer categoryId, @NotNull ProductQuery query) {
		int total = 0;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT count(1) FROM [product] WHERE 1=1";
			if (query.getKeyword() != null)
				sql += " AND [" + query.getSearchBy() + "] LIKE ? ";
			if (categoryId != null)
				sql += " AND [category_id] = " + categoryId;

			PreparedStatement stmt = con.prepareStatement(sql);
			if (query.getKeyword() != null)
				stmt.setString(1, "%" + query.getKeyword() + "%");

			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				total = rs.getInt(1);

			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to count products. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return total;
	}

	public static @NotNull List<ProductDTO> getProducts(@NotNull ProductQuery query) {
		return getProductsByCategoryId(null, query);
	}

	public static @NotNull List<ProductDTO> getProductsByCategoryId(@Nullable Integer categoryId, @NotNull ProductQuery query) {
		List<ProductDTO> list = new ArrayList<>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT * FROM (" +
					"  SELECT *, ROW_NUMBER() OVER (" +
					"     ORDER BY [" + query.getSortBy() + "] " + query.getSortOrder() +
					"  ) AS [row_num] FROM [product] WHERE 1=1";
			if (query.getKeyword() != null)
				sql += " AND [" + query.getSearchBy() + "] LIKE ? ";
			if (categoryId != null)
				sql += " AND [category_id] = " + categoryId;
			sql +=  ") AS [num_tb] " +
					"WHERE [row_num] >= ? AND [row_num] <= ?";
			System.out.println(sql);
			long time = System.currentTimeMillis();

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
					int productId = rs.getInt("product_id");
					int categoryID = rs.getInt("category_id");
					String name = rs.getString("name");
					String shortDescription = rs.getString("short_description");
					String description = rs.getString("description");
					int price = rs.getInt("price");
					String manufacturer = rs.getString("manufacturer");
					String publisher = rs.getString("publisher");
					String size = rs.getString("size");
					String format = rs.getString("format");
					int pages = rs.getInt("pages");
					int remain = rs.getInt("remain");
					Date publishDay = rs.getDate("publish_day");
					Date createdAt = rs.getDate("created_at");
					List<ImageDTO> images = ImageDAO.getImagesOfProduct(productId);
					List<AuthorDTO> authors = AuthorDAO.getAuthorsOfProduct(productId);

					ProductDTO product = new ProductDTO(productId, categoryID, name, shortDescription, description,
							price, manufacturer, publisher, size, format, pages, remain, publishDay, createdAt, images,
							authors, null);
					list.add(product);
				}
			}
			con.close();
			System.out.println("Took " + (System.currentTimeMillis() - time) + "ms");
		} catch (SQLException ex) {
			System.out.println("Failed to get products. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}

	public static @Nullable ProductDTO getProduct(int productId) {
		ProductDTO product = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "select * from [product] where [product_id] = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, productId);
			ResultSet rs = stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					int categoryId = rs.getInt("category_id");
					String name = rs.getString("name");
					String shortDescription = rs.getString("short_description");
					String description = rs.getString("description");
					int price = rs.getInt("price");
					String manufacturer = rs.getString("manufacturer");
					String publisher = rs.getString("publisher");
					String size = rs.getString("size");
					String format = rs.getString("format");
					int pages = rs.getInt("pages");
					int remain = rs.getInt("remain");
					Date publishDay = rs.getDate("publish_day");
					Date createdAt = rs.getDate("created_at");
					List<ImageDTO> images = ImageDAO.getImagesOfProduct(productId);
					List<AuthorDTO> authors = AuthorDAO.getAuthorsOfProduct(productId);

					product = new ProductDTO(productId, categoryId, name, shortDescription, description, price,
							manufacturer, publisher, size, format, pages, remain, publishDay, createdAt, images,
							authors, null);
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get product with id: " + productId + ". Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return product;
	}

	public static @Nullable Integer create(@NotNull ProductDTO product) {
		Integer result = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "insert into [product] ([category_id],[name],[price],[remain],[short_description],[description],[publish_day],[manufacturer],[publisher],[size],[format],[pages],[created_at]) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, product.getCategoryId());
			stmt.setString(2, product.getName());
			stmt.setInt(3, product.getPrice());
			stmt.setInt(4, product.getRemain());
			stmt.setString(5, product.getShortDescription());
			stmt.setString(6, product.getDescription());
			stmt.setDate(7, product.getPublishDay());
			stmt.setString(8, product.getManufacturer());
			stmt.setString(9, product.getPublisher());
			stmt.setString(10, product.getSize());
			stmt.setString(11, product.getFormat());
			stmt.setInt(12, product.getPages());
			stmt.setDate(13, product.getCreatedAt());
			if (stmt.executeUpdate() > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
				}
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to insert product. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static boolean update(@NotNull ProductDTO product) {
		boolean result = false;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "update product set [category_id] = ?, [name] = ?,[price] = ?,[remain] = ?,[short_description] = ?,[description] = ?,[publish_day] = ?,[manufacturer] = ?,[publisher] = ?,[size] = ?,[format] = ?,[pages] = ? where [product_id] = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, product.getCategoryId());
			stmt.setString(2, product.getName());
			stmt.setInt(3, product.getPrice());
			stmt.setInt(4, product.getRemain());
			stmt.setString(5, product.getShortDescription());
			stmt.setString(6, product.getDescription());
			stmt.setDate(7, product.getPublishDay());
			stmt.setString(8, product.getManufacturer());
			stmt.setString(9, product.getPublisher());
			stmt.setString(10, product.getSize());
			stmt.setString(11, product.getFormat());
			stmt.setInt(12, product.getPages());
			stmt.setInt(13, product.getProductId());
			result = stmt.executeUpdate() > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to update product. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

}
