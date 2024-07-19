package filters;

import models.user.Role;
import models.user.UserDTO;
import utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    private RequestDispatcher defaultRequestDispatcher;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.defaultRequestDispatcher =
                filterConfig.getServletContext().getNamedDispatcher("default");
    }

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        UserDTO user = (UserDTO) session.getAttribute("usersession");
        if (user == null) {
            ((HttpServletResponse) response).sendRedirect(ServletUtils.getBasePath((HttpServletRequest) request) + "/login");
        } else if (user.getRole() == Role.USER) {
            ((HttpServletResponse) response).sendRedirect(ServletUtils.getBasePath((HttpServletRequest) request));
        } else if (user.getRole() == Role.STAFF && Objects.equals(request.getParameter("tab"), "CustomerManager")) {
            ((HttpServletResponse) response).sendRedirect(ServletUtils.getBasePath((HttpServletRequest) request));
        } else {
            chain.doFilter(request, response);
        }
    }
}