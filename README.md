# SpeedMenuTablet

Aplicativo Android para tablets em modo quiosque, onde clientes de restaurante fazem pedidos diretamente na mesa.

## 📋 Sobre o Projeto

Este é um aplicativo Android desenvolvido em Kotlin usando Jetpack Compose, seguindo a arquitetura MVVM com Clean Architecture leve. O aplicativo é focado exclusivamente na experiência do cliente, permitindo que façam pedidos diretamente na mesa através de tablets fixos.

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas dentro de um único módulo Android:

```
com.speedmenu.tablet
│
├── core                    # Funcionalidades compartilhadas
│   ├── di                  # Injeção de dependências (Hilt)
│   ├── navigation          # Navegação (Navigation Compose)
│   ├── ui                  # Componentes e tema UI
│   │   ├── theme           # Material 3 Theme
│   │   └── components      # Componentes reutilizáveis
│   ├── kiosk               # Gerenciamento de modo quiosque
│   └── utils               # Utilitários e extensões
│
├── data                    # Camada de dados
│   ├── datasource          # Fontes de dados (local e remoto)
│   ├── repository          # Implementações dos repositórios
│   └── model               # Modelos de dados (entidades)
│
├── domain                  # Camada de domínio
│   ├── model               # Modelos de negócio
│   └── repository          # Interfaces dos repositórios
│
├── ui                      # Camada de apresentação
│   ├── screens             # Telas do aplicativo
│   └── viewmodel           # ViewModels (MVVM)
│
└── MainActivity.kt         # Activity principal
```

## 🛠️ Tecnologias

- **Linguagem**: Kotlin
- **UI**: Jetpack Compose
- **Arquitetura**: MVVM com Clean Architecture leve
- **DI**: Hilt
- **Navegação**: Navigation Compose
- **Material Design**: Material 3

## 📦 Estrutura de Módulos

### Core
- **DI**: Configuração do Hilt para injeção de dependências
- **Navigation**: Rotas e grafo de navegação usando sealed classes
- **UI/Theme**: Material 3 com cores, tipografia e formas customizadas
- **Kiosk**: Gerenciador de modo quiosque (estrutura preparada)
- **Utils**: Extensões e utilitários

### Data
- **DataSources**: Interfaces e implementações para dados locais e remotos
- **Repository**: Implementações concretas dos repositórios
- **Model**: Entidades de dados com mapeamento para modelos de domínio

### Domain
- **Model**: Modelos de negócio (entidades de domínio)
- **Repository**: Interfaces que definem contratos de acesso a dados

### UI
- **Screens**: Telas Compose (atualmente apenas placeholders)
- **ViewModel**: ViewModels com estados UiState

## 🚀 Estado Atual

Este projeto contém apenas a **estrutura base**:

✅ Configuração do projeto (Gradle, Hilt, Compose)  
✅ Estrutura de pastas e arquivos  
✅ Navigation Compose configurado  
✅ Material 3 Theme configurado  
✅ ViewModels vazios com estrutura  
✅ Repositories com implementações mockadas  
✅ Dependency Injection configurado  
✅ Estrutura de Kiosk Mode preparada  

❌ Nenhuma tela funcional  
❌ Nenhuma regra de negócio implementada  
❌ Nenhum botão ou interação real  

## 📝 Próximos Passos

1. Implementar telas de cardápio
2. Implementar fluxo de pedidos
3. Conectar com API real (substituir mocks)
4. Implementar modo quiosque
5. Adicionar persistência local (Room/DataStore)
6. Implementar lógica de negócio nos ViewModels

## 🔧 Configuração

### Requisitos
- Android Studio Hedgehog ou superior
- JDK 17
- Min SDK: 26
- Target SDK: 34

### Build
```bash
./gradlew build
```

### Run
```bash
./gradlew installDebug
```

## 📄 Licença

Este projeto é privado e proprietário.
