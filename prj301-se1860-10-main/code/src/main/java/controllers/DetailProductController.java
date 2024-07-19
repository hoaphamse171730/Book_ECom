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
import models.feedback.FeedbackDAO;
import models.feedback.FeedbackQuery;
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.product.ProductDAO;
import models.user.UserDTO;

@WebServlet(urlPatterns = { "/product" })
public class DetailProductController extends HttpServlet {

  protected void processRequest(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    ControllerHelper.processNotification(request);
    FeedbackQuery feedbackQuery = new FeedbackQuery(
      null,
      null,
      FeedbackQuery.SortParam.CREATED_AT,
      SortOrder.DESC,
      1,
      20
    );
    int productId = Integer.parseInt(request.getParameter("id"));
    request.setAttribute("categories", CategoryDAO.getCategories());
    request.setAttribute("currentProduct", ProductDAO.getProduct(productId));
    request.setAttribute(
      "feedbacks",
      FeedbackDAO.getFeedbacksOfProduct(productId, feedbackQuery)
    );

    request
      .getRequestDispatcher("/pages/product/DetailProduct.jsp")
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
