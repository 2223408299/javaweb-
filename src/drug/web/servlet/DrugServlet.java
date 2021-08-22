package drug.web.servlet;


import admin.comment.domain.Comment;
import admin.comment.service.CommentService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import drug.domain.Drug;
import drug.service.DrugService;
import order.domain.OrderItem;
import order.service.OrderService;
import pager.PageBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/DrugServlet")
public class DrugServlet extends BaseServlet {
    private DrugService drugService = new DrugService();
    private CommentService commentService = new CommentService();
    private OrderService orderService = new OrderService();

    /**
     * 按销量查询热门商品
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findHotDrug2(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Drug> drugList = drugService.findHotDrug2();
        req.setAttribute("size",drugList.size());
        req.setAttribute("drugList",drugList);
        return "f:/jsps/body.jsp";
    }

    /**
     * 按销量查询热门商品
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findHotDrug(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Drug> drugList = drugService.findHotDrug();
        req.setAttribute("drugList",drugList);
        return "f:/jsps/drugList.jsp";
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
     * 按分类查
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
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
         * 3. 获取查询条件，本方法就是cid，即分类的id
         */
        String cid = req.getParameter("cid");
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Drug> pb = drugService.findByCategory(cid, pc,"putaway");
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition",cid);
        return "f:/jsps/drug/list.jsp";
    }

    /**
     * 按药名模糊查
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByDrugname(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        int pc = getPc(req);
        /*
         * 3. 获取查询条件，本方法就是cid，即分类的id
         */
        String drugname = req.getParameter("drugname");
        HttpSession session = req.getSession();
        if(drugname==null){
            drugname=(String) session.getAttribute("drugname");
        }else {
            session.setAttribute("drugname",drugname);
        }
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Drug> pb = drugService.findBydrugName(drugname, pc,"putaway");
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition",drugname);
        return "f:/jsps/drug/list.jsp";
    }

    /**
     * 按drugid查询
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String load(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String drugid = req.getParameter("drugid");
        String status = req.getParameter("status");
        String orderItemId = req.getParameter("orderItemId");
        if(orderItemId!=null){
            OrderItem orderItem = orderService.load(orderItemId);
            req.setAttribute("orderItem", orderItem);
        }
        Drug drug = drugService.load(drugid);
        int pc = getPc(req);
        String url = getUrl(req);
        PageBean<Comment> pb = commentService.findCommentByDrugid(drugid,pc);
        pb.setUrl(url);
        if(status!=null){
            req.setAttribute("status", status);
        }
        req.setAttribute("pb", pb);
        req.setAttribute("drug", drug);//保存到req中
        return "f:/jsps/drug/drugDesc.jsp";//转发到drugDesc.jsp
    }

    /**
     * 降序
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findBydrugDesc(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        int pc = getPc(req);
        /*
         * 3. 获取查询条件，本方法就是cid，即分类的id
         */
//        String condition = String.valueOf(req.getParameter("condition"));
//        condition =new String(condition.getBytes("iso8859-1"),"UTF-8");
//        System.out.println(condition);
        HttpSession session = req.getSession();
        String condition = (String) session.getAttribute("condition");
        String condition2 = req.getParameter("condition2");
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Drug> pb = drugService.findBydrugDesc(condition, condition2,pc,"putaway");
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition",condition);

        return "f:/jsps/drug/list.jsp";
    }

    /**
     * 升序
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByAsc(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        int pc = getPc(req);
        /*
         * 3. 获取查询条件，本方法就是cid，即分类的id
         */
        HttpSession session = req.getSession();
        String condition = (String) session.getAttribute("condition");
        String condition2 = req.getParameter("condition2");
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Drug> pb = drugService.findByAsc(condition, condition2,pc,"putaway");
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition",condition);

        return "f:/jsps/drug/list.jsp";
    }

    /**
     * 多条件组合查询
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
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
         * 3. 获取查询条件，本方法就是cid，即分类的id
         */
        Drug criteria = CommonUtils.toBean(req.getParameterMap(), Drug.class);
        /*
         *获取当前查询的前条件
         */
        HttpSession session = req.getSession();
        if(criteria.getMinprice()==0.0 && criteria.getMaxprice()==0.0 && criteria.getSign()==null && criteria.getShipAddress()==null){
            criteria = (Drug) session.getAttribute("criteria");
        }else{
            session.setAttribute("criteria",criteria);
        }
        String condition = req.getParameter("condition");
        if(condition==null){
            condition=(String) session.getAttribute("condition");
        }else {
            session.setAttribute("condition",condition);
        }
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Drug> pb = drugService.findByCombination(criteria, pc,condition,"putaway");
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition",condition);
        return "f:/jsps/drug/list.jsp";
    }
}
