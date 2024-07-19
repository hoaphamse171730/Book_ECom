package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.cartitem.CartItemDTO;
import models.order.OrderDAO;
import models.order.OrderDTO;
import models.order.Status;
import models.orderdetail.OrderDetailDTO;
import models.user.UserDTO;
import utils.ServletUtils;

@WebServlet("/buy")
public class BuyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<CartItemDTO> cartItemList = (List<CartItemDTO>) session.getAttribute("shopcart");
		if (cartItemList == null) return;
		List<OrderDetailDTO> orderDetails = new ArrayList<>();

		for (CartItemDTO cartItem : cartItemList) {
			orderDetails.add(new OrderDetailDTO(cartItem.getProduct().getProductId(), cartItem.getProduct().getPrice(),
					cartItem.getAmount()));
		}

		UserDTO user = (UserDTO) session.getAttribute("usersession");
		if (user == null) return;
		OrderDTO orders = new OrderDTO(user.getId(), Status.PENDING, orderDetails);
		if (OrderDAO.createOrder(orders) == null) {
			request.setAttribute("error", "Lỗi tạo order!");
			request.getRequestDispatcher("/pages/order/Order.jsp").forward(request, response);
			return;
		}
		cartItemList = null;
		session.setAttribute("shopcart", cartItemList);
		response.sendRedirect("./order");
	}

	public BuyController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
