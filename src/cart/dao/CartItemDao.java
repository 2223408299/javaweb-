package cart.dao;

import cart.domain.CartItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import drug.domain.Drug;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import user.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartItemDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 查询指定药品在购物车中的个数
     *
     * @param drugId
     * @return
     * @throws SQLException
     */
    public int findByDrugId(String drugId) throws SQLException {
        String sql = "select count(*) from t_cartitem where drugId=?";
        Number cnt = (Number) qr.query(sql, new ScalarHandler(), drugId);
        return cnt == null ? 0 : cnt.intValue();
    }

    /**
     * ajax加入购物车库存检验
     * @param drugId
     * @return
     * @throws SQLException
     */
    public int ajaxValidateInventories(String drugId) throws SQLException {
        String sql = "select inventories from t_drug where drugId=?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), drugId);
        int inventories = (int) number;
        return inventories;
    }

    /*
     * 用来生成where子句
     */
    private String toWhereSql(int len) {
        StringBuilder sb = new StringBuilder("cartItemId in(");
        for(int i = 0; i < len; i++) {
            sb.append("?");
            if(i < len - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 加载多个CartItem
     * @param cartItemIds
     * @return
     * @throws SQLException
     */
    public List<CartItem> loadCartItems(String cartItemIds) throws SQLException {
        /*
         * 1. 把cartItemIds转换成数组
         */
        Object[] cartItemIdArray = cartItemIds.split(",");
        /*
         * 2. 生成wehre子句
         */
        String whereSql = toWhereSql(cartItemIdArray.length);
        /*
         * 3. 生成sql语句
         */
        String sql = "select * from t_cartitem c, t_drug b where c.drugId=b.drugId and " + whereSql;
        /*
         * 4. 执行sql，返回List<CartItem>
         */
        return toCartItemList(qr.query(sql, new MapListHandler(), cartItemIdArray));
    }

    /**
     * 按id查询
     * @param cartItemId
     * @return
     * @throws SQLException
     */
    public CartItem findByCartItemId(String cartItemId) throws SQLException {
        String sql = "select * from t_cartItem c, t_drug b where c.drugId=b.drugId and c.cartItemId=?";
        Map<String,Object> map = qr.query(sql, new MapHandler(), cartItemId);
        return toCartItem(map);
    }

    /**
     * 批量删除
     * @param cartItemIds
     * @throws SQLException
     */
    public void batchDelete(String cartItemIds) throws SQLException {
        /*
         * 需要先把cartItemIds转换成数组
         * 1. 把cartItemIds转换成一个where子句
         * 2. 与delete from 连接在一起，然后执行之
         */
        Object[] cartItemIdArray = cartItemIds.split(",");
        String whereSql = toWhereSql(cartItemIdArray.length);
        String sql = "delete from t_cartitem where " + whereSql;
        qr.update(sql, cartItemIdArray);//其中cartItemIdArray必须是Object类型的数组！
    }

    /**
     * 查询某个用户的某本图书的购物车条目是否存在
     * @throws SQLException
     */
    public CartItem findByUidAndBid(String uid, String drugId) throws SQLException {
        String sql = "select * from t_cartitem where uid=? and drugId=?";
        Map<String,Object> map = qr.query(sql, new MapHandler(), uid, drugId);
        CartItem cartItem = toCartItem(map);
        return cartItem;
    }

    /**
     * 修改指定条目的数量
     * @param cartItemId
     * @param quantity
     * @throws SQLException
     */
    public void updateQuantity(String cartItemId, int quantity) throws SQLException {
        String sql = "update t_cartitem set quantity=? where cartItemId=?";
        qr.update(sql, quantity, cartItemId);
    }

    /**
     * 添加条目
     * @param cartItem
     * @throws SQLException
     */
    public void addCartItem(CartItem cartItem) throws SQLException {
        String sql = "insert into t_cartitem(cartItemId, quantity, drugId, uid)" +
                " values(?,?,?,?)";
        Object[] params = {cartItem.getCartItemId(), cartItem.getQuantity(),
                cartItem.getDrug().getDrugId(), cartItem.getUser().getUid()};
        qr.update(sql, params);
    }

    /*
     * 把一个Map映射成一个Cartitem
     */
    private CartItem toCartItem(Map<String,Object> map) {
        if(map == null || map.size() == 0) return null;
        CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
        Drug drug = CommonUtils.toBean(map, Drug.class);
        User user = CommonUtils.toBean(map, User.class);
        cartItem.setDrug(drug);
        cartItem.setUser(user);
        return cartItem;
    }

    /*
     * 把多个Map(List<Map>)映射成多个CartItem(List<CartItem>)
     */
    private List<CartItem> toCartItemList(List<Map<String,Object>> mapList) {
        List<CartItem> cartItemList = new ArrayList<CartItem>();
        for(Map<String,Object> map : mapList) {
            CartItem cartItem = toCartItem(map);
            cartItemList.add(cartItem);
        }
        return cartItemList;
    }

    /**
     * 通过用户查询购物车条目
     * @param uid
     * @return
     * @throws SQLException
     */
    public List<CartItem> findByUser(String uid) throws SQLException {
        String sql = "select * from t_cartitem c, t_drug b where c.drugId=b.drugId and uid=? order by c.orderBy";
        List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), uid);
        return toCartItemList(mapList);
    }
}
