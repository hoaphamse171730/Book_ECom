package models.orderdetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import models.product.ProductDAO;
import models.product.ProductDTO;
import utils.DBUtils;

public class OrderDetailDAO {

	public static @NotNull List<OrderDetailDTO> getOrderDetails(int orderId) {
		List<OrderDetailDTO> list = new ArrayList<>();

		try {
			Connection con = DBUtils.getConnection();
			String sql = "select * from [order_detail] where [order_id] = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, orderId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("order_id");
				int productId = rs.getInt("product_id");
				int price = rs.getInt("price");
				int amount = rs.getInt("amount");
				ProductDTO productDTO = ProductDAO.getProduct(productId);

				OrderDetailDTO orderDetail = new OrderDetailDTO(id, productId, price, amount, productDTO);
				list.add(orderDetail);
			}

			con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get order detail. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}
}
