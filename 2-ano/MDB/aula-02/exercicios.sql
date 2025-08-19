-- Aula 2 - 11/08/25

-- Gabarito dos exercícios

-- preparando o ambiente

set serveroutput on
set verify off

-- EXERCICIO 01 – Criar um bloco PL-SQL para calcular o valor do novo 
-- salário mínimo que deverá ser de 25% em cima do atual, que é de R$???

declare
    sal_atual number(10,2) := 1518;
    sal_reaj sal_atual%type := sal_atual * 1.25;
begin
    dbms_output.put_line('Salário mínimo atual - R$'||sal_atual);
    dbms_output.put_line('Novo salário mínimo + 25% - R$'||sal_reaj);
end;

-- EXERCÍCIO 04 – Criar um bloco PL-SQL para calcular o valor das 
-- parcelas de uma compra de um carro, nas seguintes condições: 
-- OBSERVAÇÃO: 
-- 1 - Parcelas para aquisição em 10 pagamentos. 
-- 2 - O valor total dos juros é de 3% e deverá ser aplicado sobre o 
-- montante financiado 
-- 3 – No final informar o valor de cada parcela.

declare
    v_carro number(10,2) := &preco;
    v_parc v_carro%type := v_carro * 1.03 / 10;
begin
    dbms_output.put_line('Valor do carro - R$'||v_carro);
    dbms_output.put_line('Valor das prestações em 10x - R$'||v_parc);
end;

-- EXERCÍCIO 05 – Criar um bloco PL-SQL para calcular o valor de cada 
-- parcela de uma compra de um carro, nas seguintes condições:
-- - Parcelas para aquisição em 6 pagamentos. 
-- - Parcelas para aquisição em 12 pagamentos. 
-- - Parcelas para aquisição em 18 pagamentos. 
-- OBSERVAÇÃO: 
-- 1 – Deverá ser dada uma entrada de 20% do valor da compra. 
-- 2 – Deverá ser aplicada uma taxa juros, no saldo restante, nas 
-- seguintes condições: 
-- 3 – No final informar o valor das parcelas para as 3 formas de 
-- pagamento, com o Valor de aquisição de 10.000.
-- A – Pagamento em 6 parcelas: 10%. 
-- B – Pagamento em 12 parcelas: 15%. 
-- C – Pagamento em 18 parcelas: 20%.

declare
    v_carro number(10,2) := &preco;
    v_parc v_carro%type := v_carro * 0.8;
begin
    dbms_output.put_line('Valor do carro - R$'||v_carro);
    dbms_output.put_line('Valor da entrada - R$'||v_carro * 0.2);
    dbms_output.put_line('Valor das prestações em 6x - R$'||v_parc  * 1.1 / 6);
    dbms_output.put_line('Valor das prestações em 12x - R$'||v_parc * 1.15/ 12);
    dbms_output.put_line('Valor das prestações em 18x - R$'||v_parc * 1.2 / 18);
end;

declare
    sx char(1) := '&sexo';
begin
    if upper(sx) = 'M' then
        dbms_output.put_line('Masculino');
    elsif upper(sx) = 'F' then
        dbms_output.put_line('Feminino');
    else
        dbms_output.put_line('Outros');
    end if;
end;

-- Criar um bloco PL-SQL para calcular o valor de cada 
-- parcela de uma compra de um carro, de acordo com a solicitação
-- do cliente, seguem as opções possiveis:
-- - Parcelas para aquisição em 6 pagamentos. 
-- - Parcelas para aquisição em 12 pagamentos. 
-- - Parcelas para aquisição em 18 pagamentos. 
-- OBSERVAÇÃO: 
-- 1 – Deverá ser dada uma entrada de 20% do valor da compra. 
-- 2 – Deverá ser aplicada uma taxa juros, no saldo restante, nas 
-- seguintes condições: 
-- 3 – No final informar o valor das parcelas para a forma de 
-- pagamento escolhida.
-- 4 - a ectrada de dados será em tempo de execução, use o & - e comercial
-- A – Pagamento em 6 parcelas: 10%. 
-- B – Pagamento em 12 parcelas: 15%. 
-- C – Pagamento em 18 parcelas: 20%.

--Gabarito prestações com IF

declare
    v_carro number(10,2) := &preco;
    v_prest number(2) := &pretacoes;
    v_parc v_carro%type := v_carro * 0.8;
begin
    dbms_output.put_line('Valor do carro - R$'||v_carro);
    dbms_output.put_line('Valor da entrada - R$'||v_carro * 0.2);
    if v_prest = 6 then
        dbms_output.put_line('Valor das prestações em 6x - R$'||v_parc  * 1.1 / 6);
    elsif v_prest = 12 then
        dbms_output.put_line('Valor das prestações em 12x - R$'||v_parc * 1.15/ 12);
    elsif v_prest = 18 then
        dbms_output.put_line('Valor das prestações em 18x - R$'||v_parc * 1.2 / 18);
    else
        dbms_output.put_line('Opção Inválida');
    end if;
end;