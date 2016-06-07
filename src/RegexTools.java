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

    //выражение для поиска трехзначных чисел:
    private static String expression;

    //скомпилированное выражение для поиска трехзначных чисел:
    private static Pattern pattern;

    public RegexTools(){
        if(expression == null) {
            //готовим выражение для поиска трехзначных чисел:
            // ((сотни)?(десятки)?(единицы)) | ((сотни)?(10-19)) | ((сотни)?(десятки)) | (сотни)
            expression = "((";
            for (String s : hundreds) {
                expression += ("(\\b" + s + "\\b\\s*)" + '|');
            }
            expression = expression.substring(0, expression.length() - 1); //убираем символ '|' в конце
            expression += ")?(";
            for (String s : tens) {
                expression += ("(\\b" + s + "\\b\\s*)" + '|');
            }
            expression = expression.substring(0, expression.length() - 1); //убираем символ '|' в конце
            expression += ")?(";
            for (String s : ones) {
                expression += ("(\\b" + s + "\\b)" + '|');
            }
            expression = expression.substring(0, expression.length() - 1); //убираем символ '|' в конце
            expression += "))|((";
            for (String s : hundreds) {
                expression += ("(\\b" + s + "\\b\\s*)" + '|');
            }
            expression = expression.substring(0, expression.length() - 1); //убираем символ '|' в конце
            expression += ")?(";
            for (String s : elementaries) {
                expression += ("(\\b" + s + "\\b)" + '|');
            }
            expression = expression.substring(0, expression.length() - 1); //убираем символ '|' в конце
            expression += "))|((";
            for (String s : hundreds) {
                expression += ("(\\b" + s + "\\b\\s*)" + '|');
            }
            expression = expression.substring(0, expression.length() - 1); //убираем символ '|' в конце
            expression += ")?(";
            for (String s : tens) {
                expression += ("(\\b" + s + "\\b)" + '|');
            }
            expression = expression.substring(0, expression.length() - 1); //убираем символ '|' в конце
            expression += "))|(";
            for (String s : hundreds) {
                expression += ("(\\b" + s + "\\b)" + '|');
            }
            expression = expression.substring(0, expression.length() - 1); //убираем символ '|' в конце
            expression += ")";

            //компилируем сформированное выражение
            pattern = Pattern.compile(expression);
        }

    }

    /*
    Ищет числа от 1 до 999, записанные прописью, и заменяет их на цифровое представление.
     */
    public String replaceWordsToNumbers(String input) {

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String group = matcher.group(); //строка, содержащая найденное число

            /*  //debug calls
            System.out.println("found: [" + group + "]");
            System.out.println("start position: " + matcher.start());
            System.out.println("end position: " + matcher.end());
            System.out.println();
             */

            int num = toInteger(group); //конвертируем строку с числом в int
            input = input.replaceFirst(group, Integer.toString(num)); //преобразуем в строку и подставляем в результат

        }
        return input;
    }

    /*
    Преобразует число, записанное прописью, в числовой формат.
    Работает для чисел от 1 до 999.
    ВАЖНО: валидность входных данных не проверяется!
     */
    private int toInteger(String input){
        input = input.trim();
        int number = -1;
        for(int i = 0; i < hundreds.length; i++){
            if ( input.matches("\\b" + hundreds[i] + "\\b.*") ){
                number += (i+1)*100;
            }
        }
        for(int j = 0; j < elementaries.length; j++){
            if ( input.matches(".*\\b" + elementaries[j] + "\\b") ){
                number += (j+10);
            }
        }
        for(int j = 0; j < tens.length; j++){
            if ( input.matches(".*\\b" + tens[j] + "\\b.*") ){
                number += (j+2)*10;
            }
        }
        for(int j = 0; j < ones.length; j++){
            if ( input.matches(".*\\b" + ones[j] + "\\b") ){
                number += (j+1);
            }
        }

        if (number == -1)
            return -1;
        else
            return ++number;
    }

    public static void main(String args[]){
        RegexTools tools = new RegexTools();
        String result = tools.replaceWordsToNumbers(
                "пятьсот семь lol сорок azaza  шестьсот восемьдесят четыре qwerty девятьсот сорок шесть"
        );
        System.out.println(result);
    }
}
