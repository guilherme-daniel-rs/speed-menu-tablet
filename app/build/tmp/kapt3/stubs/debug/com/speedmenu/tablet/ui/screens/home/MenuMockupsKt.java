package com.speedmenu.tablet.ui.screens.home;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\f\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u001a\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u001a\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u001a\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u001a\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u001a\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u001a\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f\u001a\u0010\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\f\u00a8\u0006\u000e"}, d2 = {"createFewTopicsManyCategoriesMockup", "", "Lcom/speedmenu/tablet/ui/screens/home/MenuTopic;", "createLongCategoryNamesMockup", "createLongScrollMockup", "createManyTopicsFewCategoriesMockup", "createSelectedInMiddleMockup", "firstCategoryId", "", "topics", "getMenuMockup", "scenario", "Lcom/speedmenu/tablet/ui/screens/home/MenuMockupScenario;", "getSelectedCategoryIdForScenario", "app_debug"})
public final class MenuMockupsKt {
    
    /**
     * Gera mockup de menu com poucos tópicos e muitas categorias.
     * Testa legibilidade quando há muitas opções em poucos grupos.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.speedmenu.tablet.ui.screens.home.MenuTopic> createFewTopicsManyCategoriesMockup() {
        return null;
    }
    
    /**
     * Gera mockup de menu com muitos tópicos e poucas categorias.
     * Testa hierarquia visual quando há muitos grupos distintos.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.speedmenu.tablet.ui.screens.home.MenuTopic> createManyTopicsFewCategoriesMockup() {
        return null;
    }
    
    /**
     * Gera mockup de menu com categorias de nomes longos.
     * Testa como o layout se comporta com textos extensos.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.speedmenu.tablet.ui.screens.home.MenuTopic> createLongCategoryNamesMockup() {
        return null;
    }
    
    /**
     * Gera mockup de menu com categoria selecionada no meio da lista.
     * Testa scroll automático e destaque visual.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.speedmenu.tablet.ui.screens.home.MenuTopic> createSelectedInMiddleMockup() {
        return null;
    }
    
    /**
     * Gera mockup de menu com scroll longo.
     * Testa performance e legibilidade em listas extensas.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.speedmenu.tablet.ui.screens.home.MenuTopic> createLongScrollMockup() {
        return null;
    }
    
    /**
     * Função helper para obter o mockup baseado no cenário desejado.
     *
     * @param scenario Cenário de mockup a ser usado
     * @return Lista de MenuTopic com dados mockados
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.speedmenu.tablet.ui.screens.home.MenuTopic> getMenuMockup(@org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.ui.screens.home.MenuMockupScenario scenario) {
        return null;
    }
    
    /**
     * Retorna o ID da categoria que deve estar selecionada para cada cenário.
     * Útil para testar o comportamento de scroll automático e highlight.
     */
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.String getSelectedCategoryIdForScenario(@org.jetbrains.annotations.NotNull()
    com.speedmenu.tablet.ui.screens.home.MenuMockupScenario scenario) {
        return null;
    }
    
    /**
     * Retorna o ID da primeira categoria do primeiro tópico.
     * Usado para navegação inicial ao clicar em "Iniciar pedido".
     *
     * @param topics Lista de tópicos do menu lateral
     * @return ID da primeira categoria do primeiro tópico, ou null se não houver categorias
     */
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.String firstCategoryId(@org.jetbrains.annotations.NotNull()
    java.util.List<com.speedmenu.tablet.ui.screens.home.MenuTopic> topics) {
        return null;
    }
}