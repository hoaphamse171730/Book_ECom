package models.custom.pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.author.AuthorDAO;
import models.order.OrderDAO;
import org.jetbrains.annotations.NotNull;

import models.author.AuthorQuery;
import models.order.OrderQuery;
import models.product.ProductDAO;
import models.product.ProductQuery;
import models.user.UserDAO;
import models.user.UserQuery;
import utils.DBUtils;

public class PaginationDAO {

	public static @NotNull PaginationDTO getProductsPagination(@NotNull ProductQuery query) {
		int total = ProductDAO.countProducts(query);
		String itemLink = "./search?keyword="+query.getKeyword()+"&sort="+query.getSortBy()+"&order="+query.getSortOrder()+"&page=";
		return new PaginationDTO(total, 1, query.getPage(), query.getLimit(), itemLink);
	}
	
	public static @NotNull PaginationDTO getProductsPagination(int categoryId, @NotNull ProductQuery query) {
		int total = ProductDAO.countProducts(categoryId,query);
		String itemLink = "./search?category="+categoryId+"&sort="+query.getSortBy()+"&order="+query.getSortOrder()+"&page=";
		return new PaginationDTO(total, 1, query.getPage(), query.getLimit(), itemLink);
	}
	
	public static @NotNull PaginationDTO getProductsManagerPagination(@NotNull ProductQuery query) {
		int total = ProductDAO.countProducts(query);
		String itemLink = "./admin?tab=ProductManager";
		if (query.getKeyword() != null) {
			itemLink += "&keyword="+query.getKeyword();			
		}
		itemLink += "&sort="+query.getSortBy()+"&order="+query.getSortOrder()+"&page=";
		return new PaginationDTO(total, 1, query.getPage(), query.getLimit(), itemLink);
	}

	public static @NotNull PaginationDTO getOrdersPagination(@NotNull OrderQuery query) {
		int total = OrderDAO.countOrders(query);
		String itemLink = "./admin?tab=OrderManager&page=";
		return new PaginationDTO(total, 1, query.getPage(), query.getLimit(), itemLink);
	}

	public static @NotNull PaginationDTO getCustomersPagination(@NotNull UserQuery query) {
		int total = UserDAO.countUsers(query);
		return new PaginationDTO(total, 1, query.getPage(), query.getLimit(), "./admin?tab=CustomerManager&page=");
	}

	public static @NotNull PaginationDTO getAuthorsPagination(@NotNull AuthorQuery query) {
		int total = AuthorDAO.countAuthors(query);
		return new PaginationDTO(total, 1, query.getPage(), query.getLimit(), "./admin?tab=AuthorManager&page=");
	}
}
