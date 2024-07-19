package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.user.UserDAO;
import models.user.UserDTO;

@WebServlet(name = "LoginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/pages/login/Login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email").trim();
			if (email.length() < 5) {
				throw new IllegalArgumentException("Email must be at least 5 characters");
			} else if (email.length() > 48) {
				throw new IllegalArgumentException("Email must not exceed 48 characters");
			}

			String password = request.getParameter("password").trim();
			if (password.length() < 5) {
				throw new IllegalArgumentException("Password must be at least 5 characters");
			} else if (password.length() > 48) {
				throw new IllegalArgumentException("Password must not exceed 48 characters");
			}

			Integer userId = UserDAO.login(email, password);
			if (userId == null) {
				throw new IllegalArgumentException("Wrong email or password");
			}

			UserDTO user = UserDAO.getUser(userId);
			assert user != null;
			HttpSession session = request.getSession(true);
			session.setAttribute("usersession", user);
			response.sendRedirect("./");
			System.out.printf("Login success: %s (#%s)%n", user.getName(), user.getId());
		} catch (IllegalArgumentException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/pages/login/Login.jsp").forward(request, response);
		}
	}
}
