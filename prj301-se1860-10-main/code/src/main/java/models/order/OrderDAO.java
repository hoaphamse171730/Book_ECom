package models.order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.product.ProductQuery;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import models.SortOrder;
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.orderdetail.OrderDetailDAO;
import models.orderdetail.OrderDetailDTO;
import utils.DBUtils;

public class OrderDAO {

    public static int countOrders(@NotNull OrderQuery query) {
        int total = 0;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT count(1) FROM [order] WHERE 1=1";
            if (query.getKeyword() != null)
                sql += " AND [" + query.getSearchBy() + "] LIKE ? ";

            PreparedStatement stmt = con.prepareStatement(sql);
            if (query.getKeyword() != null)
                stmt.setString(1, "%" + query.getKeyword() + "%");

            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                total = rs.getInt(1);

            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed to count orders. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return total;
    }

    public static @NotNull
    List<OrderDTO> getOrders(@NotNull OrderQuery query) {
        List<OrderDTO> list = new ArrayList<>();

        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT * FROM (" +
                    "  SELECT *, ROW_NUMBER() OVER (" +
                    "     ORDER BY [" + query.getSortBy() + "] " + query.getSortOrder() +
                    "  ) AS [row_num] FROM [order] WHERE 1=1";
            if (query.getKeyword() != null)
                sql += " AND [" + query.getSearchBy() + "] LIKE ? ";
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

            while (rs.next()) {
                int id = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                int totalPrice = rs.getInt("total_price");
                Date createAt = rs.getDate("created_at");
                Status status = EnumUtils.getEnum(Status.class,rs.getString("status"),Status.PENDING);
                List<OrderDetailDTO> orderDetails = OrderDetailDAO.getOrderDetails(id);
                OrderDTO order = new OrderDTO(
                        id,
                        userId,
                        totalPrice,
                        status,
                        createAt,
                        orderDetails
                );
                list.add(order);
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed to get orders. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

    public static @Nullable
    OrderDTO getOrder(int orderId) {
        OrderDTO order = null;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "select * from [order] where [order_id] = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("order_id");
                    int userId = rs.getInt("user_id");
                    int totalPrice = rs.getInt("total_price");
                    Date createAt = rs.getDate("created_at");
                    Status status = EnumUtils.getEnum(Status.class,rs.getString("status"),Status.PENDING);
                    List<OrderDetailDTO> orderDetails = OrderDetailDAO.getOrderDetails(id);
                    order = new OrderDTO(
                                    id,
                                    userId,
                                    totalPrice,
                                    status,
                                    createAt,
                                    orderDetails);
                }
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println(
                    "Failed to get order with id: "
                    + orderId
                    + ". Details:"
                    + ex.getMessage()
            );
            ex.printStackTrace();
        }
        return order;
    }

    public static @Nullable
    Integer createOrder(@NotNull OrderDTO order) {
        Integer returnedOrderId = null;
        int totalPrice = order
                .getOrderDetails()
                .stream()
                .mapToInt(product -> product.getPrice() * product.getAmount())
                .sum();
        try {
            Connection con = DBUtils.getConnection();
            String sql
                    = "insert into [order] ([user_id], [total_price], [status], [created_at]) values (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, order.getUserId());
            stmt.setInt(2, totalPrice);
            stmt.setString(3, order.getStatus().toString());
            stmt.setDate(4, new Date(System.currentTimeMillis()));
            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    returnedOrderId = rs.getInt(1);
                }
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed to create order. Details:" + ex.getMessage());
            ex.printStackTrace();
        }

        if (returnedOrderId == null)
            return null;

        Connection con = DBUtils.getConnection();
        try {
            con.setAutoCommit(false);

            for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
                String childrenSql = "UPDATE product SET remain = remain - ? WHERE product_id = ? AND remain >= ?;";
                PreparedStatement childrenStmt = con.prepareStatement(childrenSql);
                childrenStmt.setInt(1, orderDetail.getAmount());
                childrenStmt.setInt(2, orderDetail.getProductId());
                childrenStmt.setInt(3, orderDetail.getAmount());
                if (childrenStmt.executeUpdate() == 0) {
                    throw new SQLException("Failed to set product amount");
                }
                childrenStmt.close();
            }

            for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
                String childrenSql
                        = "insert into [order_detail] ([order_id], [product_id], [price], [amount]) values (?, ?, ?, ?)";
                PreparedStatement childrenStmt = con.prepareStatement(childrenSql);
                childrenStmt.setInt(1, returnedOrderId);
                childrenStmt.setInt(2, orderDetail.getProductId());
                childrenStmt.setInt(3, orderDetail.getPrice());
                childrenStmt.setInt(4, orderDetail.getAmount());
                if (childrenStmt.executeUpdate() == 0) {
                    throw new SQLException("Failed to insert order detail");
                }
                childrenStmt.close();
            }

