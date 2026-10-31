package br.com.hexagonal.domain.ports.out.smtp;

import br.com.hexagonal.domain.entities.Fatura;

public interface EmailSmtp {
  String dispararEmail(Fatura fatura);
}
