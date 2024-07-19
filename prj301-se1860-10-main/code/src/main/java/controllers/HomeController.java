package controllers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.SortOrder;
import models.category.CategoryDAO;
import models.custom.CustomDAO;
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.product.ProductDAO;
import models.product.ProductQuery;
import models.user.UserDTO;

@WebServlet(name = "HomeController", urlPatterns = { "/" })
public class HomeController extends HttpServlet {

  private static final Cache<String, List<?>> cache;

  static {
    cache =
      CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).build();
  }

  protected void processRequest(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    HttpSession session = request.getSession();
    UserDTO user = (UserDTO) session.getAttribute("usersession");

    try {
      request.setAttribute(
        "tieuThuyet",
        cache.get(
          "tieuThuyet",
          () ->
            ProductDAO.getProductsByCategoryId(
              1,
              new ProductQuery(
                null,
                null,
                ProductQuery.SortParam.NAME,
                SortOrder.DESC,
                1,
                20
              )
            )
        )
      );
      request.setAttribute(
        "tacPhamKinhDien",
        cache.get(
          "tacPhamKinhDien",
          () ->
            ProductDAO.getProductsByCategoryId(
              5,
              new ProductQuery(
                null,
                null,
                ProductQuery.SortParam.NAME,
                SortOrder.DESC,
                1,
                20
              )
            )
        )
      );
      request.setAttribute(
        "kinhDi",
        cache.get(
          "kinhDi",
          () ->
            ProductDAO.getProductsByCategoryId(
              16,
              new ProductQuery(
                null,
                null,
                ProductQuery.SortParam.NAME,
                SortOrder.DESC,
                1,
                20
              )
            )
        )
      );
      request.setAttribute(
        "tranhTruyen",
        cache.get(
          "tranhTruyen",
          () ->
            ProductDAO.getProductsByCategoryId(
              9,
              new ProductQuery(
                null,
                null,
                ProductQuery.SortParam.NAME,
                SortOrder.DESC,
                1,
                20
              )
            )
        )
      );
      request.setAttribute(
        "mostSelledProducts",
        cache.get("mostSelledProducts", CustomDAO::getMostSelledProducts)
      );
      request.setAttribute(
        "categories",
        cache.get("categories", CategoryDAO::getCategories)
      );
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    ControllerHelper.processNotification(request);
    request
      .getRequestDispatcher("/pages/home/Home.jsp")
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
  public String getServletInfo() {
    return "Short description";
  } // </editor-fold>
}
