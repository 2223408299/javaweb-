package order.domain;

import user.domain.myAddress;

import java.util.List;

public class Order {
    private String oid;//主键
    private String ordertime;//下单时间
    private int orderItemtotal;//该订单下的订单条目数量
    private double total;//总计
    private myAddress myAddress;//收货信息
//    private User owner;//订单的所有者
    private List<OrderItem> orderItemList;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }


    public int getOrderItemtotal() {
        return orderItemtotal;
    }

    public void setOrderItemtotal(int orderItemtotal) {
        this.orderItemtotal = orderItemtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public myAddress getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(myAddress myAddress) {
        this.myAddress = myAddress;
    }

//    public User getOwner() {
//        return owner;
//    }
//
//    public void setOwner(User owner) {
//        this.owner = owner;
//    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

}
