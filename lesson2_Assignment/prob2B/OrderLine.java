package prob2B;

public class OrderLine {
    private int orderlinenum;
    private double price;
    private int qty;
    private Order order;
    
 

    OrderLine(int orderlinenum, double price, int qty) {
        this.orderlinenum = orderlinenum;
        this.price = price;
        this.qty = qty;
    }

    public int getOrderlinenum() {
        return orderlinenum;
    }

    public void setOrderlinenum(int orderlinenum) {
        this.orderlinenum = orderlinenum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Order getOrder() {
       return this.order;
    }

    // toString for display purposes
    @Override
    public String toString() {
        return "OrderLine{" +
                "orderlinenum=" + orderlinenum +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}
