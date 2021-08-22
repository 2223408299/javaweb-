package cart.domain;

import drug.domain.Drug;
import user.domain.User;

import java.math.BigDecimal;

public class CartItem {

    private String cartItemId;// 主键
    private int quantity;// 数量
    private Drug drug;// 条目对应的图书
    private User user;// 所属用户

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // 添加小计方法
    public double getSubtotal() {
        /*
         * 使用BigDecimal不会有误差
         * 要求必须使用String类型构造器
         */
        BigDecimal b1 = new BigDecimal(drug.getPrice() + "");
        BigDecimal b2 = new BigDecimal(quantity + "");
        BigDecimal b3 = b1.multiply(b2);
        return b3.doubleValue();
    }
}
