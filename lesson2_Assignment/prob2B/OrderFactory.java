package prob2B;

import java.time.LocalDate;

public class OrderFactory {

    public static Order createOrder(String ordernum, LocalDate orderdate) {
        return new Order(ordernum, orderdate);
    }

    public static OrderLine createOrderLine(Order order, int orderlinenum, double price, int qty) {
        OrderLine orderLine = new OrderLine(orderlinenum, price, qty);
        orderLine.setOrder(order);  
        order.addOrderLine(orderLine);// Ensure bidirectional association
        return orderLine;
    }
}