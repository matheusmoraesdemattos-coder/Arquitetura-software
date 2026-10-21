package br.com.hexagonal.domain.entities.out.persistence;

import br.com.hexagonal.domain.entities.Fatura;

public interface FatuRepository {

    void salvar(Fatura fatura);
}