package estagio.projeto.service;

import estagio.projeto.model.Produtos;

import java.util.List;

public interface ProdutoService {
     List<Produtos> getProdutosOrdenadosPorPreco();
}
