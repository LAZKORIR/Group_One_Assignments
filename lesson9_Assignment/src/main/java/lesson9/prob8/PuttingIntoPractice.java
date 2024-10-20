package lesson9.prob8;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class PuttingIntoPractice{
    public static void main(String ...args){    
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
		
		List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300), 
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),	
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );
        
        
        // Query 1: Find all transactions from year 2011 and sort them by value (small to high).
        List<Transaction> sortedTransactions = transactions.stream()
                .filter(e -> e.getYear() >= 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println();
        sortedTransactions.forEach(System.out::println);
        
        // Query 2: What are all the unique cities where the traders work?
        Set<String> uniqueCities = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .collect(Collectors.toSet());
        System.out.println();
        uniqueCities.forEach(System.out::println);

        // Query 3: Find all traders from Cambridge and sort them by name.
        List<Trader> cambridgeTraders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> "Cambridge".equals(t.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println();
        cambridgeTraders.forEach(System.out::println);
        

        // Query 4: Return a string of all traders names sorted alphabetically.
        String traderNames = transactions.stream()

                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println();
        System.out.println(traderNames);

        // Query 5: Are there any trader based in Milan?
        boolean anyInMilan = transactions.stream()
                .anyMatch(t -> "Milan".equals(t.getTrader().getCity()));
        System.out.println();
        System.out.println("Any trader in Milan? " + anyInMilan);
   
     // Query 6: Update all transactions so that the traders from Milan are set to Cambridge.
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> "Milan".equals(t.getCity()))
                .forEach(t -> t.setCity("Cambridge"));
        System.out.println();
        System.out.println("======Updated Transactions=======");
        transactions.forEach(System.out::println);
        
        
        // Query 7: What's the highest value in all the transactions?
        int highestValue = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max()
                .orElseThrow(NoSuchElementException::new);
        System.out.println();
        System.out.println("Highest transaction value: " + highestValue);
    }
}
