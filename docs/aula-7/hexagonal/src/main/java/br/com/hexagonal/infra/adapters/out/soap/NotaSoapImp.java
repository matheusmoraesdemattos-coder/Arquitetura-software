package br.com.hexagonal.infra.adapters.out.soap;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import br.com.hexagonal.domain.entities.Fatura;
import br.com.hexagonal.domain.ports.out.soap.NotaSoap;

@Component
public class NotaSoapImp implements NotaSoap {

  @Override
  public String emitirNota(Fatura fatura) {
    System.out.println("[SOAP] Enviando envelope da nota do pedido " + fatura.getPedidoId());

    StringBuilder chaveAcesso = new StringBuilder();
    for (int i = 0; i < 44; i++) {
      chaveAcesso.append(ThreadLocalRandom.current().nextInt(10));
    }

    System.out.println("[SOAP] Nota emitida. Chave de acesso (44 dígitos): " + chaveAcesso);
    return chaveAcesso.toString();
  }

}
