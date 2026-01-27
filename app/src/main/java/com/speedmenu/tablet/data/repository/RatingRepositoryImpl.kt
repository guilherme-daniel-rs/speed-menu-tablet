package com.speedmenu.tablet.data.repository

import com.speedmenu.tablet.domain.repository.RatingRepository
import javax.inject.Inject

/**
 * Implementação fake do repositório de avaliações.
 * Simula envio de avaliação com delay.
 * TODO: Substituir por implementação real usando Retrofit/OkHttp quando a API estiver disponível.
 */
class RatingRepositoryImpl @Inject constructor() : RatingRepository {

    override suspend fun submitRating(rating: Int, comment: String): Result<Unit> {
        return try {
            // Simula delay de rede (~900ms como especificado)
            kotlinx.coroutines.delay(900)
            
            // Validação básica
            if (rating < 1 || rating > 5) {
                return Result.failure(IllegalArgumentException("Rating deve estar entre 1 e 5"))
            }
            
            // Mock: sempre retorna sucesso
            // Em produção, isso será substituído por chamada ao backend
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

