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

### Arquitetura



### Video Tutorial




