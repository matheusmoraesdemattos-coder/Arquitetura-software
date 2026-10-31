package br.com.mvc.model.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mvc.model.entity.Pedido;
import br.com.mvc.model.entity.Product;
import br.com.mvc.model.repository.PedidoRepository;

@Service
public class VendaService {

    private final PedidoRepository pedidoRepository;
    private final ProductService productService;
    private final HubClient hubClient;

    public VendaService(PedidoRepository pedidoRepository,
                        ProductService productService,
                        HubClient hubClient) {
        this.pedidoRepository = pedidoRepository;
        this.productService = productService;
        this.hubClient = hubClient;
    }

    public List<Pedido> read() {
        return pedidoRepository.findAll();
    }

    public Pedido fecharPedido(BigInteger productId, int quantidade) {
        Product product = productService.buscar(productId);
        BigDecimal valor = product.getPrice().multiply(BigDecimal.valueOf(quantidade));

        Pedido pedido = Pedido.builder()
                .productName(product.getName())
                .quantidade(quantidade)
                .valor(valor)
                .build();
        pedido = pedidoRepository.save(pedido);

        String faturaId = hubClient.faturar(pedido.getId().longValue(), valor);
        pedido.setFaturaId(faturaId);

        return pedidoRepository.save(pedido);
    }
}
