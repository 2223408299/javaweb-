package order.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import drug.domain.Drug;
import order.domain.Order;
import order.domain.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import pager.Expression;
import pager.PageBean;
import pager.PageConstants;
import user.domain.myAddress;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 查询指定药品在订单中的个数
     *
     * @param drugId
     * @return
     * @throws SQLException
     */
    public int findByDrugId(String drugId) throws SQLException {
        String sql = "select count(*) from t_orderitem where drugId=?";
        Number cnt = (Number) qr.query(sql, new ScalarHandler(), drugId);
        return cnt == null ? 0 : cnt.intValue();
    }

    /*
     * 用来生成where子句
     */
    private String toWhereSql(int len) {
        StringBuilder sb = new StringBuilder("orderItemId in(");
        for (int i = 0; i < len; i++) {
            sb.append("?");
            if (i < len - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

//    /**
//     * 批量确认收货
//     *
//     * @param orderItemIds
//     */
//    public void batchUpdate(String orderItemIds) throws SQLException {
////        sql = "UPDATE t_drug a, t_orderitem b SET a.sales=a.sales+b.quantity,b.status= ?  WHERE a.drugId=b.drugId AND b.orderItemId= ?";
//        Object[] purseIdArray = orderItemIds.split(",");
//        String whereSql = toWhereSql(purseIdArray.length);
//        String sql = "delete from t_orderitem where " + whereSql;
//        qr.update(sql, purseIdArray);
//    }

    /**
     * 批量删除订单
     *
     * @param orderItemIds
     */
    public void batchDelete(String orderItemIds) throws SQLException {
        Object[] purseIdArray = orderItemIds.split(",");
        String whereSql = toWhereSql(purseIdArray.length);
        String sql = "delete from t_orderitem where " + whereSql;
        qr.update(sql, purseIdArray);
    }

    /**
     * 按用户ID或者订单编号查询
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<OrderItem> findOrderByUidOrOrderItemId(String uid,int pc) throws SQLException {
        int ps = PageConstants.ORDER_PAGE_SIZE;//每页记录数
        /*
         * 3. 总记录数
         */
        String sql = "select count(*) from t_orderitem WHERE uid = ? OR orderItemId =?";
        Number number = (Number)qr.query(sql, new ScalarHandler(),uid,uid);
        int tr = number.intValue();//得到了总记录数
        /*
         * 4. 得到beanList，即当前页记录
         */
        sql = "select * from t_orderitem WHERE uid = ? OR orderItemId =? order by ordertime desc limit ?,?";
        List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), uid,uid,(pc-1) * ps,ps);
        List<OrderItem> beanList = toOrderItemList(mapList);


        for(OrderItem orderItem : beanList) {
            loadOrder(orderItem);
        }

        /*
         * 5. 创建PageBean，设置参数
         */
        PageBean<OrderItem> pb = new PageBean<OrderItem>();
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
     * 按订单状态查询
     * @param status
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<OrderItem> findByStatus(int status, int pc) throws SQLException {
        List<Expression> exprList = new ArrayList<Expression>();
        exprList.add(new Expression("status", "=", status + ""));
        return findByCriteria(exprList, pc);
    }

    /**
     * 查询所有
     */
    public PageBean<OrderItem> findAllOrder(int pc) throws SQLException {
        List<Expression> exprList = new ArrayList<Expression>();
        return findByCriteria(exprList, pc);
    }

    /**
     * 加载订单(按orderId加载)
     * @param orderId
     * @return
     * @throws SQLException
     */
    public Order loadOrder(String orderId) throws SQLException {
        String sql = "select * from t_order where oid=?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), orderId);
        myAddress myAddress = CommonUtils.toBean(map, myAddress.class);
        Order order = CommonUtils.toBean(map, Order.class);
        order.setMyAddress(myAddress);
        loadOrderItem(order);//为当前订单加载它的所有订单条目
        return order;
    }

    /**
     * 加载订单(按orderItem加载)
     * @param orderItemId
     * @return
     * @throws SQLException
     */
    public OrderItem load(String orderItemId) throws SQLException {
        String sql = "select * from t_orderitem where orderItemId=?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), orderItemId);
        Drug drug = CommonUtils.toBean(map, Drug.class);
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        Order order = CommonUtils.toBean(map, Order.class);
        orderItem.setDrug(drug);
        orderItem.setOrder(order);
        loadOrder(orderItem);
//        loadOrderItem(orderItem);//为当前订单加载它的所有订单条目
        return orderItem;
    }

    /**
     * 批量支付准备(查询总计)
     * @param orderItemId
     * @return
     * @throws SQLException
     */
    public BigDecimal batchLoad(String orderItemId) throws SQLException {
        String sql = "select * from t_orderitem where orderItemId=?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), orderItemId);
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
//        Number total = (Number) qr.query(sql, new ScalarHandler(), orderItemId);
//        double subtotal = (double) total;
        BigDecimal total = BigDecimal.valueOf(orderItem.getSubtotal());
        return total;
    }

    /**
     * 删除订单
     * @param orderItemId
     * @throws SQLException
     */
    public void delect(String orderItemId) throws SQLException {
        String sql = "delete from t_orderitem where orderItemId=?";
        qr.update(sql,orderItemId);
    }

    /**
     * 修改订单状态
     * @param orderItemId
     * @param status
     * @throws SQLException
     */
    public void updateStatus(String orderItemId, int status) throws SQLException {
        String sql=null;
        if(status==2){
            sql = "UPDATE t_drug a, t_orderitem b SET a.inventories=a.inventories-b.quantity,b.status= ?  WHERE a.drugId=b.drugId AND b.orderItemId= ?";
        }
        else if(status==4) {
            sql = "UPDATE t_drug a, t_orderitem b SET a.sales=a.sales+b.quantity,b.status= ?  WHERE a.drugId=b.drugId AND b.orderItemId= ?";
        }else if(status==5){
            sql = "UPDATE t_drug a, t_orderitem b SET a.evaluate=a.evaluate+1,b.status= ?  WHERE a.drugId=b.drugId AND b.orderItemId= ?";
        }else {
            sql = "update t_orderitem set status=? where orderItemId=?";
        }
        qr.update(sql, status, orderItemId);
    }

    /**
     * 生成订单
     * @param order
     * @throws SQLException
     */
    public void add(Order order) throws SQLException {
        /*
         * 1. 插入订单
         */
//        String sql = "insert into t_order values(?,?,?,?,?,?,?,?)";
//        Object[] params = {order.getOid(), order.getOrdertime(),order.getOrderItemtotal(),
//                order.getTotal(),order.getMyAddress().getConsignee(),
//                order.getMyAddress().getPhone(),order.getMyAddress().getAddress(),
//                order.getOwner().getUid()};
        String sql = "insert into t_order values(?,?,?,?,?,?,?)";
        Object[] params = {order.getOid(), order.getOrdertime(),order.getOrderItemtotal(),
                order.getTotal(),order.getMyAddress().getConsignee(),
                order.getMyAddress().getPhone(),order.getMyAddress().getAddress()};
        qr.update(sql, params);

        /*
         * 2. 循环遍历订单的所有条目,让每个条目生成一个Object[]
         * 多个条目就对应Object[][]
         * 执行批处理，完成插入订单条目
         */
        sql = "insert into t_orderitem values(?,?,?,?,?,?,?,?,?,?,?)";
        int len = order.getOrderItemList().size();
        Object[][] objs = new Object[len][];
        for(int i = 0; i < len; i++){
            OrderItem item = order.getOrderItemList().get(i);
            objs[i] = new Object[]{item.getOrderItemId(),item.getQuantity(),
                    item.getSubtotal(),item.getStatus(),order.getOrdertime(),item.getDrug().getDrugId(),
                    item.getDrug().getDrugName(),item.getDrug().getPrice(),
                    item.getDrug().getImage_b(),order.getOid(),item.getOwner().getUid()};
        }
        qr.batch(sql, objs);
    }

    /**
     * 按用户查询订单
     * @param uid
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<OrderItem> findByUser(String uid, int pc) throws SQLException {
        List<Expression> exprList = new ArrayList<Expression>();
        exprList.add(new Expression("uid", "=", uid));
        return findByCriteria(exprList, pc);
    }

    /**
     * 按用户和订单状态查询
     * @param status
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<OrderItem> findByStatusAndUid(String uid,int status, int pc) throws SQLException {
        List<Expression> exprList = new ArrayList<Expression>();
        exprList.add(new Expression("uid", "=", uid));
        exprList.add(new Expression("status", "=", status + ""));
        return findByCriteria(exprList, pc);
    }

    private PageBean<OrderItem> findByCriteria(List<Expression> exprList, int pc) throws SQLException {
        /*
         * 1. 得到ps
         * 2. 得到tr
         * 3. 得到beanList
         * 4. 创建PageBean，返回
         */
        /*
         * 1. 得到ps
         */
        int ps = PageConstants.ORDER_PAGE_SIZE;//每页记录数
        /*
         * 2. 通过exprList来生成where子句
         */
        StringBuilder whereSql = new StringBuilder(" where 1=1");
        List<Object> params = new ArrayList<Object>();//SQL中有问号，它是对应问号的值
        for(Expression expr : exprList) {
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
            if(!expr.getOperator().equals("is null")) {
                whereSql.append("?");
                params.add(expr.getValue());
            }
        }

        /*
         * 3. 总记录数
         */
        String sql = "select count(*) from t_orderitem" + whereSql;
        Number number = (Number)qr.query(sql, new ScalarHandler(), params.toArray());
        int tr = number.intValue();//得到了总记录数
        /*
         * 4. 得到beanList，即当前页记录
         */
        sql = "select * from t_orderitem" + whereSql + " order by ordertime desc limit ?,?";
        params.add((pc-1) * ps);//当前页首行记录的下标
        params.add(ps);//一共查询几行，就是每页记录数

        List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), params.toArray());
        List<OrderItem> beanList = toOrderItemList(mapList);


        for(OrderItem orderItem : beanList) {
            loadOrder(orderItem);
        }

        /*
         * 5. 创建PageBean，设置参数
         */
        PageBean<OrderItem> pb = new PageBean<OrderItem>();
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
    为orderItem查找order
     */
    private void loadOrder(OrderItem orderItem) throws SQLException{

        String sql = "select * from t_order where oid=?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), orderItem.getOrder().getOid());
        myAddress myAddress = CommonUtils.toBean(map, myAddress.class);
        Order order = CommonUtils.toBean(map, Order.class);
        order.setMyAddress(myAddress);
        orderItem.setOrder(order);
    }

    /*
     * 为指定的order载它的所有OrderItem
     */
    private void loadOrderItem(Order order) throws SQLException {
        /*
         * 1. 给sql语句select * from t_orderitem where oid=?
         * 2. 执行之，得到List<OrderItem>
         * 3. 设置给Order对象
         */
        String sql = "select * from t_orderitem where oid=?";
        List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
        List<OrderItem> orderItemList = toOrderItemList(mapList);

        order.setOrderItemList(orderItemList);
    }

    /**
     * 把多个Map转换成多个OrderItem
     * @param mapList
     * @return
     */
    private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for(Map<String,Object> map : mapList) {
            OrderItem orderItem = toOrderItem(map);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    /*
     * 把一个Map转换成一个OrderItem
     */
    private OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        Drug drug = CommonUtils.toBean(map, Drug.class);
        Order order = CommonUtils.toBean(map, Order.class);
//        User user = CommonUtils.toBean(map, User.class);
//        orderItem.setOwner(user);
        orderItem.setOrder(order);
        orderItem.setDrug(drug);
        return orderItem;
    }

    /**
     * 把多个Map转换成多个Order
     * @param mapList
     * @return
     */
    private List<Order> toOrderList(List<Map<String, Object>> mapList) {
        List<Order> orderList = new ArrayList<Order>();
        for(Map<String,Object> map : mapList) {
            Order order = toOrder(map);
            orderList.add(order);
        }
        return orderList;
    }

    /*
     * 把一个Map转换成一个Order
     */
    private Order toOrder(Map<String, Object> map) {
        Order Order = CommonUtils.toBean(map, Order.class);
        myAddress myAddress = CommonUtils.toBean(map, myAddress.class);
        Order.setMyAddress(myAddress);
        return Order;
    }



}
