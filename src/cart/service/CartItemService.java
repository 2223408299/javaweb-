package cart.service;

import cart.dao.CartItemDao;
import cart.domain.CartItem;
import cn.itcast.commons.CommonUtils;

import java.sql.SQLException;
import java.util.List;

public class CartItemService {
    private CartItemDao cartItemDao = new CartItemDao();

    /**
     * 查询指定药品在购物车中的个数
     * @param drugId
     * @return
     */
    public boolean findByDrugId(String drugId) {
        try {
            int cnt = cartItemDao.findByDrugId(drugId);
            if(cnt>0){
              return true;
            }
            return false;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ajax加入购物车库存检验
     * @param quantity,drugId
     * @return
     */
    public boolean ajaxValidateInventories(int quantity, String drugId) {
        try {
            int inventories = cartItemDao.ajaxValidateInventories(drugId);
            if(inventories<quantity){
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * 加载多个CartItem
     */
    public List<CartItem> loadCartItems(String cartItemIds) {
        try {
            return cartItemDao.loadCartItems(cartItemIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改购物车条目数量
     * @param cartItemId
     * @param quantity
     * @return
     */
    public CartItem updateQuantity(String cartItemId, int quantity) {
        try {
            cartItemDao.updateQuantity(cartItemId, quantity);
            return cartItemDao.findByCartItemId(cartItemId);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除功能
     * @param cartItemIds
     */
    public void batchDelete(String cartItemIds) {
        try {
            cartItemDao.batchDelete(cartItemIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加条目
     * @param cartItem
     */
    public void add(CartItem cartItem) {
        try {
            /*
             * 1. 使用uid和bid去数据库中查询这个条目是否存在
             */
            CartItem _cartItem = cartItemDao.findByUidAndBid(
                    cartItem.getUser().getUid(),
                    cartItem.getDrug().getDrugId());
            if(_cartItem == null) {//如果原来没有这个条目，那么添加条目
                cartItem.setCartItemId(CommonUtils.uuid());
                cartItemDao.addCartItem(cartItem);
            } else {//如果原来有这个条目，修改数量
                // 使用原有数量和新条目数量之各，来做为新的数量
                int quantity = cartItem.getQuantity() + _cartItem.getQuantity();
                // 修改这个老条目的数量
                cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 我的购物车功能
     * @param uid
     * @return
     */
    public List<CartItem> myCart(String uid) {
        try {
            return cartItemDao.findByUser(uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 我的购物车功能
     * @param uid
     * @return
     */
    public boolean findByUid(String uid) {
        try {
            List<CartItem> byUser = cartItemDao.findByUser(uid);
            if(byUser.size()!=0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
