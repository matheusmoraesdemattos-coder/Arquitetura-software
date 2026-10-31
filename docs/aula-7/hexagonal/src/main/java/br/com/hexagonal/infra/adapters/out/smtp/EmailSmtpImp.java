package br.com.hexagonal.infra.adapters.out.smtp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.stereotype.Component;

import br.com.hexagonal.domain.entities.Fatura;
import br.com.hexagonal.domain.ports.out.smtp.EmailSmtp;

@Component
public class EmailSmtpImp implements EmailSmtp {

  @Override
  public String dispararEmail(Fatura fatura) {
    String conteudo = "Pedido " + fatura.getPedidoId() + " faturado no valor de R$ " + fatura.getValor();
    System.out.println("[SMTP] Enviando e-mail do pedido " + fatura.getPedidoId());

    String hash = gerarHash(conteudo);

    System.out.println("[SMTP] E-mail enviado. Hash do conteúdo: " + hash);
    return hash;
  }

  private String gerarHash(String conteudo) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] bytes = digest.digest(conteudo.getBytes(StandardCharsets.UTF_8));
      StringBuilder hash = new StringBuilder();
      for (byte b : bytes) {
        hash.append(String.format("%02x", b));
      }
      return hash.toString();
    } catch (Exception e) {
      throw new RuntimeException("Erro ao gerar o hash do e-mail", e);
    }
  }

}
