package com.speedmenu.tablet.core.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utilitário para formatação de moeda em padrão brasileiro (pt-BR).
 * Sempre usa vírgula como separador decimal e 2 casas decimais.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\nR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/speedmenu/tablet/core/utils/CurrencyFormatter;", "", "()V", "currencyFormat", "Ljava/text/NumberFormat;", "kotlin.jvm.PlatformType", "formatCurrencyBR", "", "value", "Ljava/math/BigDecimal;", "", "app_debug"})
public final class CurrencyFormatter {
    private static final java.text.NumberFormat currencyFormat = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.speedmenu.tablet.core.utils.CurrencyFormatter INSTANCE = null;
    
    private CurrencyFormatter() {
        super();
    }
    
    /**
     * Formata um valor Double como moeda brasileira.
     * Exemplo: 68.90 -> "R$ 68,90"
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatCurrencyBR(double value) {
        return null;
    }
    
    /**
     * Formata um valor BigDecimal como moeda brasileira.
     * Exemplo: BigDecimal("68.90") -> "R$ 68,90"
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatCurrencyBR(@org.jetbrains.annotations.NotNull()
    java.math.BigDecimal value) {
        return null;
    }
}