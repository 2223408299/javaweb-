package admin.purse.dao;

import admin.purse.domain.Purse;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pager.PageBean;
import pager.PageConstants;

import java.sql.SQLException;
import java.util.List;

public class PurseDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 查询指定用户在钱包中的个数
     *
     * @param uid
     * @return
     * @throws SQLException
     */
    public int findByUid(String uid) throws SQLException {
        String sql = "select count(*) from t_purse where uid=?";
        Number cnt = (Number) qr.query(sql, new ScalarHandler(), uid);
        return cnt == null ? 0 : cnt.intValue();
    }

    /*
     * 用来生成where子句
     */
    private String toWhereSql(int len) {
        StringBuilder sb = new StringBuilder("purseId in(");
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
     * 批量删除已经处理的充值申请
     *
     * @param purseIds
     */
    public void batchDelete(String purseIds) throws SQLException {
        Object[] purseIdArray = purseIds.split(",");
        String whereSql = toWhereSql(purseIdArray.length);
        String sql = "delete from t_purse where " + whereSql;
        qr.update(sql, purseIdArray);
    }

//    /**
//     * 批量处理申请
//     *
//     * @param purseIds
//     */
//    public void batchCheck(String purseIds) throws SQLException {
//        Object[] purseIdArray = purseIds.split(",");
//        String sql= null;
//        for(int i = 0;i<purseIdArray.length;i++){
//            updateState((String) purseIdArray[i]);
//        }

//        String whereSql = toWhereSql(purseIdArray.length);
//        sql = "SELECT SUM(rechange) FROM t_purse WHERE "+whereSql;
//        Number total = (Number) qr.query(sql, new ScalarHandler(), purseIdArray);
//        sql = "UPDATE t_user a, t_purse b SET a.balance=a.balance+"+total+",b.state= 1 " +
//                " WHERE a.uid=b.uid AND b."+whereSql;
//        qr.update(sql, purseIdArray);
//    }

    /**
     * 按用户ID查询
     *
     * @param pc,condition
     * @throws SQLException
     */
    public PageBean<Purse> findPurseByUid(int pc, String condition,String uid) throws SQLException {

        /*
         * 1. 得到ps
         */
        int ps = PageConstants.USER_PAGE_SIZE;//每页记录数

        String sql;
        int tr = 0;
        if ("uncheck".equals(condition)) {
            sql = "select count(*) from t_purse where state = 0 and uid = ? ";
            Number number = (Number) qr.query(sql, new ScalarHandler(),uid);
            tr = number.intValue();//得到了总记录数
            sql = "select * from t_purse where state = 0 and uid = ? order by orderBy limit ?,?";
        } else {
            sql = "select count(*) from t_purse where state = 1 and uid = ?";
            Number number = (Number) qr.query(sql, new ScalarHandler(),uid);
            tr = number.intValue();//得到了总记录数
            sql = "select * from t_purse where state = 1 and uid = ? order by orderBy limit ?,?";
        }

        List<Purse> beanList = qr.query(sql, new BeanListHandler<Purse>(Purse.class), uid,(pc - 1) * ps, ps);
        PageBean<Purse> pb = new PageBean<Purse>();
        pb.setBeanList(beanList);
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(tr);
        return pb;
    }

    /**
     * 查找所有钱包充值管理
     *
     * @param pc,condition
     * @throws SQLException
     */
    public PageBean<Purse> findAllPurse(int pc, String condition) throws SQLException {

        /*
         * 1. 得到ps
         */
        int ps = PageConstants.USER_PAGE_SIZE;//每页记录数

        String sql;
        int tr = 0;
        if ("uncheck".equals(condition)) {
            sql = "select count(*) from t_purse where state = 0";
            Number number = (Number) qr.query(sql, new ScalarHandler());
            tr = number.intValue();//得到了总记录数
            sql = "select * from t_purse where state = 0 order by orderBy limit ?,?";
        } else {
            sql = "select count(*) from t_purse where state = 1";
            Number number = (Number) qr.query(sql, new ScalarHandler());
            tr = number.intValue();//得到了总记录数
            sql = "select * from t_purse where state = 1 order by orderBy limit ?,?";
        }

        List<Purse> beanList = qr.query(sql, new BeanListHandler<Purse>(Purse.class), (pc - 1) * ps, ps);
        PageBean<Purse> pb = new PageBean<Purse>();
        pb.setBeanList(beanList);
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(tr);
        return pb;
    }

    /**
     * 管理充值申请
     *
     * @param purseId
     * @throws SQLException
     */
    public void updateState(String purseId) throws SQLException {
        String sql = "UPDATE t_user a, t_purse b SET a.balance=a.balance+b.rechange,b.state= 1 " +
                " WHERE a.uid=b.uid AND b.purseId= ? ";
        qr.update(sql, purseId);
    }

    /**
     * 删除充值申请
     *
     * @param purseId
     * @throws SQLException
     */
    public void delete(String purseId) throws SQLException {
        String sql = "delete from t_purse where purseId=?";
        qr.update(sql, purseId);
    }

}
