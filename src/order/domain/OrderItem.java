package order.domain;

import drug.domain.Drug;
import user.domain.User;

public class OrderItem {
    private String orderItemId;//主键
    private int quantity;//数量
    private double subtotal;//小计
    private int status;//订单状态：1未付款, 2已付款但未发货, 3已发货未确认收货, 4确认收货了交易成功, 5已取消(只有未付款才能取消)
    private String ordertime;//下单时间
    private Drug drug;//所关联的Drug
    private Order order;//所属的订单
    private User owner;//订单的所有者private

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }



    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
