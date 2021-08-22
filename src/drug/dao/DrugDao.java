package drug.dao;

import category.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import drug.domain.Drug;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pager.Expression;
import pager.PageBean;
import pager.PageConstants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrugDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 按销量查询热门商品
     * @return
     * @throws SQLException
     */
    public List<Drug> findHotDrug2() throws SQLException {
        int max =PageConstants.DRUGhot2_SIZE;
        String sql ="SELECT * FROM t_drug where state = 1 ORDER BY sales DESC LIMIT 0,?";
        List<Drug> drugList = qr.query(sql, new BeanListHandler<Drug>(Drug.class),max);
        return drugList;
    }

    /**
     * 按销量查询热门商品
     * @return
     * @throws SQLException
     */
    public List<Drug> findHotDrug() throws SQLException {
        int max =PageConstants.DRUGhot_SIZE;
        String sql ="SELECT * FROM t_drug where state = 1 ORDER BY sales DESC LIMIT 0,?";
        List<Drug> drugList = qr.query(sql, new BeanListHandler<Drug>(Drug.class),max);
        return drugList;
    }

    /**
     * 按drugid查询
     *
     * @param drugid
     * @return
     * @throws SQLException
     */
    public Drug findByDrugid(String drugid) throws SQLException {
        String sql = "SELECT * FROM t_drug b, t_category c WHERE b.cid=c.cid AND b.drugId=?";
        // 一行记录中，包含了很多的book的属性，还有一个cid属性
        Map<String, Object> map = qr.query(sql, new MapHandler(), drugid);
        // 把Map中除了cid以外的其他属性映射到Drug对象中
        Drug drug = CommonUtils.toBean(map, Drug.class);
        // 把Map中cid属性映射到Category中，即这个Category只有cid
        Category category = CommonUtils.toBean(map, Category.class);
        // 两者建立关系
        drug.setCategory(category);

        // 把pid获取出来，创建一个Category parnet，把pid赋给它，然后再把parent赋给category
        if (map.get("pid") != null) {
            Category parent = new Category();
            parent.setCid((String) map.get("pid"));
            category.setParent(parent);
        }
        return drug;
    }

    /**
     * 按分类查询
     *
     * @param cid
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Drug> findByCategory(String cid, int pc, String condition3) throws SQLException {
        List<Expression> exprList = new ArrayList<Expression>();
        exprList.add(new Expression("cid", "=", cid));
        if ("putaway".equals(condition3)) {
            exprList.add(new Expression("state", "=", "1"));
        } else {
            exprList.add(new Expression("state", "=", "0"));
        }
        return findByCriteria(exprList, pc);
    }

    /**
     * 按药名模糊查询
     *
     * @param drugName
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Drug> findBydrugName(String drugName, int pc, String condition3) throws SQLException {
        List<Expression> exprList = new ArrayList<Expression>();
        exprList.add(new Expression("drugName", "like", "%" + drugName + "%"));
        if ("putaway".equals(condition3)) {
            exprList.add(new Expression("state", "=", "1"));
        } else {
            exprList.add(new Expression("state", "=", "0"));
        }
        return findByCriteria(exprList, pc);
    }

    /**
     * 降序
     *
     * @param condition
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Drug> findBydrugDesc(String condition, String condition2, int pc, String condition3) throws SQLException {

        /*
         * 1. 得到ps
         */
        int ps = PageConstants.DRUG_PAGE_SIZE;//每页记录数
        /*
        判断上下架状态
         */
        int state = 0;
        if ("putaway".equals(condition3)) {
            state = 1;
        }
        /*
         * 3. 总记录数
         */
        List<Drug> beanList;
        int tr = 0;
        String sql = null;
        if ("findAllDrug".equals(condition)) {
            sql = "select count(*) from t_drug where state = ?";
            Number number = (Number) qr.query(sql, new ScalarHandler(), state);
            tr = number.intValue();//得到了总记录数
            if ("price".equals(condition2)) {
                sql = "SELECT * FROM t_drug where state = ? ORDER BY price Desc LIMIT ?,?";
            } else if ("sales".equals(condition2)) {
                sql = "SELECT * FROM t_drug where state = ? ORDER BY sales Desc LIMIT ?,?";
            } else sql = "SELECT * FROM t_drug where state = ? ORDER BY inventories Desc LIMIT ?,?";
            beanList = qr.query(sql, new BeanListHandler<Drug>(Drug.class), state, (pc - 1) * ps, ps);
        } else {
            List<Expression> exprList = new ArrayList<Expression>();
            exprList.add(new Expression("cid", "=", condition + ""));
            PageBean<Drug> p = findByCriteria(exprList, pc);

            if (p.getBeanList().size() == 0) {
                sql = "select count(*) from t_drug where drugName like  ? and state = ?";
                Number number = (Number) qr.query(sql, new ScalarHandler(), "%" + condition + "%", state);
                tr = number.intValue();//得到了总记录数
                if ("price".equals(condition2)) {
                    sql = "SELECT * FROM t_drug WHERE drugName like  ? and state = ? ORDER BY price Desc LIMIT ?,?";
                } else if ("sales".equals(condition2)) {
                    sql = "SELECT * FROM t_drug WHERE drugName like  ? and state = ? ORDER BY sales Desc LIMIT ?,?";
                } else
                    sql = "SELECT * FROM t_drug WHERE drugName like  ? and state = ? ORDER BY inventories Desc LIMIT ?,?";
                beanList = qr.query(sql, new BeanListHandler<Drug>(Drug.class), "%" + condition + "%", state, (pc - 1) * ps, ps);
            } else {
                sql = "select count(*) from t_drug where cid = ? and state = ?";
                Number number = (Number) qr.query(sql, new ScalarHandler(), condition, state);
                tr = number.intValue();//得到了总记录数
                if ("price".equals(condition2)) {
                    sql = "SELECT * FROM t_drug WHERE cid = ? and state = ? ORDER BY price Desc LIMIT ?,?";
                } else if ("sales".equals(condition2)) {
                    sql = "SELECT * FROM t_drug WHERE cid = ? and state = ? ORDER BY sales Desc LIMIT ?,?";
                } else sql = "SELECT * FROM t_drug WHERE cid = ? and state = ? ORDER BY inventories Desc LIMIT ?,?";
                beanList = qr.query(sql, new BeanListHandler<Drug>(Drug.class), condition, state, (pc - 1) * ps, ps);
            }
        }


        PageBean<Drug> pb = new PageBean<Drug>();

        pb.setBeanList(beanList);
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(tr);

        return pb;
    }

    /**
     * 升序
     *
     * @param condition
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Drug> findByAsc(String condition, String condition2, int pc, String condition3) throws SQLException {

        /*
         * 1. 得到ps
         */
        int ps = PageConstants.DRUG_PAGE_SIZE;//每页记录数
        int state = 0;
        if ("putaway".equals(condition3)) {
            state = 1;
        }
        /*
         * 3. 总记录数
         */
        List<Drug> beanList;
        int tr = 0;
        String sql = null;

        if ("findAllDrug".equals(condition)) {
            sql = "select count(*) from t_drug where state = ?";
            Number number = (Number) qr.query(sql, new ScalarHandler(), state);
            tr = number.intValue();//得到了总记录数
            if ("price".equals(condition2)) {
                sql = "SELECT * FROM t_drug where state = ? ORDER BY price ASC LIMIT ?,?";
            } else if ("sales".equals(condition2)) {
                sql = "SELECT * FROM t_drug where state = ? ORDER BY sales ASC LIMIT ?,?";
            } else sql = "SELECT * FROM t_drug where state = ? ORDER BY inventories ASC LIMIT ?,?";
            beanList = qr.query(sql, new BeanListHandler<Drug>(Drug.class), state, (pc - 1) * ps, ps);
        } else {
            List<Expression> exprList = new ArrayList<Expression>();
            exprList.add(new Expression("cid", "=", condition + ""));
            PageBean<Drug> p = findByCriteria(exprList, pc);
            if (p.getBeanList().size() == 0) {
                sql = "select count(*) from t_drug where drugName like ? and state  = ?";
                Number number = (Number) qr.query(sql, new ScalarHandler(), "%" + condition + "%", state);
                tr = number.intValue();//得到了总记录数
                if ("price".equals(condition2)) {
                    sql = "SELECT * FROM t_drug WHERE drugName like ? and state = ? ORDER BY price ASC LIMIT ?,?";
                } else if ("sales".equals(condition2)) {
                    sql = "SELECT * FROM t_drug WHERE drugName like ? and state = ? ORDER BY sales ASC LIMIT ?,?";
                } else
                    sql = "SELECT * FROM t_drug WHERE drugName like ? and state = ? ORDER BY inventories ASC LIMIT ?,?";
                beanList = qr.query(sql, new BeanListHandler<Drug>(Drug.class), "%" + condition + "%", state, (pc - 1) * ps, ps);
            } else {
                sql = "select count(*) from t_drug where cid = ? and state = ?";
                Number number = (Number) qr.query(sql, new ScalarHandler(), condition, state);
                tr = number.intValue();//得到了总记录
                if ("price".equals(condition2)) {
                    sql = "SELECT * FROM t_drug WHERE cid = ? and state = ? ORDER BY price ASC LIMIT ?,?";
                } else if ("sales".equals(condition2)) {
                    sql = "SELECT * FROM t_drug WHERE cid = ? and state = ? ORDER BY sales ASC LIMIT ?,?";
                } else
                    sql = "SELECT * FROM t_drug WHERE cid = ? and state = ? ORDER BY inventories ASC LIMIT ?,?";
                beanList = qr.query(sql, new BeanListHandler<Drug>(Drug.class), condition, state, (pc - 1) * ps, ps);
            }
        }
        /*
         * 4. 得到beanList，即当前页记录
         */


        PageBean<Drug> pb = new PageBean<Drug>();

        pb.setBeanList(beanList);
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(tr);

        return pb;
    }

    /**
     * 多条件组合查询
     *
     * @param criteria
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Drug> findByCombination(Drug criteria, int pc, String condition, String condition3) throws SQLException {
        List<Expression> exprList2 = new ArrayList<Expression>();
        List<Expression> exprList = new ArrayList<Expression>();
        if ("findAllDrug".equals(condition)) {
            exprList.add(new Expression("drugId", "is not null", ""));
        } else {
            exprList2.add(new Expression("cid", "like", "%" + condition + "%"));
            PageBean<Drug> pb = findByCriteria(exprList2, pc);
            if (pb.getBeanList().size() == 0) {
                exprList.add(new Expression("drugName", "like", "%" + condition + "%"));
            } else {
                exprList.add(new Expression("cid", "like", "%" + condition + "%"));
            }
        }
        if ("putaway".equals(condition3)) {
            exprList.add(new Expression("state", "=", "1"));
        } else {
            exprList.add(new Expression("state", "=", "0"));
        }
        exprList.add(new Expression("sign", "like", "%" + criteria.getSign() + "%"));
        exprList.add(new Expression("shipAddress", "like", "%" + criteria.getShipAddress() + "%"));
        if (criteria.getMaxprice() != 0.0) {
            exprList.add(new Expression("price", ">", +criteria.getMinprice() + ""));
            exprList.add(new Expression("price", "<", +criteria.getMaxprice() + ""));
        }
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
    private PageBean<Drug> findByCriteria(List<Expression> exprList, int pc) throws SQLException {
        /*
         * 1. 得到ps
         * 2. 得到tr
         * 3. 得到beanList
         * 4. 创建PageBean，返回
         */
        /*
         * 1. 得到ps
         */
        int ps = PageConstants.DRUG_PAGE_SIZE;//每页记录数
        /*
         * 2. 通过exprList来生成where子句
         */
        StringBuilder whereSql = new StringBuilder(" where 1=1");
        List<Object> params = new ArrayList<Object>();//SQL中有问号，它是对应问号的值
        for (Expression expr : exprList) {
            /*
             * 添加一个条件上，
             * 1) 以and开头
             * 2) 条件的名称
             * 3) 条件的运算符，可以是=、!=、>、< ... is null，is null没有值
             * 4) 如果条件不是is null，再追加问号，然后再向params中添加一与问号对应的值
             */
            whereSql.append(" and ").append(expr.getName())
                    .append(" ").append(expr.getOperator()).append(" ");
            // where 1=1 and bid = ?
            if (!expr.getOperator().equals("is null") && !expr.getOperator().equals("is not null")) {
                whereSql.append("?");
                params.add(expr.getValue());
            }
        }

        /*
         * 3. 总记录数
         */
        String sql = "select count(*) from t_drug" + whereSql;
        Number number = (Number) qr.query(sql, new ScalarHandler(), params.toArray());
        int tr = number.intValue();//得到了总记录数j
        /*
         * 4. 得到beanList，即当前页记录
         */
        sql = "select * from t_drug" + whereSql + " order by orderBy limit ?,?";
        params.add((pc - 1) * ps);//当前页首行记录的下标
        params.add(ps);//一共查询几行，就是每页记录数

        List<Drug> beanList = qr.query(sql, new BeanListHandler<Drug>(Drug.class),
                params.toArray());

        /*
         * 5. 创建PageBean，设置参数
         */
        PageBean<Drug> pb = new PageBean<Drug>();
        /*
         * 其中PageBean没有url，这个任务由Servlet完成
         */
        pb.setBeanList(beanList);
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(tr);

        return pb;
    }

    /**
     * 查找所有已经上下架的药品
     *
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Drug> findAllDrug(int pc, String condition3) throws SQLException {
        /*
         * 1. 得到ps
         */
        int ps = PageConstants.DRUG_PAGE_SIZE;//每页记录数
        /*
         * 3. 总记录数
         */
        String sql;
        int tr = 0;
        if ("putaway".equals(condition3)) {
            sql = "select count(*) from t_drug where state = 1 ";
            Number number = (Number) qr.query(sql, new ScalarHandler());
            tr = number.intValue();//得到了总记录数
            sql = "select * from t_drug where state = 1 order by orderBy limit ?,?";
        } else {
            sql = "select count(*) from t_drug where state = 0 ";
            Number number = (Number) qr.query(sql, new ScalarHandler());
            tr = number.intValue();//得到了总记录数
            sql = "select * from t_drug where state = 0 order by orderBy limit ?,?";
        }
        List<Drug> beanList = qr.query(sql, new BeanListHandler<Drug>(Drug.class), (pc - 1) * ps, ps);

        PageBean<Drug> pb = new PageBean<Drug>();
        pb.setBeanList(beanList);
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(tr);

        return pb;
    }

    /**
     * 删除药品
     *
     * @param drugId
     * @throws SQLException
     */
    public void delete(String drugId) throws SQLException {
        String sql = "delete from t_drug where drugId=?";
        qr.update(sql, drugId);
    }

    /*
     * 用来生成where子句
     */
    private String toWhereSql(int len) {
        StringBuilder sb = new StringBuilder("drugId in(");
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
     * 批量删除
     *
     * @param drugIds
     * @throws SQLException
     */
    public void batchDelete(String drugIds) throws SQLException {
        /*
         * 需要先把cartItemIds转换成数组
         * 1. 把cartItemIds转换成一个where子句
         * 2. 与delete from 连接在一起，然后执行之
         */
        Object[] drugIdsArray = drugIds.split(",");
        String whereSql = toWhereSql(drugIdsArray.length);
        String sql = "delete from t_drug where " + whereSql;
        qr.update(sql, drugIdsArray);//其中cartItemIdArray必须是Object类型的数组！
    }

    /**
     * 批量上下架药品
     *
     * @param drugIds
     * @throws SQLException
     */
    public void batchOpdrug(String drugIds, int state) throws SQLException {
        /*
         * 需要先把cartItemIds转换成数组
         * 1. 把cartItemIds转换成一个where子句
         * 2. 与delete from 连接在一起，然后执行之
         */
        Object[] drugIdsArray = drugIds.split(",");
        String whereSql = toWhereSql(drugIdsArray.length);
        String sql = null;
        if (state == 1) {
            sql = "update t_drug set state = 1 where " + whereSql;
        } else sql = " update t_drug set state  = 0 where " + whereSql;
            qr.update(sql,drugIdsArray);//其中cartItemIdArray必须是Object类型的数组！
    }

    /**
     * 上下架药品
     *
     * @param drugId,condition
     * @throws SQLException
     */
    public void updateState(String drugId, String condition3) throws SQLException {
        int state = 1;
        if ("putaway".equals(condition3)) {
            state = 0;
        }
        String sql = "update t_drug set state = ? where drugId=?";
        qr.update(sql, state, drugId);
    }

    /**
     * 修改药品
     *
     * @param drug
     * @throws SQLException
     */
    public void edit(Drug drug) throws SQLException {
        String sql = "update t_drug set drugName=?,sign=?,drugDesc=?,cost=?," +
                "price=?,inventories=?,shipAddress=?,cid=? where drugId=?";
        Object[] params = {drug.getDrugName(), drug.getSign(),
                drug.getDrugDesc(), drug.getCost(), drug.getPrice(), drug.getInventories(),
                drug.getShipAddress(), drug.getCategory().getCid(), drug.getDrugId()};
        qr.update(sql, params);
    }

    /**
     * 查询指定分类下药品的个数
     *
     * @param cid
     * @return
     * @throws SQLException
     */
    public int findDrugCountByCategory(String cid) throws SQLException {
        String sql = "select count(*) from t_drug where cid=?";
        Number cnt = (Number) qr.query(sql, new ScalarHandler(), cid);
        return cnt == null ? 0 : cnt.intValue();
    }

    /**
     * 添加药品
     *
     * @param drug
     * @throws SQLException
     */
    public void add(Drug drug) throws SQLException {
        String sql = "insert into t_drug(drugId,drugName,sign,drugDesc,cost," +
                "price,sales,inventories,evaluate,shipAddress,state,cid,image_b)" +
                " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {drug.getDrugId(), drug.getDrugName(), drug.getSign(),
                drug.getDrugDesc(), drug.getCost(), drug.getPrice(),
                drug.getSales(), drug.getInventories(), drug.getEvaluate(), drug.getShipAddress(),
                drug.isState(), drug.getCategory().getCid(),
                drug.getImage_b()};
        qr.update(sql, params);
    }

    /**
     * 修改药品图片
     *
     * @param drug
     * @throws SQLException
     */
    public void upPicture(Drug drug) throws SQLException {
        String sql = "update t_drug set image_b=? where drugId=?";
        Object[] params = {drug.getImage_b(), drug.getDrugId()};
        qr.update(sql, params);
    }
}
