package lesson9.prob1;

import java.util.Arrays;
import java.util.List;

public class CountWords {

    public static void main(String[] args) {

        List<String> strs = Arrays.asList("cocktail", "mockito", "fanta", "cock");

        System.out.println(countWords(strs,'c','d',8));

    }

    public static long countWords(List<String> words,char c,char d,int len){
       return words.stream()
                .filter(x -> x.length() == len)
                .filter(x-> x.contains(String.valueOf(c)))
                .filter(x -> !x.contains(String.valueOf(d)))
                .count();


    }
}
