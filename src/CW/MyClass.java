package CW;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// Определение аннотации Repeat с целочисленным параметром value
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Repeat {
    int value();
}

// Класс с различными методами
public class MyClass {
    // Публичный метод
    public void publicMethod(int a) {
        System.out.println("Public method called with: " + a);
    }

    // Защищенный метод с аннотацией Repeat
    @Repeat(value = 3)
    protected void protectedMethod(String s) {
        System.out.println("Protected method called with: " + s);
    }

    // Приватный метод с аннотацией Repeat
    @Repeat(value = 2)
    private void privateMethod(double d) {
        System.out.println("Private method called with: " + d);
    }

    // Метод для вызова аннотированных методов

    // Метод для создания аргументов в зависимости от параметров метода
    private Object[] createArguments(Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] args = new Object[paramTypes.length];

        for (int parameterId = 0; parameterId < paramTypes.length; parameterId++) {
            Class<?> parameterType = paramTypes[parameterId];
            if (parameterType == int.class) {
                args[parameterId] = 5; // Пример значения для типа int
            } else if (parameterType == double.class) {
                args[parameterId] = 7.4; // Пример значения для типа double
            } else if (parameterType == float.class) {
                args[parameterId] = 8.3f; // Пример значения для типа float
            } else if (parameterType == boolean.class) {
                args[parameterId] = true; // Пример значения для типа boolean
            } else if (parameterType == String.class) {
                args[parameterId] = "Lalala"; // Пример значения для типа String
            } else {
                throw new RuntimeException("Parameter type " + parameterType.getName() + " not supported!");
            }
        }
        
        return args;
    }
}
