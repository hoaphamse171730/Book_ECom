package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.EnumUtils;

import models.user.Gender;
import models.user.Role;
import models.user.UserDAO;

@WebServlet(name = "RegisterController", urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/pages/register/Register.jsp").forward(request, response);
	}

	@SuppressWarnings("DataFlowIssue")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
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

			String fullname = request.getParameter("fullname").trim();
			if (fullname.length() < 4) {
				throw new IllegalArgumentException("Full name must be at least 4 characters");
			} else if (fullname.length() > 30) {
				throw new IllegalArgumentException("Full name must not exceed 30 characters");
			}

			Gender gender = EnumUtils.getEnum(Gender.class, request.getParameter("gender").trim().toUpperCase());
			if (gender == null) {
				throw new IllegalArgumentException("Invalid gender");
			}

			String address = request.getParameter("address").trim();
			if (address.length() < 20) {
				throw new IllegalArgumentException("Address must be at least 20 characters");
			} else if (address.length() > 150) {
				throw new IllegalArgumentException("Address must not exceed 150 characters");
			}

			String phoneNumber = request.getParameter("phoneNumber").trim();
			if (phoneNumber.length() < 10) {
				throw new IllegalArgumentException("Phone number must be at least 10 characters");
			} else if (phoneNumber.length() > 12) {
				throw new IllegalArgumentException("Phone number must not exceed 12 characters");
			}

			boolean ok = UserDAO.register(email, password, fullname, gender, address, phoneNumber, Role.USER);
			if (ok) {
				request.setAttribute("success", true);
			} else {
				throw new IllegalArgumentException("Failed to create user");
			}
		} catch (IllegalArgumentException e) {
			request.setAttribute("error", e.getMessage());
		} finally {
			request.getRequestDispatcher("/pages/register/Register.jsp").forward(request, response);
		}
	}
}
