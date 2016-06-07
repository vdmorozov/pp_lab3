import java.util.regex.*;

/**
 * Created by Морозов on 07.06.2016.
 */
public class RegexTools {
    private final static String elementaries[] = {
            "десять",
            "одиннадцать",
            "двенадцать",
            "тринадцать",
            "четырнадцать",
            "пятнадцать",
            "шестнадцать",
            "семнадцать",
            "восемнадцать",
            "девятнадцать"
    };
    private final static String ones[] = {
            "один",
            "два",
            "три",
            "четыре",
            "пять",
            "шесть",
            "семь",
            "восемь",
            "девять"
    };
    private final static String tens[] = {
            "двадцать",
            "тридцать",
            "сорок",
            "пятьдесят",
            "шестьдесят",
            "семьдесят",
            "восемьдесят",
            "девяносто"
    };
    private final static String hundreds[] = {
            "сто",
            "двести",
            "триста",
            "четыреста",
            "пятьсот",
            "шестьсот",
            "семьсот",
            "восемьсот",
            "девятьсот"
    };

    /*
    Ищет числа от 1 до 999, записанные прописью, и заменяет их на цифровое представление.
     */
    public String replaceWordsToNumbers(String input) {
        //готовим выражение для поиска трехзначных чисел:
        // ((сотни)?(десятки)?(единицы)) | ((сотни)?(10-19)) | ((сотни)?(десятки)) | (сотни)
        String expression = "((";
        for(String s : hundreds) {
            expression += ( "(\\b" + s + "\\b\\s*)" + '|' );
        }
        expression = expression.substring(0, expression.length()-1); //убираем символ '|' в конце
        expression += ")?(";
        for(String s : tens) {
            expression += ( "(\\b" + s + "\\b\\s*)" + '|' );
        }
        expression = expression.substring(0, expression.length()-1); //убираем символ '|' в конце
        expression += ")?(";
        for(String s : ones) {
            expression += ( "(\\b" + s + "\\b)" + '|' );
        }
        expression = expression.substring(0, expression.length()-1); //убираем символ '|' в конце
        expression += "))|((";
        for(String s : hundreds) {
            expression += ( "(\\b" + s + "\\b\\s*)" + '|' );
        }
        expression = expression.substring(0, expression.length()-1); //убираем символ '|' в конце
        expression += ")?(";
        for(String s : elementaries) {
            expression += ( "(\\b" + s + "\\b)" + '|' );
        }
        expression = expression.substring(0, expression.length()-1); //убираем символ '|' в конце
        expression += "))|((";
        for(String s : hundreds) {
            expression += ( "(\\b" + s + "\\b\\s*)" + '|' );
        }
        expression = expression.substring(0, expression.length()-1); //убираем символ '|' в конце
        expression += ")?(";
        for(String s : tens) {
            expression += ( "(\\b" + s + "\\b)" + '|' );
        }
        expression = expression.substring(0, expression.length()-1); //убираем символ '|' в конце
        expression += "))|(";
        for(String s : hundreds) {
            expression += ( "(\\b" + s + "\\b)" + '|' );
        }
        expression = expression.substring(0, expression.length()-1); //убираем символ '|' в конце
        expression += ")";

        //используем сформированное выражение
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String group = matcher.group(); //строка, содержащая найденное число

              //debug calls
            System.out.println("found: [" + group + "]");
            System.out.println("start position: " + matcher.start());
            System.out.println("end position: " + matcher.end());
            System.out.println();

            //TODO: написать метод для конвертации строки в число

        }
        return input;
    }

}
