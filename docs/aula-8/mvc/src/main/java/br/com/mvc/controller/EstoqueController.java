package br.com.mvc.controller;

import java.math.BigInteger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mvc.model.service.EstoqueService;
import br.com.mvc.model.service.ProductService;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;
    private final ProductService productService;

    public EstoqueController(EstoqueService estoqueService, ProductService productService) {
        this.estoqueService = estoqueService;
        this.productService = productService;
    }

    @GetMapping
    public String read(Model model) {
        model.addAttribute("products", productService.read());
        return "estoque";
    }

    @PostMapping("/entrada")
    public String entrada(@RequestParam BigInteger productId, @RequestParam int quantidade) {
        estoqueService.entrada(productId, quantidade);
        return "redirect:/estoque";
    }

    @PostMapping("/saida")
    public String saida(@RequestParam BigInteger productId, @RequestParam int quantidade) {
        estoqueService.saida(productId, quantidade);
        return "redirect:/estoque";
    }
}
