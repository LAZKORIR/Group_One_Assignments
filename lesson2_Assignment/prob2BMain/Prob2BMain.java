package prob2BMain;

import prob2B.Order;
import prob2B.OrderFactory;
import prob2B.OrderLine;
import java.time.LocalDate;

public class Prob2BMain {
    public static void main(String[] args) {
        // Create an order
        Order order = OrderFactory.createOrder("1345", LocalDate.now());

        // Add order lines
        OrderLine orderLine1 = OrderFactory.createOrderLine(order, 1, 10.99, 5);
        OrderLine orderLine2 = OrderFactory.createOrderLine(order, 2, 20.99, 2);

        // Display order details
        System.out.println(order);
        System.out.println(orderLine1);
        System.out.println(orderLine2);
    }
}