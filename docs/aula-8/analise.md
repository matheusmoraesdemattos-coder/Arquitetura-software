# Aula 8 — Análise da integração entre a Venda e o Hub

A aplicação de vendas (MVC) chama o hub de faturamento (arquitetura hexagonal) por **REST**, de forma **síncrona**: ao fechar um pedido, a venda envia `{ pedidoId, valor }` e espera o `faturaId` de volta para guardar no pedido.

Como são **dois sistemas separados conversando pela rede**, essa integração pode falhar. Abaixo estão dois problemas e uma solução (entre as estudadas) para cada um.

---

## Problema 1 — O hub está fora do ar ou lento

**O que acontece com o pedido:** a venda salva o pedido e faz a chamada REST, mas o hub não responde (está fora do ar) ou demora demais (timeout). A chamada dá erro, então o `faturaId` **não é preenchido**. O pedido fica salvo no banco da venda, mas **sem fatura** — os dois sistemas ficam inconsistentes.

**O que o negócio percebe:** o vendedor vê um erro ao fechar o pedido (ou uma tela travada esperando a resposta). Existe uma venda registrada que nunca foi faturada, ou seja, uma venda que não vai gerar boleto nem nota.

**Solução (estudada): mensageria assíncrona (fila).**
Em vez de chamar o hub de forma síncrona, a venda **publica o pedido em uma fila** e responde ao vendedor na hora. O hub **consome** a mensagem quando estiver disponível e processa o faturamento.

**Onde ela atua:** no ponto da comunicação entre a venda e o hub. A fila **desacopla** os dois: se o hub estiver fora do ar, a mensagem fica guardada na fila e é processada quando ele voltar, sem perder o pedido nem travar a venda. (É o mesmo padrão "por fila" que o próprio hub já usa para emitir o boleto.)

---

## Problema 2 — A resposta se perde depois que o hub já faturou

**O que acontece com o pedido:** o hub recebe a chamada, **cria a fatura normalmente**, mas a resposta com o `faturaId` se perde na volta (queda de rede ou timeout). Para a venda, a chamada "falhou". Se a venda tentar de novo (retry), o hub recebe o mesmo pedido outra vez e **cria uma segunda fatura** — agora há **duas faturas para um único pedido**.

**O que o negócio percebe:** o cliente recebe **dois boletos e duas notas** do mesmo pedido (cobrança duplicada), gerando retrabalho e reclamação.

**Solução (estudada): idempotência.**
Fazer a operação de faturar ser **idempotente**: usar o `pedidoId` como **chave única** no hub. Antes de criar, o hub verifica se já existe fatura para aquele pedido; se existir, ele **devolve a fatura já criada** em vez de criar outra.

**Onde ela atua:** dentro do hub, na criação da fatura (`FaturaServiceImp`). Assim, chamar o faturamento duas vezes com o mesmo `pedidoId` produz **sempre o mesmo resultado**, e um retry depois de uma resposta perdida não gera fatura duplicada.

---

## Resumo

| Problema | O que acontece | O que o negócio percebe | Solução | Onde atua |
|---|---|---|---|---|
| Hub fora do ar / lento | Pedido salvo sem fatura | Erro ao fechar; venda sem faturamento | Fila (mensageria assíncrona) | Entre a venda e o hub |
| Resposta perdida + retry | Fatura duplicada | Cliente recebe cobrança em dobro | Idempotência (pedidoId como chave) | Dentro do hub, ao criar a fatura |
