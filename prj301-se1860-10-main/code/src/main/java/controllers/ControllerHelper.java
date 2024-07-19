package controllers;

import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.user.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ControllerHelper {
    public static void processNotification(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("usersession");
        boolean anyRead = false;
        if (user != null) {
            List<NotificationDTO> notifications = NotificationDAO.getNotificationsOfUser(user.getId(), Duration.ofDays(30));
            request.setAttribute("notifications", notifications);
            anyRead = !notifications.stream().allMatch(NotificationDTO::isRead);
        }
        request.setAttribute("anyRead", anyRead);
    }
}
