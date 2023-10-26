import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the equation: ");
        String input = scan.nextLine();
        System.out.print("The answer is: " + calc(input.trim()));
    }

    public static String calc(String input) {
        boolean isRoman = false;

        String[] knownRomans = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String[] operands = input.split("[-+*/\\s]+");

        if (operands.length != 2) {
            throw new IllegalArgumentException("Only two operands are allowed");
        }

        if (isInArray(operands[0].trim(), knownRomans) && isInArray(operands[1].trim(), knownRomans)) {
            isRoman = true;
        }

        String s3 = null;
        if (input.contains("+")) {
            s3 = "\\+";
        } else if (input.contains("-")) {
            s3 = "-";
        } else if (input.contains("/")) {
            s3 = "/";
        } else if (input.contains("*")) {
            s3 = "\\*";
        } else {
            throw new IllegalArgumentException("Invalid operation");
        }

        String[] numbers = input.split(s3);
        int num1, num2;
        if (isRoman) {
            num1 = romanToArabic(numbers[0].trim());
            num2 = romanToArabic(numbers[1].trim());
        } else {
            num1 = Integer.parseInt(numbers[0].trim());
            num2 = Integer.parseInt(numbers[1].trim());
        }

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Numbers should be between 1 and 10");
        }

        int result = 0;
        switch (s3) {
            case "\\+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "\\*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
        }

        if (isRoman) {
            if (result < 1) {
                throw new IllegalArgumentException("Roman numerals should be positive");
            }
            return arabicToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    public static boolean isInArray(String str, String[] array) {
        for (String s : array) {
            if (s.equals(str)) return true;
        }
        return false;
    }

    public static int romanToArabic(String roman) {
        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            int s1 = getValue(roman.charAt(i));
            if (i + 1 < roman.length()) {
                int s2 = getValue(roman.charAt(i + 1));
                if (s1 >= s2) {
                    result += s1;
                } else {
                    result += s2 - s1;
                    i++;
                }
            } else {
                result += s1;
            }
        }
        return result;
    }

    public static int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            default:
                return -1;
        }
    }

    public static String arabicToRoman(int num) {
        String[] romanNumerals = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
        int[] arabicNumerals = {1, 4, 5, 9, 10, 40, 50, 90, 100};
        StringBuilder sb = new StringBuilder();
        for (int i = arabicNumerals.length - 1; i >= 0; i--) {
            while (num >= arabicNumerals[i]) {
                sb.append(romanNumerals[i]);
                num -= arabicNumerals[i];
            }
        }
        return sb.toString();
    }
}