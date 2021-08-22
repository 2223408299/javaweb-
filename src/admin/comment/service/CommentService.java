package admin.comment.service;

import admin.comment.dao.CommentDao;
import admin.comment.domain.Comment;
import cn.itcast.jdbc.JdbcUtils;
import order.dao.OrderDao;
import pager.PageBean;

import java.sql.SQLException;

public class CommentService {
    private CommentDao commentDao = new CommentDao();
    private OrderDao orderDao = new OrderDao();

    /**
     * 批量删除评论
     *
     * @param commentIds
     */
    public void batchDelete(String commentIds) {
        Object[] commentIdsArray = commentIds.split(",");
        for (int i = 0; i < commentIdsArray.length; i++) {
            try {
                Comment comment = commentDao.findCommentByCommentId((String) commentIdsArray[i]);
                commentDao.deleteComment((String) commentIdsArray[i],comment.getDrug().getDrugId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 删除评论
     *
     * @param commentId,drugId
     */
    public void deleteComment(String commentId,String drugId) {
        try {
            commentDao.deleteComment(commentId,drugId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按订单Id查询评论
     *
     * @param orderItemId
     * @return
     */
    public Comment findCommentByOrderItemId(String orderItemId) {
        try {
            return commentDao.findCommentByOrderItemId(orderItemId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按评论Id查询评论
     *
     * @param commentId
     * @return
     */
    public Comment findCommentByCommentId(String commentId) {
        try {
            return commentDao.findCommentByCommentId(commentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 回复评论
     *
     * @param comment
     */
    public void replyComment(Comment comment) {
        try {
            commentDao.replyComment(comment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按评论状态和评论内容查询评论
     *
     * @param content
     * @param state
     * @param pc
     * @return
     */
    public PageBean<Comment> findBycontent(int state, String content, int pc) {
        try {
            return commentDao.findBycontent(state, content, pc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按评论状态查询评论
     *
     * @param state
     * @param pc
     * @return
     */
    public PageBean<Comment> findCommentBystate(int state, int pc) {
        try {
            return commentDao.findCommentBystate(state, pc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加评论
     *
     * @param comment
     * @param orderItemId
     */
    public void addComment(Comment comment, String orderItemId) {
        try {
            JdbcUtils.beginTransaction();
            commentDao.addComment(comment);
            orderDao.updateStatus(orderItemId, 5);
            JdbcUtils.commitTransaction();
        } catch (SQLException e) {
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 按drugid查询评论
     *
     * @param drugid
     * @return
     */
    public PageBean<Comment> findCommentByDrugid(String drugid, int pc) {
        try {
            return commentDao.findCommentByDrugid(drugid, pc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
