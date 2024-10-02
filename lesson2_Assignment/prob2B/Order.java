package prob2B;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String ordernum;
    private LocalDate orderdate;
    private List<OrderLine> orderLines;

    // Constructor
    public Order(String ordernum, LocalDate orderdate) {
        this.ordernum = ordernum;
        this.orderdate = orderdate;
        this.orderLines = new ArrayList<>();
    }

    
    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public LocalDate getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void addOrderLine(OrderLine orderLine) {
        this.orderLines.add(orderLine);
    }

    // toString for display purposes
    @Override
    public String toString() {
        return "Order{" +
                "ordernum='" + ordernum + '\'' +
                ", orderdate=" + orderdate +
                ", orderLines=" + orderLines +
                '}';
    }
}