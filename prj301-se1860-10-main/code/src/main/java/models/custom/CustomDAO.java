package models.custom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.author.AuthorDAO;
import models.author.AuthorDTO;
import models.image.ImageDAO;
import models.image.ImageDTO;
import models.product.ProductDTO;
import models.user.Gender;
import models.user.UserDTO;
import utils.DBUtils;

public class CustomDAO {
	
	public static List<ProductDTO> getMostSelledProducts() {
        List<ProductDTO> list = new ArrayList<>();

        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT TOP 20 p.*, COALESCE(o.tong, 0) AS total_amount FROM product p LEFT JOIN (SELECT product_id, SUM(amount) AS tong FROM order_detail GROUP BY product_id) o ON p.product_id = o.product_id ORDER BY total_amount DESC";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
					int productId = rs.getInt("product_id");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					List<ImageDTO> images = ImageDAO.getImagesOfProduct(productId);
					List<AuthorDTO> authors = AuthorDAO.getAuthorsOfProduct(productId);

					ProductDTO product = new ProductDTO(productId, 0, name, "", "",
							price, "", "", "", "", 0, 0, null, null, images,
							authors, null);
					list.add(product);
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed to get orders. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }
	
	public static List<ProductDTO> getMostSelledProductsNoImage() {
        List<ProductDTO> list = new ArrayList<>();

        try {
            Connection con = DBUtils.getConnection();
            String sql = "select top 10 * from (select pd.product_id,sum(od.amount) as 'totalAmount' from product pd\r\n"
            		+ "join order_detail od on pd.product_id = od.product_id\r\n"
            		+ "join [order] o on o.order_id = od.order_id\r\n"
            		+ "where o.status = 'COMPLETED'\r\n"
            		+ "group by pd.product_id) as tb\r\n"
            		+ "join product pd on pd.product_id = tb.product_id\r\n"
            		+ "order by totalAmount desc";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
					int productId = rs.getInt("product_id");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					int selledAmount = rs.getInt("totalAmount");

					ProductDTO product = new ProductDTO(productId, 0, name, "", "",
							price, "", "", "", "", 0, 0, null, null, null,
							null, null);
					product.setSelledAmount(selledAmount);
					list.add(product);
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed to get orders. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }
	
	public static List<UserDTO> getMostActiveCustomer() {
        List<UserDTO> list = new ArrayList<>();

        try {
            Connection con = DBUtils.getConnection();
            String sql = "select top 10 [user].user_id,[user].fullname ,sum(od.amount*od.price) as 'moneySpend' from [user]\r\n"
            		+ "join [order] o on o.user_id = [user].user_id\r\n"
            		+ "join order_detail od on od.order_id = o.order_id\r\n"
            		+ "where o.status = 'COMPLETED'\r\n"
            		+ "group by [user].user_id,[user].fullname order by moneySpend desc";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
					int userId = rs.getInt("user_id");
					String fullname = rs.getString("fullname");
					int moneySpend = rs.getInt("moneySpend");

					UserDTO user = new UserDTO(userId,"",fullname,Gender.FEMALE,"","",null,null);
					user.setMoneySpend(moneySpend);
					list.add(user);
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed to get orders. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }
}
