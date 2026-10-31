package br.com.hexagonal.infra.adapters.out.messaging;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import br.com.hexagonal.domain.entities.Fatura;
import br.com.hexagonal.domain.ports.out.messaging.BoletoMessaging;

@Component
public class BoletoMessagingImp implements BoletoMessaging {

  @Override
  public String emitirBoleto(Fatura fatura) {
    System.out.println("[FILA] Publicando emissão de boleto do pedido " + fatura.getPedidoId());

    StringBuilder codigoBarras = new StringBuilder();
    for (int i = 0; i < 44; i++) {
      codigoBarras.append(ThreadLocalRandom.current().nextInt(10));
    }

    System.out.println("[FILA] Boleto emitido. Código de barras: " + codigoBarras);
    return codigoBarras.toString();
  }

}
