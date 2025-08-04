package cartas.aula.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class AnnotationProcessor {
    /**    * Lê valores do console para cada campo anotado com @DataField.    */
    public static void processDataFields(Object target) {
        Class<?> clazz = target.getClass();
        Scanner scanner = new Scanner(System.in);
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(DataField.class)) {
                // Obtém a anotação
                 DataField dataFieldAnn = field.getAnnotation(DataField.class);
                 String prompt = dataFieldAnn.prompt();
                // Exibe o prompt para o usuário
                 System.out.print(prompt.isEmpty() ? "Digite o valor: " : prompt);
                // Lê o valor como String do console
                String inputValue = scanner.nextLine();
                // Torna o campo acessível (caso seja privado)
                field.setAccessible(true);
                try {
                    // Precisamos converter a string 'inputValue' para o tipo do campo
                    Class<?> fieldType = field.getType();
                    Object convertedValue = convertStringToFieldType(inputValue, fieldType);
                    // Define o valor convertido no campo
                    field.set(target, convertedValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**    * Invoca qualquer método anotado com @DisplayName e faz a exibição formatada.    */
    public static void processDisplayNameMethods(Object target) {
        Class<?> clazz = target.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(DisplayName.class)) {
                DisplayName displayNameAnn = method.getAnnotation(DisplayName.class);
                String formatValue = displayNameAnn.value();
                try {
                    method.setAccessible(true);
                    // Invoca o método anotado
                    Object returnValue = method.invoke(target);
                    // Se o método retorna um array de Object, imprimimos com varargs
                    if (returnValue instanceof Object[]) {
                        System.out.printf(formatValue, (Object[]) returnValue);
                        System.out.println();
                    } else {
                        // Se for um valor único
                        System.out.printf(formatValue, returnValue);
                        System.out.println();
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**    * Método auxiliar para converter a string lida no console em diferentes tipos de campo.    */
    private static Object convertStringToFieldType(String input, Class<?> fieldType) {
        if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
            return Integer.parseInt(input);
            // Exemplo simples (sem tratamento de erro)
            } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                return Double.parseDouble(input);
            } else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
                return Long.parseLong(input);
            } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                return Boolean.parseBoolean(input);       }
            // Caso padrão: tratar como String
        return input;   }}