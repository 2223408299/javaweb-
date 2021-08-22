package drug.domain;

import category.domain.Category;

public class Drug {
    private String drugId;//主键
    private String drugName;//药名
    private String sign;//牌子
    private String drugDesc;//描述
    private double cost;//成本
    private double price;//定价
    private double minprice;//最低价
    private double maxprice;//最高价
    private int sales;//销量
    private int inventories;//库存
    private int evaluate;//评价
    private String shipAddress;//发货地
    private boolean state;//状态（是否上架）
    private Category category;//所属分类
    private String image_w;//大图路径
    private String image_b;//小图路径

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public double getMinprice() {
        return minprice;
    }

    public void setMinprice(double minprice) {
        this.minprice = minprice;
    }

    public double getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(double maxprice) {
        this.maxprice = maxprice;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDrugDesc() {
        return drugDesc;
    }

    public void setDrugDesc(String drugDesc) {
        this.drugDesc = drugDesc;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    public int getInventories() {
        return inventories;
    }

    public void setInventories(int inventories) {
        this.inventories = inventories;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage_w() {
        return image_w;
    }

    public void setImage_w(String imageW) {
        image_w = imageW;
    }

    public String getImage_b() {
        return image_b;
    }

    public void setImage_b(String imageB) {
        image_b = imageB;
    }


}
