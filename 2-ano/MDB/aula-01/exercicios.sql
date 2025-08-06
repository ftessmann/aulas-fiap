set serveroutput on
set verify off

DECLARE
    salario numeric(10, 2) := 1518.00;
BEGIN
    dbms_output.put_line('novo valor de salário: ' || (salario + (salario * 0.25)));
END;

DECLARE
    dolar numeric(10, 2) := 45.00;
    taxa numeric(10, 2) := 5.60;
begin
    dbms_output.put_line('valor do cambio em reais: ' || (dolar * taxa));
end;

declare
    dolar numeric(10, 2) := &valor;
    taxa numeric(10, 2) := 5.60;
begin
    dbms_output.put_line('valor em reais: ' || (dolar * taxa));
end;

