package com.speedmenu.tablet.ui.screens.home

/**
 * Arquivo com diferentes variações de mockups para testar o menu lateral do fluxo de pedidos.
 * 
 * Cenários de teste:
 * 1. Menu com poucos tópicos e muitas categorias
 * 2. Menu com muitos tópicos e poucas categorias
 * 3. Menu com categorias longas (nomes grandes)
 * 4. Menu com categoria selecionada no meio da lista
 * 5. Menu com scroll longo
 */

/**
 * Enum para identificar qual cenário de mockup usar.
 */
enum class MenuMockupScenario {
    FEW_TOPICS_MANY_CATEGORIES,      // Poucos tópicos, muitas categorias
    MANY_TOPICS_FEW_CATEGORIES,       // Muitos tópicos, poucas categorias
    LONG_CATEGORY_NAMES,              // Categorias com nomes longos
    SELECTED_IN_MIDDLE,               // Categoria selecionada no meio
    LONG_SCROLL                        // Menu com scroll longo
}

/**
 * Gera mockup de menu com poucos tópicos e muitas categorias.
 * Testa legibilidade quando há muitas opções em poucos grupos.
 */
fun createFewTopicsManyCategoriesMockup(): List<MenuTopic> {
    return listOf(
        MenuTopic(
            id = "starters",
            title = "Para começar",
            categories = listOf(
                MenuCategory("entradas", "Entradas", "starters"),
                MenuCategory("aperitivos", "Aperitivos", "starters"),
                MenuCategory("petiscos", "Petiscos", "starters"),
                MenuCategory("tira_gosto", "Tira-gosto", "starters"),
                MenuCategory("antepastos", "Antepastos", "starters"),
                MenuCategory("ceviches", "Ceviches", "starters"),
                MenuCategory("tartares", "Tartares", "starters"),
                MenuCategory("carpaccios", "Carpaccios", "starters"),
                MenuCategory("bruschettas", "Bruschettas", "starters"),
                MenuCategory("saladas", "Saladas", "starters")
            )
        ),
        MenuTopic(
            id = "main",
            title = "Pratos principais",
            categories = listOf(
                MenuCategory("pratos_principais", "Pratos Principais", "main"),
                MenuCategory("massas", "Massas", "main"),
                MenuCategory("risottos", "Risottos", "main"),
                MenuCategory("carnes", "Carnes", "main"),
                MenuCategory("peixes", "Peixes", "main"),
                MenuCategory("frangos", "Frangos", "main"),
                MenuCategory("vegetarianos", "Vegetarianos", "main"),
                MenuCategory("veganos", "Veganos", "main"),
                MenuCategory("grelhados", "Grelhados", "main"),
                MenuCategory("assados", "Assados", "main")
            )
        ),
        MenuTopic(
            id = "drinks",
            title = "Bebidas",
            categories = listOf(
                MenuCategory("bebidas", "Bebidas", "drinks"),
                MenuCategory("sucos", "Sucos", "drinks"),
                MenuCategory("refrigerantes", "Refrigerantes", "drinks"),
                MenuCategory("aguas", "Águas", "drinks"),
                MenuCategory("cervejas", "Cervejas", "drinks"),
                MenuCategory("vinhos", "Vinhos", "drinks"),
                MenuCategory("coqueteis", "Coquetéis", "drinks"),
                MenuCategory("caipirinhas", "Caipirinhas", "drinks"),
                MenuCategory("drinks_sem_alcool", "Sem Álcool", "drinks"),
                MenuCategory("drinks_especiais", "Especiais", "drinks")
            )
        )
    )
}

/**
 * Gera mockup de menu com muitos tópicos e poucas categorias.
 * Testa hierarquia visual quando há muitos grupos distintos.
 */
