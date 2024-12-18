package CW;

import java.io.*;
import java.util.*;

class InvalidFileFormatException extends Exception {
	public InvalidFileFormatException(String message) {
		super(message);
	}
}

class FileReadException extends Exception {
	public FileReadException(String message) {
		super(message);
	}
}

public class Translator {
	private Map<String, String> dictionary = new HashMap<>();

	public void loadDictionary(String filename) throws InvalidFileFormatException, FileReadException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length != 2)
					throw new InvalidFileFormatException("Invalid dictionary format in file: " + filename);
				dictionary.put(parts[0].trim().toLowerCase(), parts[1].trim());
			}
		} catch (FileNotFoundException e) {
			throw new FileReadException("File not found: " + filename);
		} catch (IOException e) {
			throw new FileReadException("Failed to read file: " + filename);
		}
	}

	public String translate(String text) {
		StringBuilder result = new StringBuilder();
		String[] words = text.split("\\s+");
		for (String word : words) {
			String lowerCaseWord = word.toLowerCase();
			String translation = dictionary.getOrDefault(lowerCaseWord, word);
			result.append(translation).append(" ");
		}
		return result.toString().trim();
	}

	public static void main(String[] args) {
		Translator translator = new Translator();
		try {
			translator.loadDictionary("dictionary.txt");
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.print("Enter text to translate: ");
				String text = scanner.nextLine();
				if (text.equalsIgnoreCase("exit"))
					break;
				System.out.println("Translation: " + translator.translate(text));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
