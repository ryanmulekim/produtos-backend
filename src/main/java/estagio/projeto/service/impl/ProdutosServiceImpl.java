package estagio.projeto.service.impl;

import estagio.projeto.model.Produtos;
import estagio.projeto.repository.ProdutosRepository;
import estagio.projeto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutosServiceImpl implements ProdutoService {

    @Autowired
    private ProdutosRepository repository;

    @Override
    public List<Produtos> getProdutosOrdenadosPorPreco() {
            return repository.findAllByOrderByPrecoDesc();
    }
}
