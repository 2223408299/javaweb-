package order.web.servlet;

import cart.domain.CartItem;
import cart.service.CartItemService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import order.domain.Order;
import order.domain.OrderItem;
import order.service.OrderService;
import pager.PageBean;
import user.domain.myAddress;
import user.domain.User;
import user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();
    private CartItemService cartItemService = new CartItemService();
    private UserService userService = new UserService();

    /**
     * 批量支付准备
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String batchPaymentPre(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String orderItemIds = req.getParameter("orderItemIds");
        BigDecimal totals = orderService.batchLoad(orderItemIds);
//        System.out.println(totals);
        User user = (User)req.getSession().getAttribute("sessionUser");
        User mypurse = userService.mypurse(user.getUid());
        req.setAttribute("totals", totals);
        req.setAttribute("mypurse", mypurse);
        req.setAttribute("orderItemIds", orderItemIds);
        return "/jsps/order/orderPayment.jsp";
    }

    /**
     * 批量确认收货
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
        orderService.batchupdateStatus(orderItemIds,4);
        return findByStatus(req,resp);
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
        return findByStatus(req,resp);
    }

    /**
     * 删除订单
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delect(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderItemId = req.getParameter("orderItemId");
        orderService.delect(orderItemId);
        return findByStatus(req,resp);
    }

    /**
     * 修改订单状态
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
        orderService.updateStatus(orderItemId,status);
        return findByStatus(req,resp);
    }

    /**
     * 支付订单（批量支付）
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String payment3(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderItemIds = req.getParameter("orderItemIds");
        double totals = Double.parseDouble(req.getParameter("totals"));
//        Order order = orderService.loadOrder(orderId);
        User user = (User)req.getSession().getAttribute("sessionUser");
        User mypurse = userService.mypurse(user.getUid());
        if(totals>user.getBalance()){
            req.setAttribute("msg", "余额不足请先充值!");
            req.setAttribute("mypurse", mypurse);
            req.setAttribute("totals", totals);
            return "/jsps/order/orderPayment.jsp";
        }
        orderService.batchupdateStatus(orderItemIds,2);
        userService.payment(user.getUid(),totals);
//        req.setAttribute("status",2);
        return findByStatus(req,resp);
//        return myOrders(req,resp);
    }

    /**
     * 支付订单(按order支付)
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String payment2(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        Order order = orderService.loadOrder(orderId);
        User user = (User)req.getSession().getAttribute("sessionUser");
        User mypurse = userService.mypurse(user.getUid());
        if(order.getTotal()>user.getBalance()){
            req.setAttribute("msg", "余额不足请先充值!");
            req.setAttribute("mypurse", mypurse);
            req.setAttribute("order", order);
            return "/jsps/order/orderPayment.jsp";
        }
        for(OrderItem orderItem : order.getOrderItemList()){
            orderService.updateStatus(orderItem.getOrderItemId(),2);
        }
        userService.payment(user.getUid(),order.getTotal());
//        req.setAttribute("status","2");
        return findByStatus(req,resp);
//        return myOrders(req,resp);
    }

    /**
     * 支付订单(按orderItem支付)
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String payment(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderItemId = req.getParameter("orderItemId");
        OrderItem orderItem = orderService.load(orderItemId);
        User user = (User)req.getSession().getAttribute("sessionUser");
        User mypurse = userService.mypurse(user.getUid());
        if(orderItem.getSubtotal()>mypurse.getBalance()){
            req.setAttribute("msg", "余额不足请先充值!");
            req.setAttribute("mypurse", mypurse);
            req.setAttribute("orderItem", orderItem);
            return "/jsps/order/orderPayment.jsp";
        }
        orderService.updateStatus(orderItemId,2);
        userService.payment(user.getUid(),orderItem.getSubtotal());
        return findByStatus(req,resp);
//        return myOrders(req,resp);
    }

    /**
     * 支付准备
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String paymentPre(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String orderItemId = req.getParameter("orderItemId");
        OrderItem orderItem = orderService.load(orderItemId);
        User user = (User)req.getSession().getAttribute("sessionUser");
        User mypurse = userService.mypurse(user.getUid());
        req.setAttribute("orderItem", orderItem);
        req.setAttribute("mypurse", mypurse);
        return "/jsps/order/orderPayment.jsp";
    }

    /**
     * 加载订单
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
        return "/jsps/order/orderDesc.jsp";
    }

    /**
     * 生成订单
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String createOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取所有购物车条目的id，查询之
         */
        String cartItemIds = req.getParameter("cartItemIds");
        List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
        if(cartItemList.size() == 0) {
            req.setAttribute("code", "error");
            req.setAttribute("msg", "您没有选择要购买的图书，不能下单！");
            return "f:/jsps/msg.jsp";
        }
        /*
         * 2. 创建Order
         */
        Order order = new Order();
        order.setOid(CommonUtils.uuid());//设置主键
        order.setOrdertime(String.format("%tF %<tT", new Date()));//下单时间
        order.setOrderItemtotal(cartItemList.size());
        /*
        按用户选择的addressId查找收货信息
         */
        String addressId = req.getParameter("addressId");
        myAddress myAddress = userService.findByaddressId(addressId);
        order.setMyAddress(myAddress);//设置收货地址信息
        User owner = (User)req.getSession().getAttribute("sessionUser");
//        order.setOwner(owner);//设置订单所有者

        BigDecimal total = new BigDecimal("0");
        for(CartItem cartItem : cartItemList) {
            total = total.add(new BigDecimal(cartItem.getSubtotal() + ""));
        }
        order.setTotal(total.doubleValue());//设置总计

        /*
         * 3. 创建List<OrderItem>
         * 一个CartItem对应一个OrderItem
         */
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for(CartItem cartItem : cartItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(CommonUtils.uuid());//设置主键
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItem.setStatus(1);//未发货
            orderItem.setOwner(owner);
            orderItem.setDrug(cartItem.getDrug());
            orderItem.setOrder(order);
            orderItemList.add(orderItem);
        }
        order.setOrderItemList(orderItemList);

        /*
         * 4. 调用service完成添加
         */
        orderService.createOrder(order);

        // 删除购物车条目
        cartItemService.batchDelete(cartItemIds);
        /*
         * 5. 保存订单，转发到ordersucc.jsp
         */
        User user = (User)req.getSession().getAttribute("sessionUser");
        User mypurse = userService.mypurse(user.getUid());
        req.setAttribute("mypurse", mypurse);
        req.setAttribute("order", order);
        return "f:/jsps/order/orderPayment.jsp";
    }

    /**
     * 我的订单
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String myOrders(HttpServletRequest req, HttpServletResponse resp)
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
         * 3. 从当前session中获取User
         */
        User user = (User)req.getSession().getAttribute("sessionUser");

        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<OrderItem> pb = orderService.myOrders(user.getUid(), pc);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("st","0");
        return "f:/jsps/order/list.jsp";
    }

    /**
     * 按订单状态查询我的订单
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
        User user = (User)req.getSession().getAttribute("sessionUser");
        int status = Integer.parseInt(req.getParameter("status"));
        PageBean<OrderItem> pb = orderService.findByStatusAndUid(user.getUid(),status, pc);
        pb.setUrl(url);
        req.setAttribute("st", status);
        req.setAttribute("pb", pb);
        return "f:/jsps/order/list.jsp";
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
