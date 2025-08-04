-- Aula 01 - Comandos de ambiente

set serveroutput on  -- habilita a saída de dados
set verify off -- desabilita feedback da entrada de dados manual

begin
    dbms_output.put_line('Fiap');
end;

begin
    dbms_output.put_line('3x3');
end;

begin
    dbms_output.put_line('fiap');
    dbms_output.put_line('paulista');
end;

begin
    dbms_output.put_line('processamento: ' || 33); -- || pipe funciona como concat
end;

declare
    nome varchar(255) := 'fernando';
begin
    dbms_output.put_line('Aluno: ' || nome);
end;

declare
    v1 number(3) := 10;
    v2 number(3) := 10;
    v3 v1%type := v1 + v2;
begin
    dbms_output.put_line('resultado: ' || v3);
end;

-- OU

declare
    v1 number(3) := 10;
    v2 number(3) := 10;
    v3 v1%type;
begin
    v3 := v1 + v2;
    dbms_output.put_line('resultado: ' || v3);
end;

declare
    nome varchar(255) := '$valor'; -- string deve ser inserida entre ''
    v1 number(3) := &valor1; -- & E comercial abre caixa para input de dados
    v2 number(3) := &valor2;
    v3 v1%type;
begin
    v3 := v1 + v2;
    dbms_output.put_line('resultado: ' || v3);
end;

