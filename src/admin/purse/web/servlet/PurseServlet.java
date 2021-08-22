package admin.purse.web.servlet;

import admin.purse.domain.Purse;
import admin.purse.service.PurseService;
import cn.itcast.servlet.BaseServlet;
import pager.PageBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/PurseServlet")
public class PurseServlet extends BaseServlet {
    private PurseService purseService = new PurseService();


    /**
     * 批量删除已经处理的充值申请
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
        String purseIds = req.getParameter("purseIds");
        purseService.batchDelete(purseIds);
//        req.setAttribute("msg", "所选中的充值申请成功被删除!");
//        return "f:/adminjsps/msg.jsp";
        return findAllPurse(req,resp);
    }

    /**
     * 批量处理申请
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String batchCheck(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取cartItemIds参数
         * 2. 调用service方法完成工作
         * 3. 返回到list.jsp
         */
        String purseIds = req.getParameter("purseIds");
        purseService.batchCheck(purseIds);
//        req.setAttribute("msg", "所选中的充值申请成功被处理!");
//        return "f:/adminjsps/msg.jsp";
        return findAllPurse(req,resp);
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
     * 按用户ID查询
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findPurseByUid(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        int pc = getPc(req);
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);

        HttpSession session = req.getSession();
        String uid = req.getParameter("uid");
        if(uid == null){
            uid= (String) session.getAttribute("uid");
        }else {
            session.setAttribute("uid",uid);
        }
        String condition = req.getParameter("condition");
        if(condition == null){
            condition= (String) session.getAttribute("condition");
        }else {
            session.setAttribute("condition",condition);
        }
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Purse> pb = purseService.findPurseByUid(pc, condition,uid);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("condition", condition);
        req.setAttribute("pb", pb);
        return "f:/adminjsps/admin/purse/list.jsp";
    }

    /**
     * 查找所有钱包充值管理
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllPurse(HttpServletRequest req, HttpServletResponse resp)
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
        PageBean<Purse> pb = purseService.findAllUser(pc, condition);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("condition", condition);
        req.setAttribute("pb", pb);
        return "f:/adminjsps/admin/purse/list.jsp";
    }

    /**
     * 管理充值申请
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String updateState(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String purseId = req.getParameter("purseId");
        purseService.updateState(purseId);
//        req.setAttribute("msg", "审核处理成功!");
//        return "f:/adminjsps/msg.jsp";
        return findAllPurse(req,resp);
    }

    /**
     * 删除充值申请
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String purseId = req.getParameter("purseId");
        purseService.delete(purseId);
//        req.setAttribute("msg", "删除成功!");
//        return "f:/adminjsps/msg.jsp";
        return findAllPurse(req,resp);
    }
}
