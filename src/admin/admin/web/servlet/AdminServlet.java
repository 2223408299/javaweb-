package admin.admin.web.servlet;

import admin.admin.domain.Admin;
import admin.admin.service.AdminService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AdminServlet")
public class AdminServlet extends BaseServlet {
    private AdminService adminService = new AdminService();

    /**
     * 登录功能
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String login(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 封装表单数据到Admin
         */
        Admin form = CommonUtils.toBean(req.getParameterMap(), Admin.class);
        Admin admin = adminService.login(form);
        if(admin == null) {
            req.setAttribute("msg", "用户名或密码错误！");
            req.setAttribute("form",form);
            return "f:/adminjsps/login.jsp";
        }
        req.getSession().setAttribute("admin", admin);
        return "r:/adminjsps/admin/main.jsp";
    }

    /**
     * 退出功能
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String quit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        req.getSession().invalidate();
        req.getSession().removeAttribute("admin");
        return "r:/adminjsps/login.jsp";
    }
}
