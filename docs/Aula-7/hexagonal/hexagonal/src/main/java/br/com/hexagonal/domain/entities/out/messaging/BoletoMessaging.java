package br.com.hexagonal.domain.entities.out.messaging;

import br.com.hexagonal.domain.entities.Fatura;

public interface BoletoMessaging {
    String emitirBoleto(Fatura fatura);
}