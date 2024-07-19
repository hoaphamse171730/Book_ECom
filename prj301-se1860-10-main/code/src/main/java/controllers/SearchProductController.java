package controllers;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.SortOrder;
import models.category.CategoryDAO;
import models.custom.pagination.PaginationDAO;
import models.custom.pagination.PaginationDTO;
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.product.ProductDAO;
import models.product.ProductDTO;
import models.product.ProductQuery;
import models.user.UserDTO;

@WebServlet(name = "SearchProducts", urlPatterns = { "/search" })
public class SearchProductController extends HttpServlet {

  protected void processRequest(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    ControllerHelper.processNotification(request);

    String keyword = request.getParameter("keyword");
    String search = request.getParameter("search");
    String sortOrder = request.getParameter("order");
    String sortBy = request.getParameter("sort");
    String categoryIdStr = request.getParameter("category");
    Integer categoryId = null;
    int page;
    try {
      page = Integer.parseInt(request.getParameter("page"));
    } catch (NumberFormatException e) {
      page = 1; // Default to page 1 if parsing fails
    }

    int limit = 30;

    ProductQuery.SearchParam searchParam = ProductQuery.SearchParam.NAME; // Default search parameter
    if (search != null) {
      try {
        searchParam = ProductQuery.SearchParam.valueOf(search.toUpperCase());
      } catch (IllegalArgumentException e) {
        request.setAttribute("error", e.getMessage());
        request
          .getRequestDispatcher("/pages/home/Home.jsp")
          .forward(request, response);
      }
    }

    ProductQuery.SortParam sortParam = ProductQuery.SortParam.PRICE; // Default sorting parameter
    if (sortBy != null) {
      try {
        sortParam = ProductQuery.SortParam.valueOf(sortBy.toUpperCase());
      } catch (IllegalArgumentException e) {
        request.setAttribute("error", e.getMessage());
        request
          .getRequestDispatcher("/pages/home/Home.jsp")
          .forward(request, response);
      }
    }

    SortOrder sortOrderEnum = "DESC".equalsIgnoreCase(sortOrder)
      ? SortOrder.DESC
      : SortOrder.ASC;

    ProductQuery query = new ProductQuery(
      searchParam,
      keyword,
      sortParam,
      sortOrderEnum,
      page,
      limit
    );

    request.setAttribute("limit", limit);
    request.setAttribute("page", page);
    request.setAttribute("categories", CategoryDAO.getCategories());
    if (categoryIdStr != null) {
      if (!categoryIdStr.isEmpty()) {
        categoryId = Integer.parseInt(categoryIdStr);
        request.setAttribute(
          "products",
          ProductDAO.getProductsByCategoryId(categoryId, query)
        );
        PaginationDTO pagination =
          (PaginationDAO.getProductsPagination(categoryId, query));
        request.setAttribute("category", CategoryDAO.getCategory(categoryId));
        request.setAttribute("pagination", pagination);
        request
          .getRequestDispatcher("/pages/product/SearchProduct.jsp")
          .forward(request, response);
        return;
      }
    } else {
      request.setAttribute("products", ProductDAO.getProducts(query));
      PaginationDTO pagination = (PaginationDAO.getProductsPagination(query));
      request.setAttribute("keyword", keyword);
      request.setAttribute("pagination", pagination);
      request
        .getRequestDispatcher("/pages/product/SearchProduct.jsp")
        .forward(request, response);
      return;
    }

    List<ProductDTO> products = ProductDAO.getProducts(query);

    request.setAttribute("products", products);
    request
      .getRequestDispatcher("/pages/product/SearchProduct.jsp")
      .forward(request, response);
  }

  @Override
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  } // </editor-fold>
}
