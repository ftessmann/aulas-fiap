package ccr.utils;

import java.util.Random;

/**
 * Classe utilitária para geração de emails aleatórios
 */
public class EmailGenerator {

    private static final Random random = new Random();
    private static final String CARACTERES_PERMITIDOS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * Gera um email aleatório com 10 caracteres seguidos de @mail.com
     * @return Email aleatório
     */
    public static String gerarEmail() {
        StringBuilder email = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(CARACTERES_PERMITIDOS.length());
            email.append(CARACTERES_PERMITIDOS.charAt(index));
        }

        email.append("@mail.com");

        return email.toString();
    }
}
