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
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.user.UserDTO;

@WebServlet("/notification")
public class NotificationController extends HttpServlet {

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
    request.setAttribute("categories", CategoryDAO.getCategories());
    request
      .getRequestDispatcher("/pages/notification/Notification.jsp")
      .forward(request, response);
  }

  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    HttpSession session = request.getSession();
    UserDTO user = (UserDTO) session.getAttribute("usersession");
    if (user == null) {
      response.sendRedirect("./login");
      return;
    }
    int notificationId = Integer.parseInt(
      request.getParameter("notificationId")
    );

    NotificationDAO.markNotificationRead(notificationId);
    request.setAttribute("categories", CategoryDAO.getCategories());
    response.sendRedirect("./notification");
  }

  public NotificationController() {
    super();
    // TODO Auto-generated constructor stub
  }
}
