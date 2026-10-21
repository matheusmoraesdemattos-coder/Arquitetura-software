package br.com.hexagonal.application;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.hexagonal.domain.entities.Fatura;
import br.com.hexagonal.domain.entities.in.FaturaService;
import br.com.hexagonal.domain.entities.out.messaging.BoletoMessaging;
import br.com.hexagonal.domain.entities.out.persistence.FatuRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaturaServiceImp implements FaturaService {

    private final FatuRepository faturaRepository;
    private final BoletoMessaging boletoMessaging;

    @Override
    public UUID faturarPedido(long pedidoID, BigDecimal valor) {
        Fatura fatura = Fatura.criar(pedidoID, valor);
        String codigoBarras = boletoMessaging.emitirBoleto(fatura);
        fatura.inserirBoleto(codigoBarras);
        //TODO: GERAR UM BOLETO
        //TODO: EMITIR NOTA
        //TODO: DISPARAR EMAIL COM BOLETO E NOTA
        faturaRepository.salvar(fatura);
        return fatura.getId();
    }
}