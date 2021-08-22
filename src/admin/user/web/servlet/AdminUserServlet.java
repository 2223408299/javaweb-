package admin.user.web.servlet;

import admin.purse.service.PurseService;
import admin.user.service.AdminUserService;
import cart.service.CartItemService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import order.service.OrderService;
import pager.PageBean;
import user.domain.User;
import user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/AdminUserServlet")
public class AdminUserServlet extends BaseServlet {
    private AdminUserService userService = new AdminUserService();

    private UserService userService2 = new UserService();
    private PurseService purseService = new PurseService();
    private CartItemService cartItemService = new CartItemService();
    private OrderService orderService = new OrderService();

    /**
     * 批量删除用户
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String batchDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取cartItemIds参数
         * 2. 调用service方法完成工作
         * 3. 返回到list.jsp
         */
        String userIds = req.getParameter("userIds");
//        Object[] userIdArray = userIds.split(",");
//        for(int i = 0;i<userIdArray.length;i++){
//            if(userService2.findByUid((String) userIdArray[i])) {
//                req.setAttribute("msg", "该用户还存在收货地址，不能删除！");
//                return "f:/adminjsps/msg.jsp";
//            }else if(purseService.findByUid((String) userIdArray[i])){
//                req.setAttribute("msg", "该用户还存在钱包充值申请，不能删除！");
//                return "f:/adminjsps/msg.jsp";
//            }else if(cartItemService.findByUid((String) userIdArray[i])){
//                req.setAttribute("msg", "该用户购物车还存在商品，不能删除！");
//                return "f:/adminjsps/msg.jsp";
//            }else if(orderService.findByUid((String) userIdArray[i])){
//                req.setAttribute("msg", "该用户还存在订单，不能删除！");
//                return "f:/adminjsps/msg.jsp";
//            }
//        }
        userService.batchDelete(userIds);
//        req.setAttribute("msg", "所选中的用户成功被删除!");
//        return "f:/adminjsps/msg.jsp";
        return findAllUser(req,resp);
    }

    /**
     * 删除用户
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uid = req.getParameter("uid");
//        if(userService2.findByUid(uid)) {
//            req.setAttribute("msg", "该用户还存在收货地址，不能删除！");
//            return "f:/adminjsps/msg.jsp";
//        }else if(purseService.findByUid(uid)){
//            req.setAttribute("msg", "该用户还存在钱包充值申请，不能删除！");
//            return "f:/adminjsps/msg.jsp";
//        }else if(cartItemService.findByUid(uid)){
//            req.setAttribute("msg", "该用户购物车还存在商品，不能删除！");
//            return "f:/adminjsps/msg.jsp";
//        }else if(orderService.findByUid(uid)){
//            req.setAttribute("msg", "该用户还存在订单，不能删除！");
//            return "f:/adminjsps/msg.jsp";
//        }
        userService.delete(uid);//修改数据库的记录
//        req.setAttribute("msg", "成功删除用户!");
//        return "f:/adminjsps/msg.jsp";
        return findAllUser(req,resp);
    }

    /**
     * 批量解封封号
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String batchLock(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取cartItemIds参数
         * 2. 调用service方法完成工作
         * 3. 返回到list.jsp
         */
        String userIds = req.getParameter("userIds");
        String condition2 = req.getParameter("condition2");
        userService.batchLock(userIds, condition2);
//        String msg = "所选中的用户成功被解封!";
//        if ("lock".equals(condition)) {
//            msg = "所选中的用户成功被封号!";
//        }
//        req.setAttribute("msg", msg);
//        return "f:/adminjsps/msg.jsp";
        return findAllUser(req,resp);
    }

    /**
     * 获取当前页码
     *
     * @param req
     * @return
     */
    private int getPc(HttpServletRequest req) {
        int pc = 1;
        String param = req.getParameter("pc");
        if (param != null && !param.trim().isEmpty()) {
            try {
                pc = Integer.parseInt(param);
            } catch (RuntimeException e) {
            }
        }
        return pc;
    }

    /**
     * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
     *
     * @param req
     * @return
     */
    private String getUrl(HttpServletRequest req) {
        String url = req.getRequestURI() + "?" + req.getQueryString();
        /*
         * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
         */
        int index = url.lastIndexOf("&pc=");
        if (index != -1) {
            url = url.substring(0, index);
        }
        return url;
    }

    /**
     * 查找所有用户
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        int pc = getPc(req);
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);

        String condition = req.getParameter("condition");
        HttpSession session = req.getSession();
        if(condition == null){
            condition= (String) session.getAttribute("condition");
        }else {
            session.setAttribute("condition",condition);
        }
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<User> pb = userService.findAllUser(pc, condition);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("condition", condition);
        req.setAttribute("pb", pb);
        return "f:/adminjsps/admin/user/list.jsp";
    }

    /**
     * 按用户名称模糊查找
     *
     * @param req,resp
     * @return
     */
    public String findByUsername(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        int pc = getPc(req);
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);
        String condition = req.getParameter("condition");
        String loginname = req.getParameter("loginname");
        PageBean<User> pb = userService.findByUsername(pc, condition, loginname);
        pb.setUrl(url);
        req.setAttribute("condition", condition);
        req.setAttribute("pb", pb);
        return "f:/adminjsps/admin/user/list.jsp";
    }

    /**
     * 添加新用户
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 封装表单数据到User对象
         */
        User user = CommonUtils.toBean(req.getParameterMap(), User.class);

        userService.addUser(user);
//        req.setAttribute("msg", "成功添加用户！");
//        return "f:/adminjsps/msg.jsp";
        return findAllUser(req,resp);
    }

    /**
     * 封号与解封用户
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String updateState(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uid = req.getParameter("uid");
        String condition = req.getParameter("condition");
        userService.updateState(uid, condition);//修改数据库的记录
//        String msg = "用户成功被解封!";
//        if ("normal".equals(condition)) {
//            msg = "用户成功被封号!";
//        }
//        req.setAttribute("msg", msg);
//        return "f:/adminjsps/msg.jsp";
        return findAllUser(req,resp);
    }

    /**
     * 按uid查用户
     *
     * @param req
     * @param resp
     * @return
     */
    public String load(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{

        String uid = req.getParameter("uid");
        User user = userService.findByUid(uid);
        req.setAttribute("user", user);
        return "f:/adminjsps/admin/user/desc.jsp";
    }

    /**
     * 修改用户
     *
     * @param req
     * @param resp
     * @return
     */
    public String edit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{

        User user = CommonUtils.toBean(req.getParameterMap(), User.class);
        userService.edit(user);
//        req.setAttribute("msg", "成功修改用户!");
//        return "f:/adminjsps/msg.jsp";
        return findAllUser(req,resp);
    }
}