fun createManyTopicsFewCategoriesMockup(): List<MenuTopic> {
    return listOf(
        MenuTopic(
            id = "starters",
            title = "Para começar",
            categories = listOf(
                MenuCategory("entradas", "Entradas", "starters")
            )
        ),
        MenuTopic(
            id = "soups",
            title = "Sopas",
            categories = listOf(
                MenuCategory("sopas", "Sopas", "soups")
            )
        ),
        MenuTopic(
            id = "salads",
            title = "Saladas",
            categories = listOf(
                MenuCategory("saladas", "Saladas", "salads")
            )
        ),
        MenuTopic(
            id = "pasta",
            title = "Massas",
            categories = listOf(
                MenuCategory("massas", "Massas", "pasta")
            )
        ),
        MenuTopic(
            id = "risotto",
            title = "Risottos",
            categories = listOf(
                MenuCategory("risottos", "Risottos", "risotto")
            )
        ),
        MenuTopic(
            id = "meat",
            title = "Carnes",
            categories = listOf(
                MenuCategory("carnes", "Carnes", "meat")
            )
        ),
        MenuTopic(
            id = "fish",
            title = "Peixes",
            categories = listOf(
                MenuCategory("peixes", "Peixes", "fish")
            )
        ),
        MenuTopic(
            id = "chicken",
            title = "Frangos",
            categories = listOf(
                MenuCategory("frangos", "Frangos", "chicken")
            )
        ),
        MenuTopic(
            id = "vegetarian",
            title = "Vegetarianos",
            categories = listOf(
                MenuCategory("vegetarianos", "Vegetarianos", "vegetarian")
            )
        ),
        MenuTopic(
            id = "vegan",
            title = "Veganos",
            categories = listOf(
                MenuCategory("veganos", "Veganos", "vegan")
            )
        ),
        MenuTopic(
            id = "desserts",
            title = "Sobremesas",
            categories = listOf(
                MenuCategory("sobremesas", "Sobremesas", "desserts")
            )
        ),
        MenuTopic(
            id = "drinks",
            title = "Bebidas",
            categories = listOf(
                MenuCategory("bebidas", "Bebidas", "drinks")
            )
        )
    )
}

/**
 * Gera mockup de menu com categorias de nomes longos.
 * Testa como o layout se comporta com textos extensos.
 */
fun createLongCategoryNamesMockup(): List<MenuTopic> {
    return listOf(
        MenuTopic(
            id = "starters",
            title = "Para começar",
            categories = listOf(
                MenuCategory("entradas", "Entradas e Aperitivos Especiais da Casa", "starters"),
                MenuCategory("petiscos", "Petiscos e Tira-gostos Artesanais", "starters"),
                MenuCategory("antepastos", "Antepastos Italianos Tradicionais", "starters"),
                MenuCategory("ceviches", "Ceviches e Preparações com Frutos do Mar", "starters")
            )
        ),
        MenuTopic(
            id = "main",
            title = "Pratos principais",
            categories = listOf(
                MenuCategory("pratos_principais", "Pratos Principais com Carnes Selecionadas", "main"),
                MenuCategory("massas", "Massas Artesanais com Molhos Especiais", "main"),
                MenuCategory("risottos", "Risottos Cremosos com Ingredientes Premium", "main"),
                MenuCategory("vegetarianos", "Opções Vegetarianas e Veganas Gourmet", "main")
            )
        ),
        MenuTopic(
            id = "drinks",
            title = "Bebidas",
            categories = listOf(
                MenuCategory("bebidas", "Bebidas Artesanais e Selecionadas", "drinks"),
                MenuCategory("vinhos", "Vinhos Nacionais e Importados Premium", "drinks"),
                MenuCategory("coqueteis", "Coquetéis Clássicos e Assinatura do Bar", "drinks")
            )
        ),
        MenuTopic(
            id = "desserts",
            title = "Sobremesas",
            categories = listOf(
                MenuCategory("sobremesas", "Sobremesas Artesanais e Doces Tradicionais", "desserts"),
                MenuCategory("sorvetes", "Sorvetes Artesanais com Sabores Especiais", "desserts")
            )
        )
    )
}

/**
 * Gera mockup de menu com categoria selecionada no meio da lista.
 * Testa scroll automático e destaque visual.
 */
