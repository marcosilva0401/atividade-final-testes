package com.example.requisicaoemprestimo.integracao;

import com.example.requisicaoemprestimo.domain.models.Emprestimo;
import com.example.requisicaoemprestimo.domain.usecases.RequisicaoEmprestimoUseCase;
import com.example.requisicaoemprestimo.domain.models.ResultadoAnalise;
import com.example.requisicaoemprestimo.domain.models.ResultadoTesouraria;
import com.example.requisicaoemprestimo.domain.ports.IAnaliseProxy;
import com.example.requisicaoemprestimo.domain.ports.ITesourariaProxy;
import org.junit.jupiter.api.Test;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO: Use o mokito para realizar testes TOP-DOWN para criar dublês das interfaces IAnaliseProxy e ITesourariaProxy
public class RequisicaoEmprestimoUseCaseTests {

    //TODO: Setup das classes Mocks e Instância real da classe RequisicaoEmprestimoUseCase
    IAnaliseProxy iAnaliseProxy = mock(IAnaliseProxy.class);
    ITesourariaProxy iTesourariaProxy = mock(ITesourariaProxy.class);
    RequisicaoEmprestimoUseCase requisicaoEmprestimoUseCaseTests = new RequisicaoEmprestimoUseCase(iAnaliseProxy, iTesourariaProxy);


    @Test
    public void test1(){
        //TODO Fazer um teste caminho Feliz (TUDO FUNCIONA BEM)
        Emprestimo emprestimo;
        String[] resposta = {"aprovado"};
        ResultadoAnalise resultadoAnalise = new ResultadoAnalise(true, resposta);
        when(iAnaliseProxy.solicitarAnaliseDeCredito(any())).thenReturn(resultadoAnalise);
        ResultadoTesouraria resultadoTesouraria = new ResultadoTesouraria(true, "aprovado");
        when(iTesourariaProxy.solicitarLiberacaoDaTesouraria(any())).thenReturn(resultadoTesouraria);

        assertAll(()-> requisicaoEmprestimoUseCaseTests.executar(UUID.randomUUID(), 100.00, 2));
        emprestimo = requisicaoEmprestimoUseCaseTests.executar(UUID.randomUUID(), 100.00, 2);
        assertEquals(true, emprestimo.isEmprestimoFoiAprovado());
    }

    @Test
    public void test2(){
        //TODO Fazer um teste caminho INFELIZ IAnaliseProxy retornando uma Análise reprovada
        Emprestimo emprestimo;
        String[] resultado = {"reprovado"};
        ResultadoAnalise resultadoAnalise = new ResultadoAnalise(false, resultado);
        when(iAnaliseProxy.solicitarAnaliseDeCredito(any())).thenReturn(resultadoAnalise);
        ResultadoTesouraria resultadoTesouraria = new ResultadoTesouraria(false, "reprovado");
        when(iTesourariaProxy.solicitarLiberacaoDaTesouraria(any())).thenReturn(resultadoTesouraria);

        assertAll(()-> requisicaoEmprestimoUseCaseTests.executar(UUID.randomUUID(), 100.00, 2));
        emprestimo = requisicaoEmprestimoUseCaseTests.executar(UUID.randomUUID(), 100.00, 2);
        assertEquals(resultadoAnalise, emprestimo.getResultadoAnalise());
        assertEquals(false, emprestimo.isEmprestimoFoiAprovado());

    }

    @Test
    public void test3(){
        //TODO Fazer um teste caminho INFELIZ ITesourariaProxy retornando resultado reprovado
        Emprestimo emprestimo;
        String[] resposta = {"aprovado"};
        ResultadoAnalise resultadoAnalise = new ResultadoAnalise(true, resposta);
        when(iAnaliseProxy.solicitarAnaliseDeCredito(any())).thenReturn(resultadoAnalise);
        ResultadoTesouraria resultadoTesouraria = new ResultadoTesouraria(false, "reprovado");
        when(iTesourariaProxy.solicitarLiberacaoDaTesouraria(any())).thenReturn(resultadoTesouraria);

        assertAll(() -> requisicaoEmprestimoUseCaseTests.executar(UUID.randomUUID(), 100.00, 2));
        emprestimo = requisicaoEmprestimoUseCaseTests.executar(UUID.randomUUID(), 100.00, 2);
        assertEquals(resultadoTesouraria, emprestimo.getResultadoTesouraria());
        assertEquals(false, emprestimo.isEmprestimoFoiAprovado());
    }
}
}
