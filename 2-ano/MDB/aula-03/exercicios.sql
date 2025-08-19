set serveroutput on
set verify off

DECLARE
    v_n number(2) := 15;
BEGIN
    IF MOD(v_n,2) = 0 THEN
        DBMS_OUTPUT.PUT_LINE('O número ' || v_n || ' é par.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('O número ' || v_n || ' é impar.');
    END IF;
END;

CREATE TABLE ALUNO (
    RM CHAR(2),
    NOME VARCHAR2(50),
        CONSTRAINT ALUNO_PK PRIMARY KEY(RM)
);

INSERT INTO ALUNO(RM, NOME) VALUES(11, 'Antonio Alves');
INSERT INTO ALUNO(RM, NOME) VALUES(22, 'Bianca Bernardes');
INSERT INTO ALUNO(RM, NOME) VALUES(33, 'Cláudio Cardoso');

select * from aluno;

-- select
DECLARE
    V_RM char(9) := '33';
    V_NOME varchar2(50);
BEGIN
    SELECT NOME INTO V_NOME FROM ALUNO WHERE RM = V_RM;
    DBMS_OUTPUT.PUT_LINE('O nome do aluno é: ' || V_NOME);
END;

-- insert
DECLARE
    V_RM char(2) := '44';
    V_NOME varchar(50) := 'Daniela Dornoeles';
BEGIN
    INSERT INTO ALUNO(RM, NOME) VALUES(V_RM, V_NOME);
END;

select * from aluno;

-- update
DECLARE
    V_RM CHAR(2) := '11';
    V_NOME VARCHAR(50) := 'Antonio Rodrigues';
BEGIN
    UPDATE ALUNO SET NOME = V_NOME WHERE RM = V_RM;
END;

select * from aluno;

-- delete
DECLARE
    V_RM CHAR(2) := '44';
BEGIN
    DELETE FROM ALUNO WHERE RM = V_RM;
END;

select * from aluno;

-- criar bloco pl que:
-- 1 - realize a inserção de 5 linhas de dados na tabela produto
-- 2 realize uma atualização qualquer de preço na tabela produto
-- 3 - realize a entrada do código do produto, a quantidade a ser 
-- comprada, e a sáida seja os dados de entrada mais o valor total da compra 
-- 4 - realize após a compra a baixa no estoque do produto

create table produto (
    id_prod number(3),
    ds_prod varchar2(30) not null,
    pr_prod number(10, 2),
    qtd_prod number(10, 2),
    constraint id_pk primary key(id_prod),
    constraint uk_ds unique(ds_prod) 
);

BEGIN
    INSERT INTO produto (id_prod, ds_prod, pr_prod, qtd_prod) VALUES (1, 'Notebook Dell', 2500.00, 10);
    INSERT INTO produto (id_prod, ds_prod, pr_prod, qtd_prod) VALUES (2, 'Mouse Logitech', 45.90, 25);
    INSERT INTO produto (id_prod, ds_prod, pr_prod, qtd_prod) VALUES (3, 'Teclado Mecânico', 150.00, 15);
    INSERT INTO produto (id_prod, ds_prod, pr_prod, qtd_prod) VALUES (4, 'Monitor 24 pol', 800.00, 8);
    INSERT INTO produto (id_prod, ds_prod, pr_prod, qtd_prod) VALUES (5, 'HD Externo 1TB', 320.00, 12);
    
    COMMIT;
END;

select * from produto;

DECLARE
    V_ID NUMBER(3) := 1;
    V_PR number(10, 2) := 3000;
BEGIN
    UPDATE PRODUTO SET pr_prod = V_PR where id_prod = V_ID;
END;
commit;
select * from produto;

DECLARE
    v_codigo_produto NUMBER := &codigo_produto;
    v_quantidade_compra NUMBER(10,2) := &quantidade_compra;
    
    v_descricao VARCHAR2(30);
    v_preco_unitario NUMBER(10,2);
    v_estoque_atual NUMBER(10,2);
    v_valor_total NUMBER(10,2);
    
BEGIN
    SELECT ds_prod, pr_prod, qtd_prod
    INTO v_descricao, v_preco_unitario, v_estoque_atual
    FROM produto
    WHERE id_prod = v_codigo_produto;

    IF v_estoque_atual >= v_quantidade_compra THEN
        v_valor_total := v_preco_unitario * v_quantidade_compra;
        
        DBMS_OUTPUT.PUT_LINE('=== DADOS DA COMPRA ===');
        DBMS_OUTPUT.PUT_LINE('Código do Produto: ' || v_codigo_produto);
        DBMS_OUTPUT.PUT_LINE('Descrição: ' || v_descricao);
        DBMS_OUTPUT.PUT_LINE('Quantidade solicitada: ' || v_quantidade_compra);
        DBMS_OUTPUT.PUT_LINE('Preço unitário: R$ ' || TO_CHAR(v_preco_unitario, '9999.99'));
        DBMS_OUTPUT.PUT_LINE('Estoque atual: ' || v_estoque_atual);
        DBMS_OUTPUT.PUT_LINE('Valor total da compra: R$ ' || TO_CHAR(v_valor_total, '99999.99'));
    ELSE
        DBMS_OUTPUT.PUT_LINE('Estoque insuficiente!');
        DBMS_OUTPUT.PUT_LINE('Estoque disponível: ' || v_estoque_atual);
        DBMS_OUTPUT.PUT_LINE('Quantidade solicitada: ' || v_quantidade_compra);
    END IF;
END;

