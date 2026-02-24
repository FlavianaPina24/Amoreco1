package br.com.eagro.api;

import java.util.HashMap;
import java.util.Map;

public class ValidadorDocumento {

    private static final int[] PESOS_CNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final Map<Character, Integer> TABELA_VALORES_ALFA = new HashMap<>();

    static {
        char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int i = 0; i < letras.length; i++) {
            TABELA_VALORES_ALFA.put(letras[i], 17 + i);
        }
    }

    private static String removeMascara(String doc) {
        return doc.replaceAll("[./-]", "").toUpperCase();
    }

    private static int calculaDigito(String base) {
        int soma = 0;
        for (int i = base.length() - 1; i >= 0; i--) {
            char c = base.charAt(i);
            int valor;
            if (Character.isLetter(c)) {
                valor = TABELA_VALORES_ALFA.getOrDefault(c, 0);
            } else {
                valor = Character.getNumericValue(c);
            }
            int peso = PESOS_CNPJ[PESOS_CNPJ.length - base.length() + i];
            soma += valor * peso;
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    public static boolean isCnpjValido(String cnpj) {
        cnpj = removeMascara(cnpj);
        if (cnpj.length() != 14) {
            return false;
        }

        String base = cnpj.substring(0, 12);
        String dvInformado = cnpj.substring(12);

        int dv1 = calculaDigito(base);
        int dv2 = calculaDigito(base + dv1);

        String dvCalculado = String.valueOf(dv1) + String.valueOf(dv2);

        return dvInformado.equals(dvCalculado);
    }
}
