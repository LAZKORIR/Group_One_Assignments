package lesson9.prob9;

import java.util.Optional;

public class Test {

    public static void main(String[] args) {
            //any vegetarian meal available?
            boolean isVegetarianAvailable = Dish.menu.stream()
                    .anyMatch(Dish::isVegetarian);
            System.out.println("Is there any vegetarian meal available? " + isVegetarianAvailable);

            //any healthy menu with calories less than 1000?
            boolean isHealthyMenuAvailable = Dish.menu.stream()
                    .anyMatch(d -> d.getCalories() < 1000);
            System.out.println("Is there any healthy menu with calories < 1000? " + isHealthyMenuAvailable);

            //any unhealthy menu with calories greater than 1000?
            boolean isUnhealthyMenuAvailable = Dish.menu.stream()
                    .anyMatch(d -> d.getCalories() > 1000);
            System.out.println("Is there any unhealthy menu with calories > 1000? " + isUnhealthyMenuAvailable);

            // return the first item of type MEAT.
            Optional<Dish> firstMeatDish = Dish.menu.stream()
                    .filter(d -> d.getType() == Dish.Type.MEAT)
                    .findFirst();
            System.out.println("First MEAT dish: " + firstMeatDish.orElse(null));

            //total calories in the menu using reduce.
            int totalCalories = Dish.menu.stream()
                    .map(Dish::getCalories)
                    .reduce(0, Integer::sum);
            System.out.println("Total calories in menu: " + totalCalories);

            //total calories using method references.
            int totalCaloriesMethodRef = Dish.menu.stream()
                    .mapToInt(Dish::getCalories)
                    .sum();
            System.out.println("Total calories (using method reference): " + totalCaloriesMethodRef);
        }

}
