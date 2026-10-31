package br.com.hexagonal.domain.ports.out.soap;

import br.com.hexagonal.domain.entities.Fatura;

public interface NotaSoap {
  String emitirNota(Fatura fatura);
}
