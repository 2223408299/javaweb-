package admin.user.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pager.PageBean;
import pager.PageConstants;
import user.domain.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminUserDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 查找用户
     *
     * @param pc,condition
     * @throws SQLException
     */
    public PageBean<User> findAllUser(int pc, String condition) throws SQLException {

        /*
         * 1. 得到ps
         */
        int ps = PageConstants.USER_PAGE_SIZE;//每页记录数

        String sql;
        int tr = 0;
        if ("normal".equals(condition)) {
            sql = "select count(*) from t_user where state = 1";
            Number number = (Number) qr.query(sql, new ScalarHandler());
            tr = number.intValue();//得到了总记录数
            sql = "select * from t_user where state = 1 order by orderBy limit ?,?";
        } else {
            sql = "select count(*) from t_user where state = 0";
            Number number = (Number) qr.query(sql, new ScalarHandler());
            tr = number.intValue();//得到了总记录数
            sql = "select * from t_user where state = 0 order by orderBy limit ?,?";
        }

        List<User> beanList = qr.query(sql, new BeanListHandler<User>(User.class), (pc - 1) * ps, ps);
        PageBean<User> pb = new PageBean<User>();
        pb.setBeanList(beanList);
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(tr);
        return pb;
    }

    /**
     * 按用户名称模糊查找
     *
     * @param pc,condition,username
     * @return
     */
    public PageBean<User> findByUsername(int pc, String condition, String loginname) throws SQLException {
        /*
         * 1. 得到ps
         */
        int ps = PageConstants.USER_PAGE_SIZE;//每页记录数

        String sql;
        int tr = 0;
        if ("normal".equals(condition)) {
            sql = "select count(*) from t_user where state = 1 and loginname LIKE ?";
            Number number = (Number) qr.query(sql, new ScalarHandler(), "%" + loginname + "%");
            tr = number.intValue();//得到了总记录数
            sql = "select * from t_user where state = 1 and loginname LIKE ? order by orderBy limit ?,?";
        } else {
            sql = "select count(*) from t_user where state = 0 and loginname LIKE ?";
            Number number = (Number) qr.query(sql, new ScalarHandler(), "%" + loginname + "%");
            tr = number.intValue();//得到了总记录数
            sql = "select * from t_user where state = 0 and loginname LIKE ? order by orderBy limit ?,?";
        }

        List<User> beanList = qr.query(sql, new BeanListHandler<User>(User.class), "%" + loginname + "%", (pc - 1) * ps, ps);
        PageBean<User> pb = new PageBean<User>();
        pb.setBeanList(beanList);
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(tr);
        return pb;
    }

    /**
     * 删除用户
     *
     * @param uid
     * @throws SQLException
     */
    public void delete(String uid) throws SQLException {
        String sql = "delete from t_user where uid=?";
        qr.update(sql, uid);
    }

    /**
     * 封号与解封
     *
     * @param uid,condition
     * @throws SQLException
     */
    public void updateState(String uid, String condition) throws SQLException {
        int state = 1;
        if ("normal".equals(condition)) {
            state = 0;
        }
        String sql = "update t_user set state = ? where uid=?";
        qr.update(sql, state, uid);
    }

    /**
     * 修改用户
     *
     * @param user
     * @throws SQLException
     */
    public void edit(User user) throws SQLException {
        String sql = "update t_user set loginname=?,loginpass=? where uid = ?";
        Object[] params = {user.getLoginname(), user.getLoginpass(), user.getUid()};
        qr.update(sql, params);
    }

    /**
     * 按uid查询
     *
     * @param uid
     * @return
     * @throws SQLException
     */
    public User findByUid(String uid) throws SQLException {
        String sql = "SELECT * FROM t_user WHERE uid = ?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), uid);
        // 把Map中除了cid以外的其他属性映射到User对象中
        User user = CommonUtils.toBean(map, User.class);

        return user;
    }

    /**
     * 添加新用户
     *
     * @param user
     */
    public void addUser(User user) throws SQLException {
        String sql = "insert into t_user(uid,loginname,loginpass,email,balance,status,state,activationCode) values(?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUid(), user.getLoginname(), user.getLoginpass(),
                user.getEmail(), user.getBalance(), user.isStatus(), user.isState(), user.getActivationCode()};
        qr.update(sql, params);
    }

    /*
     * 用来生成where子句
     */
    private String toWhereSql(int len) {
        StringBuilder sb = new StringBuilder("uid in(");
        for (int i = 0; i < len; i++) {
            sb.append("?");
            if (i < len - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 批量管理用户
     *
     * @param userIds
     * @param state
     * @throws SQLException
     */
    public void batchLock(String userIds, int state) throws SQLException {
        /*
         * 需要先把cartItemIds转换成数组
         * 1. 把cartItemIds转换成一个where子句
         * 2. 与delete from 连接在一起，然后执行之
         */
        Object[] userIdsArray = userIds.split(",");
        String whereSql = toWhereSql(userIdsArray.length);
        String sql = null;
        if (state == 1) {
            sql = "update t_user set state = 1 where " + whereSql;
        } else sql = " update t_user set state  = 0 where " + whereSql;
        qr.update(sql, userIdsArray);//其中cartItemIdArray必须是Object类型的数组！
    }

    /**
     * 批量删除用户
     *
     * @param userIds
     */
    public void batchDelete(String userIds) throws SQLException {
        Object[] userIdsArray = userIds.split(",");
        String whereSql = toWhereSql(userIdsArray.length);
        String sql = "delete from t_user where " + whereSql;
        qr.update(sql, userIdsArray);
    }
}
