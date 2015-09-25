# diguiche
Aplicativo para guiche digital baseado em WEB

Trata-se de um aplicativo WEB para atender a necessidade de um guichê digital-web de geração de senha para atendimento.

O projeto nasceu de um teste para aplicação de uma vaga de emprego e porque não se tornar público?!

Composto de 4 interfaces:

- Display: Exibe as senhas que são chamadas. -display.html
- Terminal: Para emissão de senhas.  -terminal.html
- Agente: Para chamar as senhas (mesas).  -agent.html
- Admin: Interface do administrador para criação de dispositivos e monitoramento. /adm/admin.jsp

As interfaces foram desenvolvidas em HTML+Javascript(JQuery-Ajax), não havendo necessidade de estarem hospedadas em um servidor WEB.

Os clientes(interfaces) sincronizam dados através de chamadas de serviço Web, trafegando mensagens de texto puro e JSON, chamando páginas em ws/*.jsp.

### Pre-Requisitos

- Servidor JSP (qualquer que implemente servlets-api 2.1 ou superior)
- Banco de dados Postgre 9.3 (pode ser 8.1 ou superior mas deve trocar o JAR JDBC - incluso 9.3)
- JDBC4 (Driver para PGSQL)

### Instalação

A release contém um arquivo WAR compilado com as configurações padrão.

Para modificar a configuração de conexão com o banco de dados, nesse caso será necessário gerar novo Buil, senão os caminhos padrão podem ser utilizados (localhost:5432).
O Arquivo de configuração é o WEB.xml, parâmetros de contexto.

1. Carregar o arquivo deguiche.war(compilado) no seu servidor WEB, ou realizar a compilação e Build pelo Eclipse (J2E) o arquivo de projeto já mapeia todas as dependências, basta importar o projeto para o Eclipse.

2. Acesse a página config.jsp. Ela oferece um diagnóstico rápido do ambiente e permite a configuração inicial e criação de role-usuario-tabelas no banco de dados automaticamente.

3. Remova a página config.jsp e o diretório configscripts para não deixar desprotegidas informações do sistema.

4. Configure uma senha para o diretório /adm

5. Acesse /adm/admin.jsp para criar dispositivos (sem eles não é possível atribuir identificadores para display, terminal e agente-mesas)


### Arquitetura



### Video Tutorial




