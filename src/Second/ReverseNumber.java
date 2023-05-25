import java.util.*;

public class ReverseNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть число: ");
        int number = scanner.nextInt();

        Stack<Integer> stack = new Stack<Integer>();
        while (number > 0) {
            int digit = number % 10;
            stack.push(digit);
            number /= 10;
        }

        int reversedNumber = 0;
        int decimalPlace = 1;
        while (!stack.empty()) {
            int digit = stack.pop();
            reversedNumber += digit * decimalPlace;
            decimalPlace *= 10;
        }

        System.out.println("Число у зворотному порядку: " + reversedNumber);
    }
}