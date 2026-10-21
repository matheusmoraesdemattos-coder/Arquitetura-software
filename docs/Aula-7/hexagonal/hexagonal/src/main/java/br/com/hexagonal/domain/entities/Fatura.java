package br.com.hexagonal.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Fatura {
    private UUID id;
    private Long pedidoId;
    private BigDecimal valor;
    private LocalDateTime emitidoEm;
    private String codigoBarras;
    private String rps;
    private boolean hasEmail;

    @Builder 

  private Fatura(
      UUID id,
   Long pedidoId,
  BigDecimal valor,
LocalDateTime emitidoEm,
  String codigoBarras,
   String rps,
 boolean hasEmail

)
 {
    if(pedidoId== null || pedidoId <0) {
        throw new RuntimeException( "Afatura precisa de um pedido!");
    }
    if(valor == null || valor.compareTo(BigDecimal.ZERO) <0 ) {
        throw new RuntimeException( "o alor da fatura deve ser maior que zero!");
    }
        this.id = id;
        this.pedidoId = pedidoId;
        this.valor = valor;
        this.emitidoEm = emitidoEm;
        this.codigoBarras = codigoBarras;
        this.rps = rps;
        this.hasEmail = hasEmail;
    }

    public static Fatura criar (Long pedidoID, BigDecimal valor){
        return Fatura
        .builder()
        .id(UUID.randomUUID())
        .pedidoId(pedidoID)
        .valor(valor)
        .build();
    }
    public void inserirBoleto(String codigoBarras){
        //REGRA DE NEGOCIO DO BOLETO
        this.codigoBarras= codigoBarras;
    }

}