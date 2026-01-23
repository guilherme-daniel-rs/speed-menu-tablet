package com.speedmenu.tablet.core.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

/**
 * Utilitário para formatação de moeda em padrão brasileiro (pt-BR).
 * Sempre usa vírgula como separador decimal e 2 casas decimais.
 */
object CurrencyFormatter {
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    
    init {
        // Garante sempre 2 casas decimais
        currencyFormat.minimumFractionDigits = 2
        currencyFormat.maximumFractionDigits = 2
    }
    
    /**
     * Formata um valor Double como moeda brasileira.
     * Exemplo: 68.90 -> "R$ 68,90"
     */
    fun formatCurrencyBR(value: Double): String {
        return currencyFormat.format(value)
    }
    
    /**
     * Formata um valor BigDecimal como moeda brasileira.
     * Exemplo: BigDecimal("68.90") -> "R$ 68,90"
     */
    fun formatCurrencyBR(value: BigDecimal): String {
        return currencyFormat.format(value)
    }
}

