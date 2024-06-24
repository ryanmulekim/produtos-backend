package estagio.projeto.domain.user;

import jakarta.persistence.Column;

public record ProdutoDTO(String nome, String descricao, Double preco, Integer quantidade, Boolean disponivel) {
}
