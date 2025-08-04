package ccr.utils;

import java.util.Random;

/**
 * Classe utilitária para geração de CPFs aleatórios
 */
public class CpfGenerator {

    private static final Random random = new Random();

    /**
     * Gera um CPF aleatório com 11 dígitos
     * @param formatado se true, retorna o CPF formatado (XXX.XXX.XXX-XX)
     * @return CPF com 11 dígitos aleatórios
     */
    public static String gerarCpf(boolean formatado) {
        StringBuilder cpf = new StringBuilder();

        // Gera 11 dígitos aleatórios
        for (int i = 0; i < 11; i++) {
            cpf.append(random.nextInt(10));
        }

        if (formatado) {
            return formatarCpf(cpf.toString());
        }

        return cpf.toString();
    }

    /**
     * Formata o CPF no padrão XXX.XXX.XXX-XX
     */
    private static String formatarCpf(String cpf) {
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9);
    }
}
