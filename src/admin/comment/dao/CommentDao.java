package admin.comment.dao;

import admin.comment.domain.Comment;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import drug.domain.Drug;
import order.domain.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pager.Expression;
import pager.PageBean;
import pager.PageConstants;
import user.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 删除评论
     *
     * @param commentId
     * @throws SQLException
     */
    public void deleteComment(String commentId, String drugId) throws SQLException {
        String sql = "delete from t_comment where commentId=?";
        qr.update(sql, commentId);
        sql = "update t_drug set evaluate = evaluate-1 where drugId = ?";
        qr.update(sql, drugId);
    }

    /**
     * 按订单Id查询评论
     *
     * @param orderItemId
     * @return
     * @throws SQLException
     */
    public Comment findCommentByOrderItemId(String orderItemId) throws SQLException {
        String sql = "select * from t_comment where orderItemId = ?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), orderItemId);
        Comment comment = CommonUtils.toBean(map, Comment.class);
        User user = CommonUtils.toBean(map, User.class);
        Drug drug = CommonUtils.toBean(map, Drug.class);
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        comment.setUser(user);
        comment.setDrug(drug);
        comment.setOrderItem(orderItem);
        loadUser(comment);
        loadDrug(comment);
        loadOrderItem(comment);
        return comment;
    }

    /**
     * 按评论Id查询评论
     *
     * @param commentId
     * @return
     * @throws SQLException
     */
    public Comment findCommentByCommentId(String commentId) throws SQLException {
        String sql = "select * from t_comment where commentId = ?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), commentId);
        Comment comment = CommonUtils.toBean(map, Comment.class);
        User user = CommonUtils.toBean(map, User.class);
        Drug drug = CommonUtils.toBean(map, Drug.class);
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        comment.setUser(user);
        comment.setDrug(drug);
        comment.setOrderItem(orderItem);
        loadUser(comment);
        loadDrug(comment);
        loadOrderItem(comment);
        return comment;
    }

    /**
     * 回复评论
     *
     * @param comment
     * @throws SQLException
     */
    public void replyComment(Comment comment) throws SQLException {
        String sql = "update t_comment set reply=?,replyTime=?,state=1 where commentId = ?";
        Object[] params = {comment.getReply(), comment.getReplyTime(), comment.getCommentId()};
        qr.update(sql, params);
    }

    /**
     * 添加评论
     *
     * @param comment
     * @throws SQLException
     */
    public void addComment(Comment comment) throws SQLException {
        String sql = "insert into t_comment values(?,?,?,?,?,?,?,?,?)";
        Object[] params = {comment.getCommentId(), comment.getContent(), comment.getCommentTime(),
                comment.getUser().getUid(), comment.getDrug().getDrugId(), comment.getOrderItem().getOrderItemId(), comment.getState(), null, null};
        qr.update(sql, params);
    }

    /**
     * 按评论状态和评论内容查询评论
     *
     * @param content
     * @param state
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Comment> findBycontent(int state, String content, int pc) throws SQLException {
        List<Expression> exprList = new ArrayList<Expression>();
        exprList.add(new Expression("state", "=", state + ""));
        exprList.add(new Expression("content", "like", "%" + content + "%"));
        return findByCriteria(exprList, pc);
    }

    /**
     * 按评论状态查询评论
     *
     * @param state
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Comment> findCommentBystate(int state, int pc) throws SQLException {
        List<Expression> exprList = new ArrayList<Expression>();
        exprList.add(new Expression("state", "=", state + ""));
        return findByCriteria(exprList, pc);
    }

    /**
     * 按drugid查询评论
     *
     * @param drugId
     * @return
     * @throws SQLException
     */
    public PageBean<Comment> findCommentByDrugid(String drugId, int pc) throws SQLException {
        List<Expression> exprList = new ArrayList<Expression>();
        exprList.add(new Expression("drugId", "=", drugId));
        return findByCriteria(exprList, pc);
    }

    /**
     * 通用的查询方法
     *
     * @param exprList
     * @param pc
     * @return
     * @throws SQLException
     */
    private PageBean<Comment> findByCriteria(List<Expression> exprList, int pc) throws SQLException {

        int ps = PageConstants.DRUG_PAGE_SIZE;//每页记录数
        StringBuilder whereSql = new StringBuilder(" where 1=1");
        List<Object> params = new ArrayList<Object>();//SQL中有问号，它是对应问号的值
        for (Expression expr : exprList) {
            whereSql.append(" and ").append(expr.getName())
                    .append(" ").append(expr.getOperator()).append(" ");
            // where 1=1 and bid = ?
            if (!expr.getOperator().equals("is null") && !expr.getOperator().equals("is not null")) {
                whereSql.append("?");
                params.add(expr.getValue());
            }
        }

        String sql = "select count(*) from t_comment" + whereSql;
        Number number = (Number) qr.query(sql, new ScalarHandler(), params.toArray());
        int tr = number.intValue();//得到了总记录数j
        /*
         * 4. 得到beanList，即当前页记录
         */
        sql = "select * from t_comment" + whereSql + " order by commentTime limit ?,?";
        params.add((pc - 1) * ps);//当前页首行记录的下标
        params.add(ps);//一共查询几行，就是每页记录数

//        List<Comment> beanList = qr.query(sql, new BeanListHandler<Comment>(Comment.class),
//                params.toArray());
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), params.toArray());
        List<Comment> beanList = toCommentList(mapList);

        for (Comment comment : beanList) {
            loadUser(comment);
            loadDrug(comment);
            loadOrderItem(comment);
        }
        /*
         * 5. 创建PageBean，设置参数
         */
        PageBean<Comment> pb = new PageBean<Comment>();
        /*
         * 其中PageBean没有url，这个任务由Servlet完成
         */
        pb.setBeanList(beanList);
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(tr);

        return pb;
    }

    /*
    为comment查找user
     */
    private void loadUser(Comment comment) throws SQLException {

        String sql = "select * from t_user where uid=?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), comment.getUser().getUid());
        User user = CommonUtils.toBean(map, User.class);
        comment.setUser(user);
    }

    /*
    为comment查找drug
     */
    private void loadDrug(Comment comment) throws SQLException {

        String sql = "select * from t_drug where drugId=?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), comment.getDrug().getDrugId());
        Drug drug = CommonUtils.toBean(map, Drug.class);
        comment.setDrug(drug);
    }

    /*
    为comment查找orderItem
     */
    private void loadOrderItem(Comment comment) throws SQLException {

        String sql = "select * from t_orderitem where orderItemId=?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), comment.getOrderItem().getOrderItemId());
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        comment.setOrderItem(orderItem);
    }

    /**
     * 把多个Map转换成多个Comment
     *
     * @param mapList
     * @return
     */
    private List<Comment> toCommentList(List<Map<String, Object>> mapList) {
        List<Comment> commentList = new ArrayList<Comment>();
        for (Map<String, Object> map : mapList) {
            Comment comment = toComment(map);
            commentList.add(comment);
        }
        return commentList;
    }

    /*
     * 把一个Map转换成一个toComment
     */
    private Comment toComment(Map<String, Object> map) {
        Comment comment = CommonUtils.toBean(map, Comment.class);
        Drug drug = CommonUtils.toBean(map, Drug.class);
        User user = CommonUtils.toBean(map, User.class);
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        comment.setOrderItem(orderItem);
        comment.setUser(user);
        comment.setDrug(drug);
        return comment;
    }
}
