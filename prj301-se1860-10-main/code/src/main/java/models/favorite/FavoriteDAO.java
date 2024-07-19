package models.favorite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import models.product.ProductDAO;
import utils.DBUtils;

public class FavoriteDAO {

    public static @NotNull List<FavoriteDTO> getFavorites(int userId, @NotNull FavoriteQuery query) {
        List<FavoriteDTO> list = new ArrayList<>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT * FROM (" +
                    "  SELECT *, ROW_NUMBER() OVER (" +
                    "     ORDER BY [" + query.getSortBy() + "] " + query.getSortOrder() +
                    "  ) AS [row_num] FROM [favorite] WHERE [user_id] = " + userId;
            sql +=  ") AS [num_tb] " +
                    "WHERE [row_num] >= ? AND [row_num] <= ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, query.getStartRow());
            stm.setInt(2, query.getEndRow());
            ResultSet rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                FavoriteDTO favorite = new FavoriteDTO(
                        rs.getInt("product_id"),
                        rs.getInt("user_id"),
                        rs.getDate("created_at")
                );
                favorite.setProduct(ProductDAO.getProduct(rs.getInt("product_id")));
                list.add(favorite);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in getting favourites. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

    public static boolean addFavorite(@NotNull FavoriteDTO favorite) {
        boolean ok = false;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "INSERT INTO [favorite] ([user_id], [product_id], [created_at]) VALUES (?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, favorite.getUserId());
            stm.setInt(2, favorite.getProductId());
            stm.setDate(3, new Date(System.currentTimeMillis()));
            ok = stm.executeUpdate() > 0;
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in adding favourite. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return ok;
    }

    public static boolean removeFavorite(int userId, int productId) {
        boolean ok = false;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "DELETE FROM [favorite] WHERE [user_id] = ? AND [product_id] = ? ";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, productId);
            ok = stm.executeUpdate() > 0;
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in removing favourites. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return ok;
    }
}
