public class Main {
    public static void main(String[] args) {

        String cpf = "1234567891";
        String cnpj = "1234567891234";

        String cpfFormatado = formatarDocumento(cpf, "F");
        String cnpjFormatado = formatarDocumento(cnpj, "J");

        System.out.println("CPF formatado: " + cpfFormatado);
        System.out.println("CNPJ formatado: " + cnpjFormatado);
    }

    public static String formatarDocumento(String documento, String tipmov) {
        // Verifica se o tipo de movimento é para CNPJ
        if (tipmov.equals("J")) {
            // Formata CNPJ
            return formatarCNPJ(documento);
        } else if (tipmov.equals("F")) {
            // Formata CPF
            return formatarCPF(documento);
        } else {
            // Tipo de movimento inválido
            return "Tipo de movimento inválido";
        }
    }

    private static String formatarCPF(String cpf) {
        // Verifica se o CPF já está formatado
        if (cpf.length() == 11) {
            return cpf;
        }
        // Completa com zeros à esquerda até totalizar 11 caracteres
        return String.format("%011d", Long.parseLong(cpf));
    }

    private static String formatarCNPJ(String cnpj) {
        // Verifica se o CNPJ já está formatado
        if (cnpj.length() == 14) {
            return cnpj;
        }
        // Completa com zeros à esquerda até totalizar 14 caracteres
        return String.format("%014d", Long.parseLong(cnpj));
    }
}