package estagio.projeto.controller;


import estagio.projeto.domain.user.ProdutoDTO;
import estagio.projeto.event.RecursoCriarEvent;
import estagio.projeto.model.Produtos;
import estagio.projeto.repository.ProdutosRepository;
import estagio.projeto.service.impl.ProdutosServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.beans.Beans;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private ProdutosServiceImpl produtosService;

    @GetMapping
    public List<Produtos> listar(){
        return produtosRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Produtos> buscarPeloCodigo(@PathVariable Long codigo){
        Optional<Produtos> produtos = produtosRepository.findById(codigo);
        return produtos.isPresent() ? ResponseEntity.ok(produtos.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Produtos> criarPessoa(@Valid @RequestBody ProdutoDTO dto, HttpServletResponse response){

        Produtos newProduto = new Produtos();

        BeanUtils.copyProperties(dto, newProduto);

        Produtos pessoaSalva = produtosRepository.save(newProduto);

        publisher.publishEvent(new RecursoCriarEvent(this, response, pessoaSalva.getCodigo()));


        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Produtos> removerPessoa(@Valid @PathVariable Long codigo){
        Optional<Produtos> produtos = produtosRepository.findById(codigo);
        if (produtos.isPresent()) {
            produtosRepository.deleteById(codigo);
            return ResponseEntity.noContent().build(); // Retorna 204 NO_CONTENT ao invés de 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 NOT_FOUND se a pessoa não for encontrada
        }
    }

    @GetMapping("/preco")
    public ResponseEntity<List<Produtos>> getProdutosOrdenadosPorPreco() {
        List<Produtos> produtos = produtosService.getProdutosOrdenadosPorPreco();
        return ResponseEntity.ok(produtos);
    }

}
