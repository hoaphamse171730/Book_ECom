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
import models.category.CategoryDAO;
import models.feedback.FeedbackDAO;
import models.feedback.FeedbackDTO;
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.product.ProductDAO;
import models.user.UserDTO;

@WebServlet(name = "FeedbackController", urlPatterns = { "/feedback" })
public class FeedbackController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();
    UserDTO user = (UserDTO) session.getAttribute("usersession");
    if (user == null) {
      response.sendRedirect("./login");
      return;
    }
    ControllerHelper.processNotification(request);
    Integer productId = null;
    Integer orderId = null;
    try {
      productId = Integer.parseInt(request.getParameter("productId"));
      orderId = Integer.parseInt(request.getParameter("orderId"));
    } catch (Exception e) {
      response.sendRedirect("./login");
      return;
    }
    request.setAttribute("categories", CategoryDAO.getCategories());
    request.setAttribute(
      "feedback",
      FeedbackDAO.getFeedback(productId, user.getId())
    );
    request.setAttribute("product", ProductDAO.getProduct(productId));
    request.setAttribute("orderId", orderId);
    request
      .getRequestDispatcher("/pages/feedback/Feedback.jsp")
      .forward(request, response);
  }

  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();
    UserDTO user = (UserDTO) session.getAttribute("usersession");
    Integer productId = Integer.parseInt(request.getParameter("productId"));
    String feedbackIdStr = request.getParameter("feedbackId");
    Integer orderId = Integer.parseInt(request.getParameter("orderId"));
    String content = (request.getParameter("content"));
    Integer rating = Integer.parseInt(request.getParameter("rating"));
    if (!feedbackIdStr.isEmpty()) {
      Integer feedbackId = Integer.parseInt(feedbackIdStr);
      FeedbackDAO.updateFeedback(
        new FeedbackDTO(
          feedbackId,
          user.getId(),
          productId,
          rating,
          content,
          null
        )
      );
    } else {
      FeedbackDAO.createFeedback(
        new FeedbackDTO(0, user.getId(), productId, rating, content, null)
      );
    }
    request.setAttribute("orderId", orderId);
    request
      .getRequestDispatcher("/pages/feedback/ThanksForFeedback.jsp")
      .forward(request, response);
    //		System.out.println("productID "+productId);
    //		System.out.println("content "+content);
    //		System.out.println("rating "+rating);
    //		System.out.println("user "+user.getId());
  }

  public FeedbackController() {
    super();
    // TODO Auto-generated constructor stub
  }
}
