**Funcionalidade:** Adicionar um Novo Item ao Estoque

**Cenário:** Adicionar um novo item ao estoque com informações válidas

**Dado** que o usuário está logado no sistema como um administrador
**E** que o usuário está na página de gerenciamento de estoque
**Quando** o usuário preencher o formulário de adição de item com as seguintes informações:

- Nome do Item: Parafuso
- Quantidade: 100 unidades
- Categoria: Ferragens

**E** o usuário clicar no botão "Adicionar Item"
**Então** o sistema deve exibir uma mensagem de confirmação informando que o item foi adicionado com sucesso
**E** o sistema deve atualizar a lista de itens no estoque para incluir o novo item com as informações fornecidas
**E** a quantidade total de itens no estoque deve ser atualizada para refletir a adição bem-sucedida

**Cenário:** Tentar adicionar um novo item ao estoque com informações inválidas

**Dado** que o usuário está logado no sistema como um administrador
**E** que o usuário está na página de gerenciamento de estoque
**Quando** o usuário preencher o formulário de adição de item com informações faltando ou inválidas
**E** o usuário clicar no botão "Adicionar Item"
**Então** o sistema deve exibir uma mensagem de erro indicando que informações válidas são necessárias
**E** o sistema não deve adicionar um novo item ao estoque
**E** a lista de itens no estoque e a quantidade total de itens não devem ser alteradas

## Funcionalidade: Acautelar um ou mais equipamentos

**Cenário:** Criar um acautelamento com informações válidas

**Dado** que o usuário está logado no sistema como um administrador
**E** que o usuário está na página de criação de acautelamento
**Quando** o usuário preencher o formulário de criação de Acautelamento com as seguintes informações:

- Matricula do armeiro
- Matricula do Guarda Municipal
- Equipamentos selecionados
- Quantidade de cada equipamento selecionado

**E** o usuário clicar no botão "Criar acautelamento"
**Então** o sistema deve exibir uma mensagem de confirmação informando que acautelamento foi criado com sucesso.
**E** o sistema deve atualizar a lista de acautelamentos do dia atual
**E** a quantidade total de cada equipamento selectionado deve ser atualizada no estoque.

**Cenário:** Tentar adicionar um novo item ao estoque com informações inválidas

**Dado** que o usuário está logado no sistema como um administrador
**E** que o usuário está na página de criação de acautelamento
**Quando** o usuário preencher o formulário com informações faltando ou inválidas
**E** o usuário clicar no botão "Criar acautelamento"
**Então** o sistema deve exibir uma mensagem de erro indicando que informações válidas são necessárias
**E** o sistema não deve criar um novo acautelamento
**E** a lista de acautelamentos do dia atual não deve ser atualizada, nem o estoque deve ser atualizado