fun createSelectedInMiddleMockup(): List<MenuTopic> {
    return listOf(
        MenuTopic(
            id = "starters",
            title = "Para começar",
            categories = listOf(
                MenuCategory("entradas", "Entradas", "starters"),
                MenuCategory("aperitivos", "Aperitivos", "starters"),
                MenuCategory("petiscos", "Petiscos", "starters")
            )
        ),
        MenuTopic(
            id = "soups",
            title = "Sopas",
            categories = listOf(
                MenuCategory("sopas", "Sopas", "soups")
            )
        ),
        MenuTopic(
            id = "salads",
            title = "Saladas",
            categories = listOf(
                MenuCategory("saladas", "Saladas", "salads")
            )
        ),
        MenuTopic(
            id = "pasta",
            title = "Massas",
            categories = listOf(
                MenuCategory("massas", "Massas", "pasta")
            )
        ),
        MenuTopic(
            id = "main",
            title = "Pratos principais",
            categories = listOf(
                MenuCategory("pratos_principais", "Pratos Principais", "main"), // Esta será a selecionada
                MenuCategory("carnes", "Carnes", "main"),
                MenuCategory("peixes", "Peixes", "main")
            )
        ),
        MenuTopic(
            id = "desserts",
            title = "Sobremesas",
            categories = listOf(
                MenuCategory("sobremesas", "Sobremesas", "desserts")
            )
        ),
        MenuTopic(
            id = "drinks",
            title = "Bebidas",
            categories = listOf(
                MenuCategory("bebidas", "Bebidas", "drinks")
            )
        )
    )
}

/**
 * Gera mockup de menu com scroll longo.
 * Testa performance e legibilidade em listas extensas.
 */
fun createLongScrollMockup(): List<MenuTopic> {
    return listOf(
        MenuTopic(
            id = "starters",
            title = "Para começar",
            categories = listOf(
                MenuCategory("entradas", "Entradas", "starters"),
                MenuCategory("aperitivos", "Aperitivos", "starters"),
                MenuCategory("petiscos", "Petiscos", "starters"),
                MenuCategory("tira_gosto", "Tira-gosto", "starters"),
                MenuCategory("antepastos", "Antepastos", "starters")
            )
        ),
        MenuTopic(
            id = "soups",
            title = "Sopas",
            categories = listOf(
                MenuCategory("sopas", "Sopas", "soups"),
                MenuCategory("caldos", "Caldos", "soups"),
                MenuCategory("cremes", "Cremes", "soups")
            )
        ),
        MenuTopic(
            id = "salads",
            title = "Saladas",
            categories = listOf(
                MenuCategory("saladas", "Saladas", "salads"),
                MenuCategory("saladas_verdes", "Saladas Verdes", "salads"),
                MenuCategory("saladas_quentes", "Saladas Quentes", "salads")
            )
        ),
        MenuTopic(
            id = "pasta",
            title = "Massas",
            categories = listOf(
                MenuCategory("massas", "Massas", "pasta"),
                MenuCategory("raviolis", "Raviolis", "pasta"),
                MenuCategory("gnocchis", "Gnocchis", "pasta"),
                MenuCategory("lasanhas", "Lasanhas", "pasta")
            )
        ),
        MenuTopic(
            id = "risotto",
            title = "Risottos",
            categories = listOf(
                MenuCategory("risottos", "Risottos", "risotto"),
                MenuCategory("risottos_vegetais", "Risottos de Vegetais", "risotto"),
                MenuCategory("risottos_carnes", "Risottos com Carnes", "risotto")
            )
        ),
        MenuTopic(
            id = "meat",
            title = "Carnes",
            categories = listOf(
                MenuCategory("carnes", "Carnes", "meat"),
                MenuCategory("bovinas", "Bovinas", "meat"),
                MenuCategory("suinas", "Suínas", "meat"),
                MenuCategory("cordeiros", "Cordeiros", "meat")
            )
        ),
        MenuTopic(
            id = "fish",
            title = "Peixes",
            categories = listOf(
                MenuCategory("peixes", "Peixes", "fish"),
                MenuCategory("salmão", "Salmão", "fish"),
                MenuCategory("atum", "Atum", "fish"),
                MenuCategory("frutos_mar", "Frutos do Mar", "fish")
            )
        ),
        MenuTopic(
            id = "chicken",
            title = "Frangos",
            categories = listOf(
                MenuCategory("frangos", "Frangos", "chicken"),
                MenuCategory("frango_grelhado", "Frango Grelhado", "chicken"),
                MenuCategory("frango_assado", "Frango Assado", "chicken")
            )
        ),
        MenuTopic(
            id = "vegetarian",
            title = "Vegetarianos",
            categories = listOf(
                MenuCategory("vegetarianos", "Vegetarianos", "vegetarian"),
                MenuCategory("vegetarianos_light", "Vegetarianos Light", "vegetarian")
            )
        ),
        MenuTopic(
            id = "vegan",
            title = "Veganos",
            categories = listOf(
                MenuCategory("veganos", "Veganos", "vegan"),
                MenuCategory("veganos_raw", "Veganos Raw", "vegan")
            )
        ),
        MenuTopic(
            id = "grill",
            title = "Grelhados",
            categories = listOf(
                MenuCategory("grelhados", "Grelhados", "grill"),
                MenuCategory("grelhados_especiais", "Grelhados Especiais", "grill")
            )
        ),
        MenuTopic(
            id = "roasted",
            title = "Assados",
            categories = listOf(
                MenuCategory("assados", "Assados", "roasted")
            )
        ),
        MenuTopic(
            id = "desserts",
            title = "Sobremesas",
            categories = listOf(
                MenuCategory("sobremesas", "Sobremesas", "desserts"),
                MenuCategory("doces", "Doces", "desserts"),
                MenuCategory("sorvetes", "Sorvetes", "desserts"),
                MenuCategory("tortas", "Tortas", "desserts"),
                MenuCategory("mousses", "Mousses", "desserts")
            )
        ),
        MenuTopic(
            id = "drinks",
            title = "Bebidas",
            categories = listOf(
                MenuCategory("bebidas", "Bebidas", "drinks"),
                MenuCategory("sucos", "Sucos", "drinks"),
                MenuCategory("refrigerantes", "Refrigerantes", "drinks"),
                MenuCategory("aguas", "Águas", "drinks"),
                MenuCategory("cervejas", "Cervejas", "drinks"),
                MenuCategory("vinhos", "Vinhos", "drinks"),
                MenuCategory("coqueteis", "Coquetéis", "drinks"),
                MenuCategory("caipirinhas", "Caipirinhas", "drinks")
            )
        )
    )
}

