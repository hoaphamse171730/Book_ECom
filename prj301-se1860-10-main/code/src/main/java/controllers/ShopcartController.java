package controllers;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.cartitem.CartItemDTO;
import models.category.CategoryDAO;
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.product.ProductDAO;
import models.user.UserDTO;

@WebServlet(name = "Shopcart", urlPatterns = { "/shopcart" })
public class ShopcartController extends HttpServlet {

  protected void processRequest(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ServletException, IOException {
    HttpSession session = request.getSession();
    List<CartItemDTO> cartItemList = (List<CartItemDTO>) session.getAttribute(
      "shopcart"
    );
    request.setAttribute("categories", CategoryDAO.getCategories());
    UserDTO user = (UserDTO) session.getAttribute("usersession");

    if (user == null) {
      response.sendRedirect("./login");
      return;
    }

    ControllerHelper.processNotification(request);

    String action = request.getParameter("action");
    if (action == null) {
      if (cartItemList != null) {
        request.setAttribute("cartItems", cartItemList);
        request.setAttribute(
          "totalAmount",
          cartItemList.stream().mapToInt(CartItemDTO::getAmount).sum()
        );
        request.setAttribute(
          "totalPrice",
          cartItemList
            .stream()
            .mapToDouble(item -> item.getProduct().getPrice() * item.getAmount()
            )
            .sum()
        );
      }
      request
        .getRequestDispatcher("/pages/shopcart/Shopcart.jsp")
        .forward(request, response);
    } else if (action.equals("add")) {
      int productId = Integer.parseInt(request.getParameter("productId"));
      int amount = Integer.parseInt(request.getParameter("amount"));
      if (cartItemList == null) {
        cartItemList = new ArrayList<>();
      }
      boolean exist = cartItemList
        .stream()
        .anyMatch(item -> item.getProduct().getProductId() == productId);
      if (exist) {
        Optional<CartItemDTO> existItem = cartItemList
          .stream()
          .filter(item -> item.getProduct().getProductId() == productId)
          .findFirst();
        existItem.ifPresent(cartItemDTO -> cartItemDTO.setAmount(amount));
      } else {
        cartItemList.add(
          new CartItemDTO(ProductDAO.getProduct(productId), amount)
        );
      }
      session.setAttribute("shopcart", cartItemList);
      response.sendRedirect("./shopcart");
    } else if (action.equals("delete")) {
      int productId = Integer.parseInt(request.getParameter("productId"));
      Optional<CartItemDTO> deleteItem = cartItemList
        .stream()
        .filter(item -> item.getProduct().getProductId() == productId)
        .findFirst();
      if (deleteItem.isPresent()) {
        cartItemList.remove(deleteItem.get());
      }
      session.setAttribute("shopcart", cartItemList);
      response.sendRedirect("./shopcart");
    }
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
