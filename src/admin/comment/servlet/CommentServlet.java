package admin.comment.servlet;

import admin.comment.domain.Comment;
import admin.comment.service.CommentService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import drug.domain.Drug;
import drug.service.DrugService;
import order.domain.OrderItem;
import order.service.OrderService;
import pager.PageBean;
import user.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet("/CommentServlet")
public class CommentServlet extends BaseServlet {
    private CommentService commentService = new CommentService();
    private DrugService drugService = new DrugService();
    private OrderService orderService = new OrderService();

    /**
     * 按评论状态和评论内容查询评论
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findBycontent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int pc = getPc(req);
        String url = getUrl(req);
        int state = Integer.parseInt(req.getParameter("state"));
        String content = req.getParameter("content");
        PageBean<Comment> pb = commentService.findBycontent(state, content, pc);
        pb.setUrl(url);
        req.setAttribute("state", state);
        req.setAttribute("pb", pb);
        return "f:/adminjsps/admin/comment/list.jsp";
    }

    /**
     * 批量删除评论
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String batchDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String commentIds = req.getParameter("commentIds");
        String state = req.getParameter("state");
        System.out.println(state);
        commentService.batchDelete(commentIds);
        return findCommentBystate(req, resp);
    }

    /**
     * 删除评论
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String deleteComment(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String commentId = req.getParameter("commentId");
        String drugId = req.getParameter("drugId");
        commentService.deleteComment(commentId,drugId);
        return findCommentBystate(req, resp);
    }

    /**
     * 按订单Id查询评论
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findCommentByOrderItemId(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderItemId = req.getParameter("orderItemId");
        Comment comment = commentService.findCommentByOrderItemId(orderItemId);
        req.setAttribute("comment", comment);
        return "f:/adminjsps/admin/comment/commentDesc.jsp";
    }

    /**
     * 按评论Id查询评论
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findCommentByCommentId(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String commentId = req.getParameter("commentId");
        Comment comment = commentService.findCommentByCommentId(commentId);
        req.setAttribute("comment", comment);
        return "f:/adminjsps/admin/comment/commentDesc.jsp";
    }

    /**
     * 回复评论
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String replyComment(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> map = req.getParameterMap();
        Comment comment = CommonUtils.toBean(map, Comment.class);
        comment.setReplyTime(String.format("%tF %<tT", new Date()));
        commentService.replyComment(comment);
        return findCommentBystate(req, resp);
    }

    /**
     * 获取所有评论
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public String findCommentBystate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int pc = getPc(req);
        String url = getUrl(req);
        int state = Integer.parseInt(req.getParameter("state"));
        PageBean<Comment> pb = commentService.findCommentBystate(state, pc);
        pb.setUrl(url);
        req.setAttribute("state", state);
        req.setAttribute("pb", pb);
        return "f:/adminjsps/admin/comment/list.jsp";
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
    /*
     * http://localhost:8080/goods/BookServlet?methed=findByCategory&cid=xxx&pc=3
     * /goods/BookServlet + methed=findByCategory&cid=xxx&pc=3
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
     * 添加评论
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void addComment(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String orderItemId = req.getParameter("orderItemId");
        Map map = req.getParameterMap();
        Comment comment = CommonUtils.toBean(map, Comment.class);
        OrderItem orderItem = orderService.load(orderItemId);
        Drug drug = CommonUtils.toBean(map, Drug.class);
        User user = (User) req.getSession().getAttribute("sessionUser");
        comment.setCommentId(CommonUtils.uuid());
        comment.setCommentTime(String.format("%tF %<tT", new Date()));
        comment.setOrderItem(orderItem);
        comment.setState(0);
        comment.setUser(user);
        comment.setDrug(drug);
        commentService.addComment(comment, orderItemId);
        req.getRequestDispatcher("/DrugServlet?method=load&drugid=" + drug.getDrugId()).forward(req, resp);
    }
}
