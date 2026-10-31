package br.com.mvc.model.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.mvc.model.entity.Perfil;
import br.com.mvc.model.entity.Product;
import br.com.mvc.model.entity.StatusProduto;
import br.com.mvc.model.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        product.setStatus(StatusProduto.INDISPONIVEL);
        product.setQuantidade(0);
        return productRepository.save(product);
    }

    public List<Product> read() {
        return productRepository.findAll();
    }

    public Product buscar(BigInteger id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    public Product descontinuar(BigInteger id, Perfil perfil) {
        if (perfil != Perfil.ADMINISTRADOR) {
            throw new IllegalStateException("Apenas o administrador pode descontinuar um produto.");
        }
        Product product = buscar(id);
        product.setStatus(StatusProduto.DESCONTINUADO);
        return productRepository.save(product);
    }
}