			NotificationDAO.addNotification(new NotificationDTO(order.getUserId(),
					"Bạn đã đặt thành công đơn hàng #" + returnedOrderId+", xin vui lòng chờ trong quá trình xử lí", "Thông báo", false));

            con.commit();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed to insert order. Details:" + ex.getMessage());
            ex.printStackTrace();
            try {
                System.out.println("Rolling back transaction...");
                con.rollback();
                con.close();
                try {
                    con = DBUtils.getConnection();
                    String sql
                            = "delete from [order] where [order_id] = ?";
                    PreparedStatement stmt = con.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );
                    stmt.setInt(1, returnedOrderId);
                    if (stmt.executeUpdate() == 0) {
                        System.out.println("Failed to rollback order");
                    }
                    stmt.close();
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Failed to rollback order. Details:" + e.getMessage());
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return returnedOrderId;
    }

    public static boolean updateOrderStatus(
            int orderId,
            @NotNull Status status
    ) {
        boolean result = false;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "update [order] set [status] = ? where [order_id] = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, status.toString());
            stmt.setInt(2, orderId);
            result = stmt.executeUpdate() > 0;
            
            OrderDTO order = OrderDAO.getOrder(orderId);
            if (status.toString().equalsIgnoreCase("DELIVERING")) {            	
            	NotificationDAO.addNotification(new NotificationDTO(order.getUserId(),
					"Đơn hàng #" + order.getOrderId()+" đã được đóng gói và đưa đi vận chuyển", "Thông báo", false));
            } else if (status.toString().equalsIgnoreCase("COMPLETED")) {
            	NotificationDAO.addNotification(new NotificationDTO(order.getUserId(),
    				"Đơn hàng #" + order.getOrderId()+" đã được giao và thanh toán thành công", "Thông báo", false));
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed to update. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }
    
    public static boolean updateCancelledStatus(OrderDTO order,String message) {
        boolean result = false;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "update [order] set [status] = ? where [order_id] = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "CANCELLED");
            stmt.setInt(2, order.getOrderId());
            result = stmt.executeUpdate() > 0;
            
			for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
				try {
					String childrenSql = "UPDATE product SET remain = remain + ? WHERE product_id = ?;";
					PreparedStatement childrenStmt = con.prepareStatement(childrenSql);
					childrenStmt.setInt(1, orderDetail.getAmount());
					childrenStmt.setInt(2, orderDetail.getProductId());
					childrenStmt.executeUpdate();
					childrenStmt.close();
				} catch (SQLException ex) {
					System.out.println("Failed to insert order detail. Details:" + ex.getMessage());
					ex.printStackTrace();
				}
			}
			NotificationDAO.addNotification(new NotificationDTO(order.getUserId(),
					"Đơn hàng #" + order.getOrderId()+" đã bị hủy với lời nhắn: "+message, "Thông báo", false));

            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed to update. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }
    
    public static List<OrderDTO> getOrdersByUserId(int userId, String statusFilter, String sortBy, SortOrder sortOrder) {
        List<OrderDTO> list = new ArrayList<>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT * FROM [order] WHERE [user_id] = ?";
            
            if (!statusFilter.isEmpty() && !statusFilter.equals("ALL")) {
                sql += " AND [status] = '" + statusFilter + "'";
            }
            
            sql += " ORDER BY " + sortBy + " " + sortOrder.name();

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, userId);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("order_id");
                int totalPrice = rs.getInt("total_price");
                Date createAt = rs.getDate("created_at");
                Status status = EnumUtils.getEnum(Status.class, rs.getString("status"), Status.PENDING);
                List<OrderDetailDTO> orderDetails = OrderDetailDAO.getOrderDetails(id);
                OrderDTO order = new OrderDTO(id, userId, totalPrice, status, createAt, orderDetails);
                list.add(order);
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed to get orders by user ID. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }
}
