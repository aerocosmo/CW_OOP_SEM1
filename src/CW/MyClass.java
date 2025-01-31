package CW;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// Аннотация Repeat с параметром value
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Repeat {
    int value();
}

// Класс MyClass с методами, помеченными @Repeat
public class MyClass {

    // Пример публичного метода (не используется аннотация)
    public void publicMethod(int a) {
        System.out.println("Public method called with: " + a);
    }

    // Методы, использующие аннотацию Repeat
    @Repeat(value = 3)
    protected void protectedMethod(String s) {
        System.out.println("Protected method called with: " + s);
    }

    @Repeat(value = 2)
    private void privateMethod(double d) {
        System.out.println("Private method called with: " + d);
    }

    // Добавляем статический метод invokeAnnotatedMethods
    public static String invokeAnnotatedMethods(Object obj) throws IllegalAccessException, InvocationTargetException {
        StringBuilder output = new StringBuilder();
        Method[] methods = obj.getClass().getDeclaredMethods(); // Получаем все методы объекта

        // Проходимся по всем методам и проверяем наличие аннотации Repeat
        for (Method method : methods) {
            if (method.isAnnotationPresent(Repeat.class)) {
                Repeat repeatAnnotation = method.getAnnotation(Repeat.class);
                int repeatCount = repeatAnnotation.value(); // Получаем значение параметра аннотации
                method.setAccessible(true); // Делаем метод доступным для вызова

                // Вызываем метод указанное количество раз
                for (int i = 0; i < repeatCount; i++) {
                    if (method.getParameterCount() == 1) { // Проверяем, принимает ли метод один параметр
                        Object[] args = createArgumentsForMethod(method); // Создаем аргументы для вызова метода
                        method.invoke(obj, args); // Вызываем метод
                    }
                }

                // Добавляем описание вызова метода в результат
                output.append("Method ").append(method.getName())
                      .append(" invoked ").append(repeatCount).append(" times.\n");
            }
        }

        return output.toString();
    }

    // Вспомогательный метод для создания аргументов для методов
    private static Object[] createArgumentsForMethod(Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] args = new Object[paramTypes.length];

        for (int parameterId = 0; parameterId < paramTypes.length; parameterId++) {
            Class<?> parameterType = paramTypes[parameterId];
            if (parameterType == int.class) {
                args[parameterId] = 5; // Значение для int
            } else if (parameterType == double.class) {
                args[parameterId] = 7.4; // Значение для double
            } else if (parameterType == float.class) {
                args[parameterId] = 8.3f; // Значение для float
            } else if (parameterType == boolean.class) {
                args[parameterId] = true; // Значение для boolean
            } else if (parameterType == String.class) {
                args[parameterId] = "Lalala"; // Значение для String
            }else if (parameterType == char.class) {
                args[parameterId] = 'x'; // Значение для char
            } else {
                throw new RuntimeException("Parameter type " + parameterType.getName() + " not supported!");
            }
        }
        return args;
    }
}
