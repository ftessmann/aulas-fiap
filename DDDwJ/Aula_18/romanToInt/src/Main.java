import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String romanNumeral = "MCMXCIV";

        Map<Character, Integer> romanValues = new HashMap<>();
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);
        romanValues.put('L', 50);
        romanValues.put('C', 100);
        romanValues.put('D', 500);
        romanValues.put('M', 1000);

        int total = 0;
        int previousValue = 0;

        for (int i = romanNumeral.length() - 1; i >= 0; i--) {
            int currentValue = romanValues.get(romanNumeral.charAt(i));

            if (currentValue < previousValue) {
                total -= currentValue;
            } else {
                total += currentValue;
            }

            previousValue = currentValue;
        }

        System.out.println("O número romano " + romanNumeral + " em inteiro é: " + total);
    }
}