/**
 * Função helper para obter o mockup baseado no cenário desejado.
 * 
 * @param scenario Cenário de mockup a ser usado
 * @return Lista de MenuTopic com dados mockados
 */
fun getMenuMockup(scenario: MenuMockupScenario): List<MenuTopic> {
    return when (scenario) {
        MenuMockupScenario.FEW_TOPICS_MANY_CATEGORIES -> createFewTopicsManyCategoriesMockup()
        MenuMockupScenario.MANY_TOPICS_FEW_CATEGORIES -> createManyTopicsFewCategoriesMockup()
        MenuMockupScenario.LONG_CATEGORY_NAMES -> createLongCategoryNamesMockup()
        MenuMockupScenario.SELECTED_IN_MIDDLE -> createSelectedInMiddleMockup()
        MenuMockupScenario.LONG_SCROLL -> createLongScrollMockup()
    }
}

/**
 * Retorna o ID da categoria que deve estar selecionada para cada cenário.
 * Útil para testar o comportamento de scroll automático e highlight.
 */
fun getSelectedCategoryIdForScenario(scenario: MenuMockupScenario): String? {
    return when (scenario) {
        MenuMockupScenario.SELECTED_IN_MIDDLE -> "pratos_principais"
        else -> null
    }
}

/**
 * Retorna o ID da primeira categoria do primeiro tópico.
 * Usado para navegação inicial ao clicar em "Iniciar pedido".
 * 
 * @param topics Lista de tópicos do menu lateral
 * @return ID da primeira categoria do primeiro tópico, ou null se não houver categorias
 */
fun firstCategoryId(topics: List<MenuTopic>): String? {
    return topics.firstOrNull()?.categories?.firstOrNull()?.id
}

