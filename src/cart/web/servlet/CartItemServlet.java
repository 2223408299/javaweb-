package cart.web.servlet;

import cart.domain.CartItem;
import cart.service.CartItemService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import drug.domain.Drug;
import user.domain.myAddress;
import user.domain.User;
import user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/CartItemServlet")
public class CartItemServlet extends BaseServlet {
    private CartItemService cartItemService = new CartItemService();
    private UserService userService = new UserService();

    /**
     * ajax加入购物车库存检验
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String ajaxValidateInventories(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String drugId = req.getParameter("drugId");
        boolean b = cartItemService.ajaxValidateInventories(quantity,drugId);
        /*
         * 3. 发给客户端
         */
        resp.getWriter().print(b);
        return null;
    }

    /**
     * 加载多个CartItem
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String loadCartItems(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取cartItemIds参数
         */
        String cartItemIds = req.getParameter("cartItemIds");
        double total = Double.parseDouble(req.getParameter("total"));
        /*
         * 2. 通过service得到List<CartItem>
         */
        List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
        /*
         * 3. 保存，然后转发到/cart/showitem.jsp
         */
        req.setAttribute("cartItemList", cartItemList);
        req.setAttribute("total", total);
        req.setAttribute("cartItemIds", cartItemIds);
        /*
         * 获取收货地址
         */
        User user = (User)req.getSession().getAttribute("sessionUser");
        String uid = user.getUid();
        List<myAddress> myAddressList = userService.myaddress(uid);
        req.setAttribute("myAddressList", myAddressList);
        return "f:/jsps/cart/showitem.jsp";
    }

    /**
     * 修改商品数量
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String updateQuantity(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String cartItemId = req.getParameter("cartItemId");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        CartItem cartItem = cartItemService.updateQuantity(cartItemId, quantity);

        // 给客户端返回一个json对象
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
        sb.append(",");
        sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
        sb.append("}");

        resp.getWriter().print(sb);
        return null;
    }

    /**
     * 批量删除功能
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
        String cartItemIds = req.getParameter("cartItemIds");
        cartItemService.batchDelete(cartItemIds);
        return myCart(req, resp);
    }

    /**
     * 添加购物车条目
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 封装表单数据到CartItem(bid, quantity)
         */
        Map map = req.getParameterMap();
        CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
        Drug drug = CommonUtils.toBean(map, Drug.class);
        User user = (User)req.getSession().getAttribute("sessionUser");
        cartItem.setDrug(drug);
        cartItem.setUser(user);

        /*
         * 2. 调用service完成添加
         */
        cartItemService.add(cartItem);
        /*
         * 3. 查询出当前用户的所有条目，转发到list.jsp显示
         */
        return myCart(req, resp);
    }

    /**
     * 我的购物车
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String myCart(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 得到uid
         */
        User user = (User)req.getSession().getAttribute("sessionUser");
        String uid = user.getUid();
        /*
         * 2. 通过service得到当前用户的所有购物车条目
         */
        List<CartItem> cartItemLIst = cartItemService.myCart(uid);
        /*
         * 3. 保存起来，转发到/cart/list.jsp
         */
        req.setAttribute("cartItemList", cartItemLIst);
        return "f:/jsps/cart/list.jsp";
    }

}
