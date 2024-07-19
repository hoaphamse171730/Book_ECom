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
import models.favorite.FavoriteDAO;
import models.favorite.FavoriteDTO;
import models.favorite.FavoriteQuery;
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.user.UserDTO;

@WebServlet(name = "FavoriteController", urlPatterns = { "/favorite" })
public class FavoriteController extends HttpServlet {

  protected void processRequest(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    HttpSession session = request.getSession();
    UserDTO user = (UserDTO) session.getAttribute("usersession");
    request.setAttribute("categories", CategoryDAO.getCategories());
    if (user == null) {
      response.sendRedirect("./login");
      return;
    }

    ControllerHelper.processNotification(request);

    String action = request.getParameter("action");
    if (action == null) {
      List<FavoriteDTO> favoriteList = FavoriteDAO.getFavorites(
        user.getId(),
        new FavoriteQuery(
          FavoriteQuery.SortParam.CREATED_AT,
          SortOrder.DESC,
          1,
          10
        )
      );
      request.setAttribute("favoriteItems", favoriteList);
      request
        .getRequestDispatcher("/pages/favorite/Favorite.jsp")
        .forward(request, response);
      return;
    } else if (action.equals("add")) {
      int productId = Integer.parseInt(request.getParameter("productId"));
      FavoriteDAO.addFavorite(new FavoriteDTO(productId, user.getId(), null));
      response.sendRedirect("./favorite");
      return;
    } else if (action.equals("delete")) {
      int productId = Integer.parseInt(request.getParameter("productId"));
      FavoriteDAO.removeFavorite(user.getId(), productId);
      response.sendRedirect("./favorite");
      return;
    }

    request
      .getRequestDispatcher("/pages/favorite/Favorite.jsp")
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
