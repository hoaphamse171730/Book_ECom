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
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.user.Gender;
import models.user.UserDAO;
import models.user.UserDTO;

@WebServlet(name = "ProfileController", urlPatterns = { "/profile" })
public class ProfileController extends HttpServlet {

  protected void processRequest(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    // Support Unicode for request
    request.setCharacterEncoding("UTF-8");

    HttpSession session = request.getSession();
    UserDTO user = (UserDTO) session.getAttribute("usersession");

    if (user == null) {
      response.sendRedirect("./login");
      return;
    }

    ControllerHelper.processNotification(request);

    boolean isProfileUpdated = false;
    if ("POST".equalsIgnoreCase(request.getMethod())) {
      // Update profile comes from a POST request

      // Update user details
      String fullName = request.getParameter("fullName");
      String password = request.getParameter("password");
      String confirmPassword = request.getParameter("confirmPassword");
      String gender = request.getParameter("gender");
      String address = request.getParameter("address");
      String phoneNumber = request.getParameter("phoneNumber");

      if (fullName != null && !fullName.isEmpty()) user.setName(fullName);
      if (gender != null && !gender.isEmpty()) user.setGender(
        Gender.valueOf(gender.toUpperCase())
      );
      if (address != null && !address.isEmpty()) user.setAddress(address);
      if (phoneNumber != null && !phoneNumber.isEmpty()) user.setPhoneNumber(
        phoneNumber
      );

      // Perform user profile update without password
      isProfileUpdated = UserDAO.update(user);

      // Only proceed to update the password if it's provided and valid
      if (password != null && !password.isEmpty()) {
        if (password.equals(confirmPassword) && password.length() >= 5) {
          UserDAO.updatePassword(user.getId(), password);
          request.setAttribute(
            "updateSuccess",
            "Profile and Password Update successfully"
          );
        } else {
          if (!password.equals(confirmPassword)) {
            request.setAttribute(
              "updateError",
              "Password and confirm password do not match."
            );
          } else {
            request.setAttribute(
              "updateError",
              "Password must be at least 5 characters long."
            );
          }
        }
      } else if (isProfileUpdated) {
        request.setAttribute("updateSuccess", "Profile updated successfully.");
      }

      session.setAttribute("usersession", user);
    }

    // If there was an attempt to update the profile but it failed
    if (!isProfileUpdated && "POST".equalsIgnoreCase(request.getMethod())) {
      request.setAttribute(
        "updateError",
        "Failed to update profile. Please try again."
      );
    }

    request.setAttribute("user", user);
    request
      .getRequestDispatcher("/pages/profile/Profile.jsp")
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
