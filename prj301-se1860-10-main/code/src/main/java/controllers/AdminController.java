package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.SortOrder;
import models.author.AuthorDAO;
import models.author.AuthorDTO;
import models.author.AuthorQuery;
import models.category.CategoryDAO;
import models.category.CategoryDTO;
import models.custom.CustomDAO;
import models.custom.pagination.PaginationDAO;
import models.custom.pagination.PaginationDTO;
import models.custom.statistic.StatisticDAO;
import models.image.ImageDAO;
import models.image.ImageDTO;
import models.order.OrderDAO;
import models.order.OrderDTO;
import models.order.OrderQuery;
import models.order.Status;
import models.product.ProductDAO;
import models.product.ProductDTO;
import models.product.ProductQuery;
import models.user.Role;
import models.user.UserDAO;
import models.user.UserDTO;
import models.user.UserQuery;
import utils.DateTimeUtils;
import utils.RegexUtils;
import utils.ServletUtils;

@WebServlet(name = "AdminController", urlPatterns = { "/admin" })
public class AdminController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("usersession");
		
		if (user == null) {
			response.sendRedirect(ServletUtils.getBasePath(request) + "/login");
		} else if (user.getRole() == Role.USER) {
			response.sendRedirect(ServletUtils.getBasePath(request));
		}
		request.setAttribute("user", user);
		
		String tab = request.getParameter("tab");
		String action = request.getParameter("action");
		int page = 1;
		int limit = 20;
		try {
			page = Integer.parseInt(request.getParameter("page"));
			limit = Integer.parseInt(request.getParameter("limit"));
		} catch (Exception e) {
		}
		int productId = -1;
		int orderId = -1;
		int userId = -1;
		int categoryId = -1;
		int authorId = -1;
		int staffId = -1;
		if (tab == null || tab.equals("Dashboard")) {
			List<Map<Object, Object>> list = StatisticDAO.getTotalIncomeStatistic();
			List<Map<Object, Object>> listPie = StatisticDAO.getIncomeByCategory();
			request.setAttribute("totalIncomeStatistic", list);
			request.setAttribute("totalIncomeByCategory", listPie);
			request.setAttribute("tongDoanhThu", StatisticDAO.getTongDoanhThu()/1000000000);
			request.setAttribute("tongDonHang", StatisticDAO.getTongDonHang());
			request.setAttribute("tongSanPham", StatisticDAO.getTongSanPham());
			request.setAttribute("tonKho", StatisticDAO.getTonKho());
			request.setAttribute("mostSelledProduct", CustomDAO.getMostSelledProductsNoImage());
			request.setAttribute("mostPotentialCustomer", CustomDAO.getMostActiveCustomer());
			request.getRequestDispatcher("/pages/admin/dashboard/Dashboard.jsp").forward(request, response);
		} else if (tab.equals("ProductManager")) {
			String keyword = request.getParameter("keyword");
			String search = request.getParameter("search");
			String sortOrder = request.getParameter("order");
			String sortBy = request.getParameter("sort");
			
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {
				page = 1; // Default to page 1 if parsing fails
			}

			ProductQuery.SearchParam searchParam = ProductQuery.SearchParam.NAME; // Default search parameter
			if (search != null) {
				try {
					searchParam = ProductQuery.SearchParam.valueOf(search.toUpperCase());
				} catch (IllegalArgumentException e) {
					request.setAttribute("error", e.getMessage());
					request.getRequestDispatcher("/pages/home/Home.jsp").forward(request, response);
				}
			}

			ProductQuery.SortParam sortParam = ProductQuery.SortParam.PRODUCT_ID; // Default sorting parameter
			if (sortBy != null) {
				try {
					sortParam = ProductQuery.SortParam.valueOf(sortBy.toUpperCase());
				} catch (IllegalArgumentException e) {
					request.setAttribute("error", e.getMessage());
					request.getRequestDispatcher("/pages/home/Home.jsp").forward(request, response);
				}
			}

			SortOrder sortOrderEnum = "ASC".equalsIgnoreCase(sortOrder) ? SortOrder.ASC : SortOrder.DESC;
			
			ProductQuery productQuery = new ProductQuery(searchParam, keyword, sortParam, sortOrderEnum, page, limit);
			
			if (action == null) {
				action = "";
			} else if (action.equals("update")) {
				try {
					productId = Integer.parseInt(request.getParameter("productId"));
				} catch (Exception e) {
				}
				ProductDTO product = ProductDAO.getProduct(productId);
				if (product != null) {
					request.setAttribute("updateProduct", product);
					if (product.getImages() != null)
						request.setAttribute("images", product.getImages().stream().map(ImageDTO::getUrl)
								.collect(Collectors.joining("\n")));
				}
			}

			boolean toModify = action.equals("update") || action.equals("create");

			request.setAttribute("categories", CategoryDAO.getCategories());
			request.setAttribute("action", action);
			request.setAttribute("tab", tab);
			if (toModify) {
				request.setAttribute("authors", AuthorDAO.getAuthorIdNames());
			} else {
				request.setAttribute("products", ProductDAO.getProducts(productQuery));
				PaginationDTO pagination = (PaginationDAO.getProductsManagerPagination(productQuery));
				request.setAttribute("pagination", pagination);
			}
			request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
		} else if (tab.equals("OrderManager")) {
			OrderQuery orderQuery = new OrderQuery(null, null, OrderQuery.SortParam.ORDER_ID, SortOrder.DESC, page,
					limit);
			if (action == null) {
				action = "";
			}
			request.setAttribute("action", action);
			request.setAttribute("tab", tab);
			if (action.isEmpty()) {
				PaginationDTO pagination = (PaginationDAO.getOrdersPagination(orderQuery));
				request.setAttribute("orders", OrderDAO.getOrders(orderQuery));
				request.setAttribute("pagination", pagination);
				request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
			} else if (action.equals("detail")) {
				try {
					orderId = Integer.parseInt(request.getParameter("orderId"));
				} catch (Exception e) {
				}
				request.setAttribute("order", OrderDAO.getOrder(orderId));
				request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
			}
		} else if (tab.equals("CustomerManager")) {
			UserQuery customerQuery = new UserQuery(null, null, UserQuery.SortParam.USER_ID, SortOrder.DESC, page,
					limit);
			if (action == null) {
				action = "";
			}
			request.setAttribute("action", action);
			request.setAttribute("tab", tab);
			if (action.isEmpty()) {
				PaginationDTO pagination = (PaginationDAO.getCustomersPagination(customerQuery));
				request.setAttribute("pagination", pagination);
				action = "";
				request.setAttribute("customers", UserDAO.getUsers(customerQuery));
				request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
			} else if (action.equals("detail")) {
				try {
					userId = Integer.parseInt(request.getParameter("userId"));
				} catch (Exception e) {
				}
				System.out.println("userid " + userId);
				request.setAttribute("customer", UserDAO.getUser(userId));
				request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
			}
		} else if (tab.equals("CategoryManager")) {
			if (action == null) {
				action = "";
			}
			request.setAttribute("action", action);
			request.setAttribute("tab", tab);
			if (action.isEmpty()) {
				request.setAttribute("categoryies", CategoryDAO.getCategories());
				request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
			} else if (action.equals("update")) {
				try {
					categoryId = Integer.parseInt(request.getParameter("categoryId"));

				} catch (Exception e) {
				}
				request.setAttribute("categoryUpdate", CategoryDAO.getCategory(categoryId));
				request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
			} else if (action.equals("create")) {
				request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
			} else if (action.equals("delete")) {
				try {
					categoryId = Integer.parseInt(request.getParameter("categoryId"));
				} catch (Exception e) {
				}
				CategoryDAO.delete(categoryId);
				response.sendRedirect("./admin?tab=CategoryManager");
			}
		} else if (tab.equals("AuthorManager")) {
			AuthorQuery authorQuery = new AuthorQuery(null, null, AuthorQuery.SortParam.AUTHOR_ID, SortOrder.DESC, page,
					limit);
			if (action == null) {
				action = "";
			}
			request.setAttribute("action", action);
			request.setAttribute("tab", tab);
			if (action.isEmpty()) {
				PaginationDTO pagination = (PaginationDAO.getAuthorsPagination(authorQuery));
				request.setAttribute("pagination", pagination);
				request.setAttribute("authors", AuthorDAO.getAuthors(authorQuery));
				request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
			} else if (action.equals("detail")) {
				try {
					userId = Integer.parseInt(request.getParameter("userId"));
				} catch (Exception e) {
				}
				request.setAttribute("customer", UserDAO.getUser(userId));
				request.getRequestDispatcher("/pages/Admin.jsp").forward(request, response);
			} else if (action.equals("update")) {
				try {
					authorId = Integer.parseInt(request.getParameter("authorId"));
				} catch (Exception e) {
				}
				request.setAttribute("authorUpdate", AuthorDAO.getAuthorById(authorId));
				request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
			} else if (action.equals("create")) {
				request.getRequestDispatcher("/pages/admin/Admin.jsp").forward(request, response);
			} else if (action.equals("delete")) {
				try {
					authorId = Integer.parseInt(request.getParameter("authorId"));
				} catch (Exception e) {
				}
				AuthorDAO.deleteAuthor(authorId);
				response.sendRedirect("./admin?tab=AuthorManager");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String tab = request.getParameter("tab");
		String action = request.getParameter("action");
		String name = request.getParameter("name");
		String shortDescription = request.getParameter("shortDescription");
		String description = request.getParameter("description");
		String manufacturer = request.getParameter("manufacturer");
		String publisher = request.getParameter("publisher");
		String size = request.getParameter("size");
		String format = "";
		if (tab.equals("ProductManager")) {
			if (request.getParameter("format").equals("1")) {
				format = "Bìa cứng";
			} else {
				format = "Bìa mềm";
			}
		}
		int categoryId = 1;
		int pages = 1;
		int remain = 1;
		int price = 1;
		Date publishDay = Date.valueOf("2000-01-01");
		try {
			categoryId = Integer.parseInt(request.getParameter("categoryId"));
			pages = Integer.parseInt(request.getParameter("pages"));
			remain = Integer.parseInt(request.getParameter("remain"));
			price = Integer.parseInt(request.getParameter("price"));
			publishDay = DateTimeUtils.stringToDate(request.getParameter("publishDay"));
		} catch (Exception e) {
		}
		int returnedId = 1;
		if (tab.equals("ProductManager")) {
			if (action.equals("create")) {
				returnedId = ProductDAO.create(new ProductDTO(-1, categoryId, name, shortDescription, description, price,
						manufacturer, publisher, size, format, pages, remain, publishDay, new Date(System.currentTimeMillis())));
				List<String> images = RegexUtils.getImagesLink(request.getParameter("images"));
				for (String image:images) {
					ImageDAO.createImage(new ImageDTO(0, returnedId, image, new Date(System.currentTimeMillis())));
				}
				int author1 = -1,author2 = -1,author3 = -1,author4 = -1;
				try {
					author1 = Integer.parseInt(request.getParameter("author1"));
					if (AuthorDAO.getAuthorById(author1) != null) {
						AuthorDAO.addAuthorsOfProduct(returnedId, author1);
					}
					author2 = Integer.parseInt(request.getParameter("author2"));
					if (AuthorDAO.getAuthorById(author2) != null) {
						AuthorDAO.addAuthorsOfProduct(returnedId, author2);
					}
					author3 = Integer.parseInt(request.getParameter("author3"));
					if (AuthorDAO.getAuthorById(author3) != null) {
						AuthorDAO.addAuthorsOfProduct(returnedId, author3);
					}
					author4 = Integer.parseInt(request.getParameter("author4"));
					if (AuthorDAO.getAuthorById(author4) != null) {
						AuthorDAO.addAuthorsOfProduct(returnedId, author4);
					}
				} catch (Exception e) {

				}
			} else if (action.equals("update")) {
				int productId = Integer.parseInt(request.getParameter("productId"));
				ProductDAO.update(new ProductDTO(productId, categoryId, name, shortDescription,
						description, price, manufacturer, publisher, size, format, pages, remain, publishDay, new Date(System.currentTimeMillis())));
				returnedId = productId;
			}
			response.sendRedirect("./admin?tab=ProductManager&action=update&productId=" + returnedId);
			return;
		} else if (tab.equals("CategoryManager")) {
			if (action.equals("create")) {
				returnedId = CategoryDAO.create(new CategoryDTO(name));
			} else if (action.equals("update")) {
				returnedId = CategoryDAO.update(new CategoryDTO(categoryId, name));
			}
			response.sendRedirect("./admin?tab=CategoryManager&action=update&categoryId=" + returnedId);
			return;
		} else if (tab.equals("AuthorManager")) {
			int authorId = -1;
			try {
				authorId = Integer.parseInt(request.getParameter("authorId"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (action.equals("create")) {
				returnedId = AuthorDAO.addAuthor(new AuthorDTO(name));
			} else if (action.equals("update")) {
				returnedId = AuthorDAO.updateAuthor(new AuthorDTO(authorId, name));
			}
			response.sendRedirect("./admin?tab=AuthorManager&action=update&authorId=" + returnedId);
			return;
		}  else if (tab.equals("OrderManager")) {
			int orderId = -1;
			String status = request.getParameter("status");
			try {
				orderId = Integer.parseInt(request.getParameter("orderId"));
			} catch (Exception e) {
			}
			if (status.equals("CANCELLED")) {
				String message = request.getParameter("message");
				if (message.isEmpty()) {
					message = "Shipper giao 3 lần đều không nghe máy";
				}
				System.out.println("message: "+message);
				OrderDTO order = OrderDAO.getOrder(orderId);
				OrderDAO.updateCancelledStatus(order,message);								
			} else {
				OrderDAO.updateOrderStatus(orderId, Status.valueOf(status));				
			}
			response.sendRedirect("./admin?tab=OrderManager&action=detail&orderId=" + orderId);
			return;
		}   else if (tab.equals("CustomerManager")) {
			int userId = -1;
			String role = request.getParameter("role");
			try {
				userId = Integer.parseInt(request.getParameter("id"));
				UserDTO user = UserDAO.getUser(userId);
				user.setRole(Role.valueOf(role));
				UserDAO.update(user);
			} catch (Exception e) {
			}
			response.sendRedirect("./admin?tab=CustomerManager&action=detail&userId=" + userId);
			return;
		}
//        System.out.println("name " + name);
//        System.out.println("cate " + categoryId);
//        System.out.println("price " + price);
//        System.out.println("remain " + remain);
//        System.out.println("shortDescription " + shortDescription);
//        System.out.println("description " + description);
//        System.out.println("publishDay " + publishDay);
//        System.out.println("manufacturer " + manufacturer);
//        System.out.println("publisher " + publisher);
//        System.out.println("size " + size);
//        System.out.println("format " + format);
//        System.out.println("pages " + pages);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
