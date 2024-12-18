package CW;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// ����������� ��������� Repeat � ������������� ���������� value
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Repeat {
    int value();
}

// ����� � ���������� ��������
public class MyClass {
    // ��������� �����
    public void publicMethod(int a) {
        System.out.println("Public method called with: " + a);
    }

    // ���������� ����� � ���������� Repeat
    @Repeat(value = 3)
    protected void protectedMethod(String s) {
        System.out.println("Protected method called with: " + s);
    }

    // ��������� ����� � ���������� Repeat
    @Repeat(value = 2)
    private void privateMethod(double d) {
        System.out.println("Private method called with: " + d);
    }

    // ����� ��� ������ �������������� �������

    // ����� ��� �������� ���������� � ����������� �� ���������� ������
    private Object[] createArguments(Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] args = new Object[paramTypes.length];

        for (int parameterId = 0; parameterId < paramTypes.length; parameterId++) {
            Class<?> parameterType = paramTypes[parameterId];
            if (parameterType == int.class) {
                args[parameterId] = 5; // ������ �������� ��� ���� int
            } else if (parameterType == double.class) {
                args[parameterId] = 7.4; // ������ �������� ��� ���� double
            } else if (parameterType == float.class) {
                args[parameterId] = 8.3f; // ������ �������� ��� ���� float
            } else if (parameterType == boolean.class) {
                args[parameterId] = true; // ������ �������� ��� ���� boolean
            } else if (parameterType == String.class) {
                args[parameterId] = "Lalala"; // ������ �������� ��� ���� String
            } else {
                throw new RuntimeException("Parameter type " + parameterType.getName() + " not supported!");
            }
        }
        
        return args;
    }
}
