package botaoDeAcao;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.modelcore.MGEModelException;

import java.math.BigDecimal;

public class CorrigirZerosAesquerda implements AcaoRotinaJava {
    @Override
    public void doAction(ContextoAcao contextoAcao) throws Exception {
        Registro[] linhasSelecionadas = contextoAcao.getLinhas();

        for (Registro linha : linhasSelecionadas) {
            BigDecimal codparc = (BigDecimal) linha.getCampo("CODPARC");
            String cnpjCpf = (String) linha.getCampo("CGC_CPF");
            String tipoPessoa = (String) linha.getCampo("TIPPESSOA");

            String documentoFormatado = formatarDocumento(cnpjCpf, tipoPessoa);

            JapeSession.SessionHandle hnd = null;
            try {
                hnd = JapeSession.open();

                JapeFactory.dao("Parceiro").
                        prepareToUpdateByPK(codparc)
                        .set("CGC_CPF", documentoFormatado)
                        .update();

            } catch (Exception e) {
                throw new MGEModelException("Erro: " + e);
            } finally {
                JapeSession.close(hnd);
            }

        }

    }

    public static String formatarDocumento(String cnpjCpf, String tipoPessoa) {
        if (tipoPessoa.equals("J")) {
            return formatarCNPJ(cnpjCpf);
        } else if (tipoPessoa.equals("F")) {
            return formatarCPF(cnpjCpf);
        } else {
            return cnpjCpf;
        }
    }

    private static String formatarCPF(String cpf) {
        if (cpf.length() == 11) {
            return cpf;
        }
        return String.format("%011d", Long.parseLong(cpf));
    }

    private static String formatarCNPJ(String cnpj) {
        if (cnpj.length() == 14) {
            return cnpj;
        }
        return String.format("%014d", Long.parseLong(cnpj));
    }
}
