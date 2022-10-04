package com.example.requisicaoemprestimo.unitarios;

import com.example.requisicaoemprestimo.domain.models.Emprestimo;
import com.example.requisicaoemprestimo.domain.models.Parcela;
import com.example.requisicaoemprestimo.domain.models.ResultadoAnalise;
import com.example.requisicaoemprestimo.domain.models.ResultadoTesouraria;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static com.example.requisicaoemprestimo.unitarios.EmprestimoTestsFixture.*;

import java.text.DecimalFormat;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoTests {
    @Test
    public void testParcelas(){
        // ARRANGE
        //TODO: FAÇA USO DO FIXTURE
        //TODO: Crie uma classe Emprestimo com valor 100 e quantidade de parcelas 12
        Emprestimo emprestimo = emprestimoAprovado(100, 12);
        // ACT
        //TODO: Recupere as parcelas do emprestimo
        Optional<Parcela[]> parcelas = emprestimo.getParcelas();

        // ASSERTS
        //TODO: Validar se existe parcelas
        assertNotNull(parcelas);
        //TODO: Validar o valor total de empréstimo é 106.50
        assertEquals(106.50, emprestimo.getValorTotalEmprestimo());
        //TODO: Validar se o número de parcelas é 12
        assertEquals(12, emprestimo.getQuantidadeParcelasSolicitadas());
    }

    @Test
    public void testeAnaliseDeCreditoInvalida(){
        // ARRANGE
        //TODO: FAÇA USO DO FIXTURE
        //TODO: Crie uma classe Emprestimo com valor 100 e quantidade de parcelas 12
        Emprestimo emprestimo = emprestimoAprovado(100, 12);


        // ACT
        //TODO: Crie uma análise de crédito rejeitando a proposta
        String[] resultado = {"reprovado"};
        ResultadoAnalise resultadoAnalise = buildResultadoAnalise(false, resultado);
        //TODO: Associe or resulado ao emprestimo
        emprestimo.setResultadoAnalise(resultadoAnalise);
        // ASSERTS
        //TODO: Validar que o  emprestimo NÃO está aprovado
        assertTrue(!emprestimo.isEmprestimoFoiAprovado());
    }

    @Test
    public void testeResultadoDaTesourariaInvalida(){
        // ARRANGE
        //TODO: FAÇA USO DO FIXTURE
        //TODO: Crie uma classe Emprestimo com valor 100 e quantidade de parcelas 12
        Emprestimo emprestimo = emprestimoAprovado(100, 12);

        // ACT
        //TODO: Crie uma solicitação para tesouraria rejeitando a proposta
        ResultadoTesouraria resultadoTesouraria = buildResultadoTesouraria(false, "fichado", emprestimo);
        //TODO: Associe o resultado ao emprestimo
        emprestimo.setResultadoTesouraria(resultadoTesouraria);

        // ASSERTS
        //TODO: Validar que o  emprestimo NÃO está aprovado
        assertTrue(!emprestimo.isEmprestimoFoiAprovado());
    }

}