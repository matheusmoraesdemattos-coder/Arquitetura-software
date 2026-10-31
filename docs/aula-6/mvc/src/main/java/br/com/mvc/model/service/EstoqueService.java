package br.com.mvc.model.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import br.com.mvc.model.entity.Product;
import br.com.mvc.model.entity.StatusProduto;
import br.com.mvc.model.repository.ProductRepository;

@Service
public class EstoqueService {

    private final ProductRepository productRepository;

    public EstoqueService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product entrada(BigInteger productId, int quantidade) {
        validarQuantidade(quantidade);
        Product product = buscar(productId);

        if (product.getStatus() == StatusProduto.DESCONTINUADO) {
            throw new IllegalStateException("Produto descontinuado não pode receber entrada de estoque.");
        }

        product.setQuantidade(product.getQuantidade() + quantidade);

        if (product.getQuantidade() > 0) {
            product.setStatus(StatusProduto.DISPONIVEL);
        }

        return productRepository.save(product);
    }

    public Product saida(BigInteger productId, int quantidade) {
        validarQuantidade(quantidade);
        Product product = buscar(productId);

        if (product.getStatus() == StatusProduto.DESCONTINUADO) {
            throw new IllegalStateException("Produto descontinuado não pode ter saída de estoque.");
        }

        if (product.getStatus() != StatusProduto.DISPONIVEL || product.getQuantidade() < quantidade) {
            throw new IllegalStateException("Estoque insuficiente para a saída solicitada.");
        }

        product.setQuantidade(product.getQuantidade() - quantidade);

        if (product.getQuantidade() == 0) {
            product.setStatus(StatusProduto.INDISPONIVEL);
        }

        return productRepository.save(product);
    }

    private Product buscar(BigInteger id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    private void validarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
    }
}
