package br.com.eagro.api;

import java.util.ArrayList;
import java.util.List;

public class MassaDeDados {

    public static List<User> getUsuariosDeTeste() {
        List<User> elenco = new ArrayList<>();

        // 1. O Certinho - CPF
        elenco.add(new User(1, "João Silva", "Maria Joaquina Silva", 30, new ContaBancaria("1234", "56789", "0"), "joao.silva@example.com", "Joca", "123.456.789-00", null));

        // 2. O Certinho - CNPJ
        elenco.add(new User(2, "Empresa Fantasia LTDA", null, 5, new ContaBancaria("5678", "12345", "1"), "contato@empresa.com", "Loja de Doces", null, "12.345.678/0001-90"));

        // 3. O CPF Curto (Deve Falhar)
        elenco.add(new User(3, "Ana Pereira", "Joana Santos Pereira", 25, new ContaBancaria("1122", "33445", "X"), "ana.pereira@example.com", "Aninha", "123.456.789", null));

        // 4. O CNPJ Curto (Deve Falhar)
        elenco.add(new User(4, "Mercado da Esquina", null, 2, new ContaBancaria("3344", "55667", "2"), "mercado@esquina.com", "Mercadinho", null, "12.345.678/0001"));

        // 5. O CPF com Letra (Deve Falhar)
        elenco.add(new User(5, "Carlos Andrade", "Lúcia Andrade", 45, new ContaBancaria("A1B2", "C3D4E", "5"), "carlos.andrade@example.com", "Carlão", "123.456.789-0A", null));

        // 6. O CNPJ com Caractere Especial (Deve Falhar)
        elenco.add(new User(6, "Oficina do Zé", null, 12, new ContaBancaria("B2C3", "D4E5F", "6"), "ze@oficina.com", "Oficina do Zé", null, "12.345.678/0001-9@"));

        // 7. O CPF sem Máscara (Deve Passar na Validação de Formato)
        elenco.add(new User(7, "Mariana Costa", "Helena Costa", 22, new ContaBancaria("C3D4", "E5F6G", "7"), "mari.costa@example.com", "Mari", "12345678900", null));

        // 8. O CNPJ sem Máscara (Deve Passar na Validação de Formato)
        elenco.add(new User(8, "Consultoria XYZ", null, 8, new ContaBancaria("D4E5", "F6G7H", "8"), "consultoria@xyz.com", "XYZ Corp", null, "12345678000190"));

        // 9. A Conta com Letras (Válido, como sugerido)
        elenco.add(new User(9, "Beatriz Lima", "Sonia Lima", 35, new ContaBancaria("12AB", "56CD", "Y"), "bia.lima@example.com", "Bia", "111.222.333-44", null));

        // 10. O CPF com Espaço (Deve Falhar)
        elenco.add(new User(10, "Ricardo Nunes", "Marta Nunes", 50, new ContaBancaria("E5F6", "G7H8I", "9"), "ricardo.nunes@example.com", "Rick", "123.456.789 00", null));

        // 13. O Bode Expiatório (Para Testar a Falha)
        elenco.add(new User(13, "Carlos Farsante", "Maria Farsante", 99, new ContaBancaria("0000", "00000", "0"), "carlos@farsa.com", "Farsante", "999.999.999-99", null));

        return elenco;
    }

    public static User getUsuarioPorId(int id) {
        return getUsuariosDeTeste().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static User getUsuarioPorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return null;
        }
        
        String documentoLimpo = documento.replaceAll("[\\s./-]+", "");

        return getUsuariosDeTeste().stream()
                .filter(user -> {
                    String cpfLimpo = user.getCpf() != null ? user.getCpf().replaceAll("[\\s./-]+", "") : null;
                    String cnpjLimpo = user.getCnpj() != null ? user.getCnpj().replaceAll("[\\s./-]+", "") : null;

                    return documentoLimpo.equals(cpfLimpo) || documentoLimpo.equals(cnpjLimpo);
                })
                .findFirst()
                .orElse(null);
    }
}
