package br.com.hexagonal.domain.entities.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface FaturaService {
    UUID faturarPedido(long pedidoID, BigDecimal valor);
}