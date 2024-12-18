package CW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class MainApp {

	private JTextArea outputAreaTask1;
    private JTextArea outputAreaTask2;
    private JTextArea outputAreaTask3;
    private JTextArea outputAreaTask4;
    private Translator translator;
    private boolean dictionaryLoaded = false;


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new MainApp().createAndShowGUI();
		});
	}

	private void createAndShowGUI() {
        JFrame frame = new JFrame("Многозадачный интерфейс");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Увеличенный размер окна

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel task1Panel = new JPanel(new BorderLayout());
        createTask1Panel(task1Panel);
        tabbedPane.addTab("Задание 1", task1Panel);

        JPanel task2Panel = new JPanel(new BorderLayout());
        createTask2Panel(task2Panel);
        tabbedPane.addTab("Задание 2", task2Panel);

        JPanel task3Panel = new JPanel(new BorderLayout());
        createTask3Panel(task3Panel);
        tabbedPane.addTab("Задание 3", task3Panel);

        JPanel task4Panel = new JPanel(new BorderLayout());
        createTask4Panel(task4Panel);
        tabbedPane.addTab("Задание 4", task4Panel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

	// Создание панели для Задания 1
	private void createTask1Panel(JPanel panel) {
		JPanel inputPanel = new JPanel(new GridLayout(2, 1));

		JButton walkButton = new JButton("Ходить");
		JButton rideButton = new JButton("Ездить на лошади");
	    JButton flyButton = new JButton("Fly"); // Added Fly button

		walkButton.addActionListener(e -> {
			Hero hero = new Hero("Aragorn");
			hero.setStrategy(new Walk());
			hero.moveTo(new Point(10, 20));
			outputAreaTask1.setText(hero.getName() + " ходит к точке " + new Point(10, 20));
		});

		rideButton.addActionListener(e -> {
			Hero hero = new Hero("Aragorn");
			hero.setStrategy(new HorseRide());
			hero.moveTo(new Point(30, 40));
			outputAreaTask1.setText(hero.getName() + " едет к точке " + new Point(30, 40));
		});
		
		flyButton.addActionListener(e -> { // Added ActionListener for Fly button
	        Hero hero = new Hero("Aragorn");
	        hero.setStrategy(new Fly());
	        hero.moveTo(new Point(50, 60));
	        outputAreaTask1.setText(hero.getName() + " flew to " + new Point(50, 60));
	    });

		inputPanel.add(walkButton);
		inputPanel.add(rideButton);
	    inputPanel.add(flyButton); // Added Fly button to the panel

		outputAreaTask1 = new JTextArea();
		outputAreaTask1.setEditable(false);

		panel.add(inputPanel, BorderLayout.NORTH);
		panel.add(new JScrollPane(outputAreaTask1), BorderLayout.CENTER);
	}

	// Создание панели для Задания 2
	private void createTask2Panel(JPanel panel) {
	    JPanel inputPanel = new JPanel(new BorderLayout());

	    JButton annotationButton = new JButton("Запустить аннотированные методы");

	    annotationButton.addActionListener(e -> {
	        try {
	            MyClass obj = new MyClass();
	            StringBuilder output = new StringBuilder();

	            // Вызов вспомогательного метода для выполнения аннотированных методов
	            output.append(invokeAnnotatedMethods(obj));

	            outputAreaTask2.setText(output.toString());
	        } catch (IllegalAccessException | InvocationTargetException ex) {
	            ex.printStackTrace();
	            outputAreaTask2.setText("Ошибка при выполнении метода: " + ex.getMessage());
	        }
	    });

	    inputPanel.add(annotationButton, BorderLayout.NORTH);

	    outputAreaTask2 = new JTextArea();
	    outputAreaTask2.setEditable(false);

	    panel.add(inputPanel, BorderLayout.NORTH);
	    panel.add(new JScrollPane(outputAreaTask2), BorderLayout.CENTER);
	}

	// Вспомогательный метод для вызова аннотированных методов
	private String invokeAnnotatedMethods(MyClass obj) throws IllegalAccessException, InvocationTargetException {
	    StringBuilder output = new StringBuilder();
	    Method[] methods = MyClass.class.getDeclaredMethods();

	    for (Method m : methods) {
	        if (m.isAnnotationPresent(Repeat.class)) {
	            Repeat repeatAnnotation = m.getAnnotation(Repeat.class);
	            int repeatCount = repeatAnnotation.value();
	            m.setAccessible(true); // Делаем метод доступным

	            // Вызов метода столько раз, сколько указано в аннотации
	            invokeMethodMultipleTimes(m, obj, repeatCount);
	            output.append("Метод ").append(m.getName()).append(" выполнен ").append(repeatCount).append(" раз\n");
	        }
	    }
	    return output.toString();
	}

	// Вспомогательный метод для многократного вызова метода
	private void invokeMethodMultipleTimes(Method method, MyClass obj, int repeatCount) throws IllegalAccessException, InvocationTargetException {
	    for (int i = 0; i < repeatCount; i++) {
	        if (method.getParameterCount() == 1) {
	            Class<?> paramType = method.getParameterTypes()[0];
	            if (paramType == String.class) {
	                method.invoke(obj, "testString"); // Передаем строку для защищенного метода
	            } else if (paramType == double.class) {
	                method.invoke(obj, 3.14); // Передаем число для приватного метода
	            }
	        }
	    }
	}

	// Создание панели для Задания 3
	private void createTask3Panel(JPanel panel) {
		JPanel inputPanel = new JPanel(new BorderLayout());

		JButton loadDictionaryButton = new JButton("Загрузить словарь");
		JButton translateButton = new JButton("Перевести текст");
		JTextField inputTextField = new JTextField();
		outputAreaTask3 = new JTextArea();
		outputAreaTask3.setEditable(false);

		loadDictionaryButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    translator = new Translator();
                    translator.loadDictionary(selectedFile.getAbsolutePath());
                    dictionaryLoaded = true; // Устанавливаем флаг
                    outputAreaTask3.setText("Словарь загружен. Введите текст для перевода.");
                } catch (InvalidFileFormatException | FileReadException ex) {
                	outputAreaTask3.setText("Ошибка: " + ex.getMessage());
                    dictionaryLoaded = false; // Сбрасываем флаг при ошибке
                }
            }
        });

        translateButton.addActionListener(e -> {
            if (!dictionaryLoaded) { // Проверяем флаг
            	outputAreaTask3.setText("Ошибка: Словарь не загружен. Загрузите словарь, используя кнопку 'Load Dictionary'.");
                return;
            }
            String inputText = inputTextField.getText();
            try {
                String translation = translator.translate(inputText);
                outputAreaTask3.setText(translation);
            } catch (Exception ex) {
            	outputAreaTask3.setText("Ошибка перевода: " + ex.getMessage());
            }
        });

		inputPanel.add(loadDictionaryButton, BorderLayout.NORTH);
		inputPanel.add(inputTextField, BorderLayout.CENTER);
		inputPanel.add(translateButton, BorderLayout.SOUTH);

		outputAreaTask3 = new JTextArea();
		outputAreaTask3.setEditable(false);

		panel.add(inputPanel, BorderLayout.NORTH);
		panel.add(new JScrollPane(outputAreaTask3), BorderLayout.CENTER);
	}

	// Создание панели для Задания 4
	private void createTask4Panel(JPanel panel) {
	    JPanel inputPanel = new JPanel(new GridLayout(7, 2));

	    // Labels and Text Fields
	    JLabel averageLabel = new JLabel("Enter numbers for average (comma-separated):");
	    JTextField averageInput = new JTextField();
	    JLabel prefixLabel = new JLabel("Enter strings for prefix (comma-separated):");
	    JTextField prefixInput = new JTextField();
	    JLabel uniqueSquaresLabel = new JLabel("Enter numbers for unique squares (comma-separated):");
	    JTextField uniqueSquaresInput = new JTextField();
	    JLabel lastElementLabel = new JLabel("Enter numbers for last element (comma-separated):");
	    JTextField lastElementInput = new JTextField();
	    JLabel sumEvenLabel = new JLabel("Enter numbers for sum of even (space-separated):");
	    JTextField sumEvenInput = new JTextField();
	    JLabel stringToMapLabel = new JLabel("Enter strings for string to map (comma-separated):");
	    JTextField stringToMapInput = new JTextField();

	    // Buttons
	    JButton averageButton = new JButton("Calculate Average");
	    JButton prefixButton = new JButton("Prefix Strings");
	    JButton uniqueSquaresButton = new JButton("Unique Squares");
	    JButton lastElementButton = new JButton("Get Last Element");
	    JButton sumEvenButton = new JButton("Sum of Even");
	    JButton stringToMapButton = new JButton("String to Map");

	    // Action Listeners
	    averageButton.addActionListener(e -> processAverage(averageInput));
	    prefixButton.addActionListener(e -> processPrefix(prefixInput));
	    uniqueSquaresButton.addActionListener(e -> processUniqueSquares(uniqueSquaresInput));
	    lastElementButton.addActionListener(e -> processLastElement(lastElementInput));
	    sumEvenButton.addActionListener(e -> processSumEven(sumEvenInput));
	    stringToMapButton.addActionListener(e -> processStringToMap(stringToMapInput));

	    // Add components to the panel
	    inputPanel.add(averageLabel);
	    inputPanel.add(averageInput);
	    inputPanel.add(averageButton);
	    inputPanel.add(prefixLabel);
	    inputPanel.add(prefixInput);
	    inputPanel.add(prefixButton);
	    inputPanel.add(uniqueSquaresLabel);
	    inputPanel.add(uniqueSquaresInput);
	    inputPanel.add(uniqueSquaresButton);
	    inputPanel.add(lastElementLabel);
	    inputPanel.add(lastElementInput);
	    inputPanel.add(lastElementButton);
	    inputPanel.add(sumEvenLabel);
	    inputPanel.add(sumEvenInput);
	    inputPanel.add(sumEvenButton);
	    inputPanel.add(stringToMapLabel);
	    inputPanel.add(stringToMapInput);
	    inputPanel.add(stringToMapButton);


	    outputAreaTask4 = new JTextArea();
	    outputAreaTask4.setEditable(false);

	    panel.add(inputPanel, BorderLayout.NORTH);
	    panel.add(new JScrollPane(outputAreaTask4), BorderLayout.CENTER);
	}
	private void processAverage(JTextField input) {
	    try {
	        List<Integer> numbers = parseIntegers(input.getText(), ",");
	        double average = StreamOperations.average(numbers);
	        outputAreaTask4.setText("Average: " + average);
	    } catch (Exception ex) {
	        outputAreaTask4.setText("Error: Invalid input for average. " + ex.getMessage());
	    }
	}

	private void processPrefix(JTextField input) {
	    try {
	        List<String> strings = parseStrings(input.getText(), ",");
	        List<String> prefixedStrings = StreamOperations.prefixStrings(strings);
	        outputAreaTask4.setText("Prefixed Strings: " + prefixedStrings);
	    } catch (Exception ex) {
	        outputAreaTask4.setText("Error: Invalid input for prefix. " + ex.getMessage());
	    }
	}

	private void processUniqueSquares(JTextField input) {
	    try {
	        List<Integer> numbers = parseIntegers(input.getText(), ",");
	        List<Integer> uniqueSquares = StreamOperations.squaresOfUnique(numbers);
	        outputAreaTask4.setText("Unique Squares: " + uniqueSquares);
	    } catch (Exception ex) {
	        outputAreaTask4.setText("Error: Invalid input for unique squares. " + ex.getMessage());
	    }
	}

	private void processLastElement(JTextField input) {
	    try {
	        List<Integer> numbers = parseIntegers(input.getText(), ",");
	        int lastElement = StreamOperations.getLastElement(numbers);
	        outputAreaTask4.setText("Last Element: " + lastElement);
	    } catch (NoSuchElementException ex) {
	        outputAreaTask4.setText("Error: Input list is empty.");
	    } catch (Exception ex) {
	        outputAreaTask4.setText("Error: Invalid input for last element. " + ex.getMessage());
	    }
	}

	private void processSumEven(JTextField input) {
	    try {
	        int[] numbers = Arrays.stream(input.getText().split("\\s+"))
	                .mapToInt(Integer::parseInt)
	                .toArray();
	        int sum = StreamOperations.sumOfEven(numbers);
	        outputAreaTask4.setText("Sum of Even: " + sum);
	    } catch (NumberFormatException ex) {
	        outputAreaTask4.setText("Error: Invalid input for sum of even.");
	    }
	}

	private void processStringToMap(JTextField input) {
	    try {
	        List<String> strings = parseStrings(input.getText(), ",");
	        Map<Character, String> resultMap = StreamOperations.stringToMap(strings);
	        outputAreaTask4.setText("String to Map: " + resultMap);
	    } catch (Exception ex) {
	        outputAreaTask4.setText("Error: Invalid input for string to map. " + ex.getMessage());
	    }
	}


	// Helper methods for parsing input
	private List<Integer> parseIntegers(String input, String delimiter) {
	    return Arrays.stream(input.split(delimiter))
	            .map(String::trim)
	            .map(Integer::parseInt)
	            .collect(Collectors.toList());
	}

	private List<String> parseStrings(String input, String delimiter) {
	    return Arrays.stream(input.split(delimiter))
	            .map(String::trim)
	            .collect(Collectors.toList());
	}
}
