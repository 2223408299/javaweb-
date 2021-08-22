package admin.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "AdminLoginFilter", urlPatterns = { "/adminjsps/admin/*","/AdminCategoryServlet"})
public class AdminLoginFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		Object admin = req.getSession().getAttribute("admin");
		if(admin == null) {
			request.setAttribute("msg", "您还没有登录，请登录！");
			request.getRequestDispatcher("/adminjsps/login.jsp").forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}  

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}
}
