package admin.drug;

import cart.service.CartItemService;
import category.domain.Category;
import category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import drug.domain.Drug;
import drug.service.DrugService;
import order.service.OrderService;
import pager.PageBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/AdminDrugServlet")
public class AdminDrugServlet extends BaseServlet {
    private DrugService drugService = new DrugService();
    private CategoryService categoryService = new CategoryService();
    private CartItemService cartItemService = new CartItemService();
    private OrderService orderService = new OrderService();

    /**
     * 批量删除功能
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
        String drugIds = req.getParameter("drugIds");
        Object[] drugIdArray = drugIds.split(",");
        for (int i = 0; i < drugIdArray.length; i++) {
            /*
             * 删除图片
             */
            Drug drug = drugService.load((String) drugIdArray[i]);
            String savepath = this.getServletContext().getRealPath("/");//获取真实的路径
            new File(savepath, drug.getImage_b()).delete();//删除文件
//            if(cartItemService.findByDrugId((String) drugIdArray[i])) {
//                req.setAttribute("msg", "该商品还存在被用户添加到购物车，不能删除！");
//                return "f:/adminjsps/msg.jsp";
//            }else if(orderService.findByDrugId((String) drugIdArray[i])){
//                req.setAttribute("msg", "该商品还存在订单中，不能删除！");
//                return "f:/adminjsps/msg.jsp";
//            }
        }
        drugService.batchDelete(drugIds);
//        req.setAttribute("msg", "删除药品成功！");
//        return "f:/adminjsps/msg.jsp";
        return findAllDrug(req, resp);
    }

    /**
     * 删除药品
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String drugId = req.getParameter("drugId");
//        if(cartItemService.findByDrugId(drugId)) {
//            req.setAttribute("msg", "该商品还存在被用户添加到购物车，不能删除！");
//            return "f:/adminjsps/msg.jsp";
//        }else if(orderService.findByDrugId(drugId)){
//            req.setAttribute("msg", "该商品还存在订单中，不能删除！");
//            return "f:/adminjsps/msg.jsp";
//        }
        //        /*
//         * 删除图片
//         */
        Drug drug = drugService.load(drugId);
        String savepath = this.getServletContext().getRealPath("/");//获取真实的路径
        System.out.println(savepath);
        new File(savepath, drug.getImage_b()).delete();//删除文件
        drugService.delete(drugId);//删除数据库的记录
//
//        req.setAttribute("msg", "删除药品成功！");
//        return "f:/adminjsps/msg.jsp";
        return findAllDrug(req, resp);

    }

    /**
     * 批量下架药品
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String batchSoldout(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取cartItemIds参数
         * 2. 调用service方法完成工作
         * 3. 返回到list.jsp
         */
        String drugIds = req.getParameter("drugIds");
        drugService.batchOpdrug(drugIds, 0);
//        req.setAttribute("msg", "所选的药品成功下架！");
//        return "f:/adminjsps/msg.jsp";
        return findAllDrug(req, resp);
    }

    /**
     * 批量上架药品
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String batchPutaway(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取cartItemIds参数
         * 2. 调用service方法完成工作
         * 3. 返回到list.jsp
         */
        String drugIds = req.getParameter("drugIds");
        drugService.batchOpdrug(drugIds, 1);
//        req.setAttribute("msg", "所选的药品成功上架！");
//        return "f:/adminjsps/msg.jsp";
        return findAllDrug(req, resp);
    }

    /**
     * 上下架药品
     */
    public String updateState(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String drugId = req.getParameter("drugId");
        String condition3 = req.getParameter("condition3");
        drugService.updateState(drugId, condition3);//修改数据库的记录
//        String msg = "药品成功上架!";
//        if ("putaway".equals(condition3)) {
//            msg = "药品成功下架!";
//        }
//        req.setAttribute("msg", msg);
//        return "f:/adminjsps/msg.jsp";
        return findAllDrug(req, resp);
    }


    /**
     * 修改药品
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String edit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 把表单数据封装到Book对象中
         * 2. 封装cid到Category中
         * 3. 把Category赋给Book
         * 4. 调用service完成工作
         * 5. 保存成功信息，转发到msg.jsp
         */
        Map map = req.getParameterMap();
        Drug drug = CommonUtils.toBean(map, Drug.class);
        Category category = CommonUtils.toBean(map, Category.class);
        drug.setCategory(category);

        drugService.edit(drug);
