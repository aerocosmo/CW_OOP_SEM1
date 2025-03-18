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

	private String dictionaryPath;

	public void setDictionaryPath(String path) {
	    this.dictionaryPath = path;
	}

	public String getDictionaryPath() {
	    return this.dictionaryPath;
	}
	
	public void loadDictionary(String filename) throws InvalidFileFormatException, FileReadException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
	            // Удостоверимся, что строка не пустая
	            if (line.trim().isEmpty()) {
	                throw new InvalidFileFormatException("Invalid dictionary format: empty line in file: " + filename);
	            }

	            // Разделяем строку по |
	            String[] parts = line.split("\\|");

	            // Проверяем формат строки — должно быть ровно 2 части
	            if (parts.length != 2) {
	                throw new InvalidFileFormatException("Invalid dictionary format in file: " + filename 
	                                                       + ". Line: " + line);
	            }

	            // Добавляем в словарь, в нижнем регистре
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
	    String[] words = text.split("\\s+"); // Разделяем текст на слова
	    int i = 0;

	    while (i < words.length) {
	        String bestMatch = null;  // Для хранения текущего лучшего совпадения
	        String originalPhrase = ""; // Соответствующая оригинальная фраза
	        int bestMatchLength = 0;    // Длина наилучшего совпадения

	        // Формируем фразу с учетом всех доступных слов
	        StringBuilder currentPhrase = new StringBuilder();
	        for (int j = i; j < words.length; j++) {
	            if (currentPhrase.length() > 0) {
	                currentPhrase.append(" ");
	            }
	            currentPhrase.append(words[j]);
	            String phrase = currentPhrase.toString().toLowerCase();

	            // Если текущая фраза присутствует в словаре и длиннее лучшего совпадения
	            if (dictionary.containsKey(phrase) && phrase.length() > bestMatchLength) {
	                bestMatch = dictionary.get(phrase); // Перевод
	                originalPhrase = phrase;            // Оригинальная фраза
	                bestMatchLength = phrase.length();  // Обновляем длину
	            }
	        }

	        if (bestMatch != null) {
	            // Если нашли лучшее совпадение, добавляем перевод
	            result.append(bestMatch).append(" ");
	            i += originalPhrase.split("\\s+").length; // Пропускаем слова, покрытые фразой
	        } else {
	            // Если совпадения нет — оставляем как есть
	            result.append(words[i]).append(" ");
	            i++; // Переходим к следующему слову
	        }
	    }

	    return result.toString().trim();
	}


	public boolean isDictionaryLoaded() {
	    return !dictionary.isEmpty();
	}


	public static void main(String[] args) { 
		Translator translator = new
			Translator(); try { translator.loadDictionary(
					"D:\\eclipse-workspace\\CourseWork_OOP_Java\\src\\CW\\dictionary.txt");
		Scanner scanner = new Scanner(System.in); while (true) {
			System.out.print("Enter text to translate: "); String text =
					scanner.nextLine(); if (text.equalsIgnoreCase("exit")) break;
	 System.out.println("Translation: " + translator.translate(text)); } } catch
			(Exception e) { System.err.println(e.getMessage()); } 
	}
}
