package order.service;

import cn.itcast.jdbc.JdbcUtils;
import order.dao.OrderDao;
import order.domain.Order;
import order.domain.OrderItem;
import pager.PageBean;

import java.math.BigDecimal;
import java.sql.SQLException;

public class OrderService {
    private OrderDao orderDao = new OrderDao();

    /**
     * 查询指定药品在订单中的个数
     *
     * @param drugId
     * @return
     */
    public boolean findByDrugId(String drugId) {
        try {
            int cnt = orderDao.findByDrugId(drugId);
            if (cnt > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量支付准备
     *
     * @param orderItemIds
     * @return
     */
    public BigDecimal batchLoad(String orderItemIds) {
        Object[] orderItemIdArray = orderItemIds.split(",");
//        double totals = 0.00;
        BigDecimal totals = BigDecimal.valueOf(0.00);
        for (int i = 0; i < orderItemIdArray.length; i++) {
            try {
                totals = totals.add(orderDao.batchLoad((String) orderItemIdArray[i]));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return totals;
    }

    /**
     * 批量修改订单状态
     *
     * @param orderItemIds
     * @param status
     */
    public void batchupdateStatus(String orderItemIds, int status) {
        Object[] purseIdArray = orderItemIds.split(",");
        for (int i = 0; i < purseIdArray.length; i++) {
            try {
                orderDao.updateStatus((String) purseIdArray[i], status);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 批量删除订单
     *
     * @param orderItemIds
     */
    public void batchDelete(String orderItemIds) {
        try {
            orderDao.batchDelete(orderItemIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按用户ID或者订单编号查询
     *
     * @param status
     * @param pc
     * @return
     */
    public PageBean<OrderItem> findOrderByUidOrOrderItemId(String uid, int pc) {
        try {
            JdbcUtils.beginTransaction();
            PageBean<OrderItem> pb = orderDao.findOrderByUidOrOrderItemId(uid, pc);
            JdbcUtils.commitTransaction();
            return pb;
        } catch (SQLException e) {
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 按用户和状态查询
     *
     * @param status
     * @param pc
     * @return
     */
    public PageBean<OrderItem> findByStatus(int status, int pc) {
        try {
            JdbcUtils.beginTransaction();
            PageBean<OrderItem> pb = orderDao.findByStatus(status, pc);
            JdbcUtils.commitTransaction();
            return pb;
        } catch (SQLException e) {
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所有
     *
     * @param pc
     * @return
     */
    public PageBean<OrderItem> findAllOrder(int pc) {
        try {
            JdbcUtils.beginTransaction();
            PageBean<OrderItem> pb = orderDao.findAllOrder(pc);
            JdbcUtils.commitTransaction();
            return pb;
        } catch (SQLException e) {
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载订单(按orderId加载)
     *
     * @param orderId
     * @return
     */
    public Order loadOrder(String orderId) {
        try {
            JdbcUtils.beginTransaction();
            Order order = orderDao.loadOrder(orderId);
            JdbcUtils.commitTransaction();
            return order;
        } catch (SQLException e) {
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载订单(按orderItem加载)
     *
     * @param orderItemId
     * @return
     */
    public OrderItem load(String orderItemId) {
        try {
            JdbcUtils.beginTransaction();
            OrderItem orderItem = orderDao.load(orderItemId);
            JdbcUtils.commitTransaction();
            return orderItem;
        } catch (SQLException e) {
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除订单
     *
     * @param orderItemId
     */
    public void delect(String orderItemId) {
        try {
            orderDao.delect(orderItemId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改订单状态
     *
     * @param orderItemId
     * @param status
     */
    public void updateStatus(String orderItemId, int status) {
        try {
            orderDao.updateStatus(orderItemId, status);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成订单
     *
     * @param order
     */
    public void createOrder(Order order) {
        try {
            JdbcUtils.beginTransaction();
            orderDao.add(order);
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
     * 我的订单
     *
     * @param uid
     * @return
     */
    public boolean findByUid(String uid) {
        try {
            PageBean<OrderItem> pb = orderDao.findByUser(uid, 1);
            if (pb.getBeanList().size() != 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 我的订单
     *
     * @param uid
     * @param pc
     * @return
     */
    public PageBean<OrderItem> myOrders(String uid, int pc) {
        try {
            JdbcUtils.beginTransaction();
            PageBean<OrderItem> pb = orderDao.findByUser(uid, pc);
            JdbcUtils.commitTransaction();
            return pb;
        } catch (SQLException e) {
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 按用户和状态查询
     *
     * @param status
     * @param pc
     * @return
     */
    public PageBean<OrderItem> findByStatusAndUid(String uid, int status, int pc) {
        try {
            JdbcUtils.beginTransaction();
            PageBean<OrderItem> pb = orderDao.findByStatusAndUid(uid, status, pc);
            JdbcUtils.commitTransaction();
            return pb;
        } catch (SQLException e) {
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

}