//        req.setAttribute("msg", "修改药品信息成功！");
//        return "f:/adminjsps/msg.jsp";
        return findAllDrug(req, resp);
    }

    /**
     * 加载药品
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String load(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取bid，得到Book对象，保存之
         */
        String drugId = req.getParameter("drugId");
        Drug drug = drugService.load(drugId);
        req.setAttribute("drug", drug);

        /*
         * 2. 获取所有一级分类，保存之
         */
        req.setAttribute("parents", categoryService.findParents());
        /*
         * 3. 获取当前图书所属的一级分类下所有2级分类
         */
        String pid = drug.getCategory().getParent().getCid();
        req.setAttribute("children", categoryService.findChildren(pid));

        /*
         * 4. 转发到desc.jsp显示
         */
        return "f:/adminjsps/admin/drug/desc.jsp";
    }

    /**
     * 添加药品：第一步
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addPre(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取所有一级分类，保存之
         * 2. 转发到add.jsp，该页面会在下拉列表中显示所有一级分类
         */
        List<Category> parents = categoryService.findParents();
        req.setAttribute("parents", parents);
        return "f:/adminjsps/admin/drug/add.jsp";
    }

    public String ajaxFindChildren(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取pid
         * 2. 通过pid查询出所有2级分类
         * 3. 把List<Category>转换成json，输出给客户端
         */
        String pid = req.getParameter("pid");
        List<Category> children = categoryService.findChildren(pid);
        String json = toJson(children);
        resp.getWriter().print(json);
        return null;
    }

    // {"cid":"fdsafdsa", "cname":"fdsafdas"}
    private String toJson(Category category) {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
        sb.append(",");
        sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
        sb.append("}");
        return sb.toString();
    }

    // [{"cid":"fdsafdsa", "cname":"fdsafdas"}, {"cid":"fdsafdsa", "cname":"fdsafdas"}]
    private String toJson(List<Category> categoryList) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < categoryList.size(); i++) {
            sb.append(toJson(categoryList.get(i)));
            if (i < categoryList.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 显示所有分类
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findCategoryAll(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 通过service得到所有的分类
         * 2. 保存到request中，转发到left.jsp
         */
        List<Category> parents = categoryService.findAll();
        req.setAttribute("parents", parents);
        return "f:/adminjsps/admin/drug/listcate.jsp";
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
     * 查找所有药品
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllDrug(HttpServletRequest req, HttpServletResponse resp)
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
        判断是否上下架
         */
        String condition3 = req.getParameter("condition3");
        HttpSession session = req.getSession();
        if (condition3 == null) {
            condition3 = (String) session.getAttribute("condition3");
        } else {
            session.setAttribute("condition3", condition3);
        }
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Drug> pb = drugService.findAllDrug(pc, condition3);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition3", condition3);
        req.setAttribute("condition", "findAllDrug");
        List<Category> parents = categoryService.findAll();
        req.setAttribute("parents", parents);
        return "f:/adminjsps/admin/drug/list.jsp";

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
        String condition3 = req.getParameter("condition3");
        String cid = req.getParameter("cid");
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Drug> pb = drugService.findByCategory(cid, pc, condition3);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition3", condition3);
        req.setAttribute("condition", cid);
        List<Category> parents = categoryService.findAll();
        req.setAttribute("parents", parents);
        return "f:/adminjsps/admin/drug/list.jsp";
    }

    /**
     * 按药名模糊查
     *
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
        String condition3 = req.getParameter("condition3");
        String drugname = req.getParameter("drugname");
        HttpSession session = req.getSession();
        if (drugname == null) {
            drugname = (String) session.getAttribute("drugname");
        } else {
            session.setAttribute("drugname", drugname);
        }
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Drug> pb = drugService.findBydrugName(drugname, pc, condition3);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition3", condition3);
        req.setAttribute("condition", drugname);
        List<Category> parents = categoryService.findAll();
        req.setAttribute("parents", parents);
        return "f:/adminjsps/admin/drug/list.jsp";
    }

    /**
     * 降序
     *
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
        String condition3 = req.getParameter("condition3");
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
        PageBean<Drug> pb = drugService.findBydrugDesc(condition, condition2, pc, condition3);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition3", condition3);
        req.setAttribute("condition", condition);
        List<Category> parents = categoryService.findAll();
        req.setAttribute("parents", parents);
        return "f:/adminjsps/admin/drug/list.jsp";
    }

    /**
     * 升序
     *
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
        String condition3 = req.getParameter("condition3");
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
        PageBean<Drug> pb = drugService.findByAsc(condition, condition2, pc, condition3);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition3", condition3);
        req.setAttribute("condition", condition);
        List<Category> parents = categoryService.findAll();
        req.setAttribute("parents", parents);
        return "f:/adminjsps/admin/drug/list.jsp";
    }

    /**
     * 多条件组合查询
     *
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
        String condition3 = req.getParameter("condition3");
        Drug criteria = CommonUtils.toBean(req.getParameterMap(), Drug.class);
        /*
         *获取当前查询的前条件
         */
        HttpSession session = req.getSession();
        if (criteria.getMinprice() == 0.0 && criteria.getMaxprice() == 0.0 && criteria.getSign() == null && criteria.getShipAddress() == null) {
            criteria = (Drug) session.getAttribute("criteria");
        } else {
            session.setAttribute("criteria", criteria);
        }
        String condition = req.getParameter("condition");
        if (condition == null) {
            condition = (String) session.getAttribute("condition");
        } else {
            session.setAttribute("condition", condition);
        }
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Drug> pb = drugService.findByCombination(criteria, pc, condition, condition3);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        req.setAttribute("condition3", condition3);
        req.setAttribute("condition", condition);
        List<Category> parents = categoryService.findAll();
        req.setAttribute("parents", parents);
        return "f:/adminjsps/admin/drug/list.jsp";
    }

}
