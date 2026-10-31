package br.com.mvc.controller;

import java.math.BigInteger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mvc.model.service.ProductService;
import br.com.mvc.model.service.VendaService;

@Controller
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;
    private final ProductService productService;

    public VendaController(VendaService vendaService, ProductService productService) {
        this.vendaService = vendaService;
        this.productService = productService;
    }

    @GetMapping
    public String read(Model model) {
        model.addAttribute("pedidos", vendaService.read());
        model.addAttribute("products", productService.read());
        return "vendas";
    }

    @PostMapping
    public String fechar(@RequestParam BigInteger productId, @RequestParam int quantidade) {
        vendaService.fecharPedido(productId, quantidade);
        return "redirect:/vendas";
    }
}
