package com.speedmenu.tablet.core.navigation;

/**
 * Sealed class que define todas as rotas de navegação da aplicação.
 * Cada objeto representa uma tela do aplicativo.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0006\u0007\b\t\n\u000b\fB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0006\r\u000e\u000f\u0010\u0011\u0012\u00a8\u0006\u0013"}, d2 = {"Lcom/speedmenu/tablet/core/navigation/Screen;", "", "route", "", "(Ljava/lang/String;)V", "getRoute", "()Ljava/lang/String;", "Cart", "Home", "Placeholder", "ProductDetail", "Products", "Splash", "Lcom/speedmenu/tablet/core/navigation/Screen$Cart;", "Lcom/speedmenu/tablet/core/navigation/Screen$Home;", "Lcom/speedmenu/tablet/core/navigation/Screen$Placeholder;", "Lcom/speedmenu/tablet/core/navigation/Screen$ProductDetail;", "Lcom/speedmenu/tablet/core/navigation/Screen$Products;", "Lcom/speedmenu/tablet/core/navigation/Screen$Splash;", "app_debug"})
public abstract class Screen {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    
    private Screen(java.lang.String route) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    /**
     * Tela de pedido/carrinho
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/speedmenu/tablet/core/navigation/Screen$Cart;", "Lcom/speedmenu/tablet/core/navigation/Screen;", "()V", "app_debug"})
    public static final class Cart extends com.speedmenu.tablet.core.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.speedmenu.tablet.core.navigation.Screen.Cart INSTANCE = null;
        
        private Cart() {
        }
    }
    
    /**
     * Tela Home principal do aplicativo
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/speedmenu/tablet/core/navigation/Screen$Home;", "Lcom/speedmenu/tablet/core/navigation/Screen;", "()V", "app_debug"})
    public static final class Home extends com.speedmenu.tablet.core.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.speedmenu.tablet.core.navigation.Screen.Home INSTANCE = null;
        
        private Home() {
        }
    }
    
    /**
     * Tela placeholder para desenvolvimento futuro
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/speedmenu/tablet/core/navigation/Screen$Placeholder;", "Lcom/speedmenu/tablet/core/navigation/Screen;", "()V", "app_debug"})
    public static final class Placeholder extends com.speedmenu.tablet.core.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.speedmenu.tablet.core.navigation.Screen.Placeholder INSTANCE = null;
        
        private Placeholder() {
        }
    }
    
    /**
     * Tela de detalhes do prato (VerPratoScreen)
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/speedmenu/tablet/core/navigation/Screen$ProductDetail;", "Lcom/speedmenu/tablet/core/navigation/Screen;", "()V", "createRoute", "", "productId", "app_debug"})
    public static final class ProductDetail extends com.speedmenu.tablet.core.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.speedmenu.tablet.core.navigation.Screen.ProductDetail INSTANCE = null;
        
        private ProductDetail() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String productId) {
            return null;
        }
    }
    
    /**
     * Tela de Produtos/Pratos de uma categoria
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/speedmenu/tablet/core/navigation/Screen$Products;", "Lcom/speedmenu/tablet/core/navigation/Screen;", "()V", "createRoute", "", "categoryName", "app_debug"})
    public static final class Products extends com.speedmenu.tablet.core.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.speedmenu.tablet.core.navigation.Screen.Products INSTANCE = null;
        
        private Products() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String categoryName) {
            return null;
        }
    }
    
    /**
     * Tela de splash/boas-vindas
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/speedmenu/tablet/core/navigation/Screen$Splash;", "Lcom/speedmenu/tablet/core/navigation/Screen;", "()V", "app_debug"})
    public static final class Splash extends com.speedmenu.tablet.core.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.speedmenu.tablet.core.navigation.Screen.Splash INSTANCE = null;
        
        private Splash() {
        }
    }
}