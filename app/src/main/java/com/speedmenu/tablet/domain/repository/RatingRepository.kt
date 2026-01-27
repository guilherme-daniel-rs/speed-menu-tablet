package com.speedmenu.tablet.domain.repository

/**
 * Interface do repositório de avaliações.
 * Define os contratos para envio de avaliações do local.
 * A implementação concreta fica na camada de dados.
 */
interface RatingRepository {
    /**
     * Envia uma avaliação do local.
     * @param rating Nota de 1 a 5 estrelas
     * @param comment Comentário opcional do usuário
     * @return Result indicando sucesso ou falha
     */
    suspend fun submitRating(rating: Int, comment: String): Result<Unit>
}

