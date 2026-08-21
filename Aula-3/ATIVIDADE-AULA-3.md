
# Arquitetura de Software – Estilos Arquiteturais

## Monolítico

**1. Conceito e definição**

É quando todo o sistema fica dentro de uma única aplicação — todo o código é compilado, testado e implantado junto, como um bloco só. Não tem separação em serviços independentes, tudo roda no mesmo processo.

**2. Casos de uso comuns**

Funciona bem pra sistemas pequenos ou médios, times enxutos e produtos no início de vida, quando ainda não vale a pena complicar a infraestrutura. Exemplos reais: a Shopify roda um monolito modular em Ruby on Rails até hoje, mesmo processando bilhões em vendas; e a maioria dos ERPs de empresa pequena/média também segue esse modelo.

**3. Principais vantagens**

Deploy simples (é um pacote só), mais fácil de debugar porque tudo tá no mesmo lugar, e o desenvolvimento inicial é mais rápido sem a complexidade de coordenar vários serviços.

**4. Principais desvantagens**

Conforme o sistema cresce, o acoplamento entre as partes aumenta e fica difícil escalar só um pedaço específico. Qualquer deploy é arriscado porque mexe no sistema inteiro, e times grandes acabam pisando no pé um do outro no mesmo código.

---

## Distribuído

**1. Conceito e definição**

O sistema roda em várias máquinas diferentes que se coordenam entre si pra funcionar como um todo só, dividindo carga de trabalho ou replicando dados.

**2. Casos de uso comuns**

Usado quando o volume de acesso é grande demais pra uma máquina só aguentar, ou quando se precisa de resiliência (o sistema não pode cair se uma máquina falhar). Exemplos reais: o Discord mantém um cluster com centenas de máquinas Elixir/Erlang pra sustentar milhões de usuários conectados ao mesmo tempo; bancos de dados com réplicas de leitura separadas da escrita também são um exemplo clássico.

**3. Principais vantagens**

Escala muito melhor que uma máquina só e ganha resiliência — se uma parte falha, o resto continua funcionando.

**4. Principais desvantagens**

Tem atraso de rede entre as máquinas, elas podem falhar no meio de uma operação, e manter os dados consistentes entre todas as partes é bem mais difícil.