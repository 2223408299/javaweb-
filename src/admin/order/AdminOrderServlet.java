package admin.order;

import cn.itcast.servlet.BaseServlet;
import order.domain.OrderItem;
import order.service.OrderService;
import pager.PageBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    /**
     * 批量发货
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String batchUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderItemIds = req.getParameter("orderItemIds");
        orderService.batchupdateStatus(orderItemIds, 3);
        return findByStatus(req, resp);
    }

    /**
     * 批量删除已经取消的订单
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String batchDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderItemIds = req.getParameter("orderItemIds");
        orderService.batchDelete(orderItemIds);
        return findByStatus(req, resp);
    }

    /**
     * 加载订单
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String load(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderItemId = req.getParameter("orderItemId");
        OrderItem orderItem = orderService.load(orderItemId);
        req.setAttribute("orderItem", orderItem);
        return "f:/adminjsps/admin/order/desc.jsp";
    }

    /**
     * 修改订单状态
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String updateStatus(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderItemId = req.getParameter("orderItemId");
        int status = Integer.parseInt(req.getParameter("status"));
        orderService.updateStatus(orderItemId, status);
        return findByStatus(req, resp);
    }

    /**
     * 按用户ID或者订单编号查询
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findOrderByUidOrOrderItemId(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int pc = getPc(req);
        String url = getUrl(req);
        String uid = req.getParameter("uid");
        PageBean<OrderItem> pb = orderService.findOrderByUidOrOrderItemId(uid, pc);
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/adminjsps/admin/order/list.jsp";
    }

    /**
     * 按状态查询
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByStatus(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int pc = getPc(req);
        String url = getUrl(req);
        int status = Integer.parseInt(req.getParameter("status"));
        PageBean<OrderItem> pb = orderService.findByStatus(status, pc);
        pb.setUrl(url);
        req.setAttribute("st", status);
        req.setAttribute("pb", pb);
        return "f:/adminjsps/admin/order/list.jsp";
    }

    /**
     * 查看所有订单
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        int pc = getPc(req);
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);

        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<OrderItem> pb = orderService.findAllOrder(pc);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/adminjsps/admin/order/list.jsp";
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
}
