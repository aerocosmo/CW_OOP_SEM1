package CW;

import java.util.*;
import java.util.stream.Collectors;

public class StreamOperations {

	public static double average(List<Integer> numbers) {
		return numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
	}

	public static List<String> prefixStrings(List<String> strings) {
		return strings.stream().map(String::toUpperCase).map(s -> "_new_" + s).collect(Collectors.toList());
	}
	// √енерик-метод, который позвол€ет обработать любые типы данных
	public static <T extends Number> List<Double> squaresOfUnique(List<T> numbers) {
	    return numbers.stream()
	            .distinct()
	            .map(n -> Math.pow(n.doubleValue(), 2)) // ѕреобразуем все в double дл€ вычислени€
	            .collect(Collectors.toList());
	}
	/*
	 * public static List<Integer> squaresOfUnique(List<Integer> numbers) { return
	 * numbers.stream().filter(n -> Collections.frequency(numbers, n) == 1).map(n ->
	 * n * n) .collect(Collectors.toList()); }
	 */

	public static <T> T getLastElement(Collection<T> collection) {
		if (collection.isEmpty())
			throw new NoSuchElementException("Collection is empty!");
		return collection.stream().reduce((first, second) -> second).get();
	}

	public static int sumOfEven(int[] numbers) {
		return Arrays.stream(numbers).filter(n -> n % 2 == 0).sum();
	}

	public static Map<Character, String> stringToMap(List<String> strings) {
	    return strings.stream()
	        .collect(Collectors.toMap(
	            s -> s.charAt(0), // ключ - первый символ строки
	            s -> s.substring(1), // значение - остаток строки
	            (existing, replacement) -> replacement)); // если есть дублирование ключей, замен€ем старое значение новым
	}

	/*
	 * public static Map<Character, String> stringToMap(List<String> strings) {
	 * return strings.stream().collect(Collectors.toMap(s -> s.charAt(0), s ->
	 * s.substring(1))); }
	 */
	
    public static List<Integer> parseNumbers(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

	
	public static void main(String[] args) { List<Integer> numbers =
		Arrays.asList(1, 2, 3, 4, 5); System.out.println("Average: " +
				average(numbers));
		
		List<String> strings = Arrays.asList("hello", "world");
			System.out.println("Prefixed Strings: " + prefixStrings(strings));
		
		//List<Integer> uniqueNumbers = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
		//System.out.println("Squares of Unique Numbers: " +
		//	squaresOfUnique(uniqueNumbers));
		List<Number> mixedNumbers = Arrays.asList(1, 2.1, 2, 3.5f, 2);
	    System.out.println("Squares of Unique (Mixed Types): " + squaresOfUnique(mixedNumbers));
		
		System.out.println("Last Element: " + getLastElement(numbers));
		
		int[] numArray = { 1, 2, 3, 4, 5 };
			System.out.println("Sum of Even Numbers: " + sumOfEven(numArray));
		
		List<String> forMap = Arrays.asList("apple", "banana", "cherry");
		System.out.println("Map from Strings: " + stringToMap(forMap).entrySet());
	}
	
}
