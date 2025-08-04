/*
DML Data Manipulation Language

sintaxe: insert into table_name values (value1, ..., valueN);
colunas não numéricas necessitam de 'dado' -> usar apostrofe

outra sintaxe possivel: insert into table_name (column_name1, ..., column_name2) values (value1, ..., valueN);

*/

create table curso (id_curso number(3) constraint curso_id_pk primary key,
                    nm_curso varchar(40) constraint curso_nome_nn not null
                                         constraint curso_nome_uk unique,
                    preco number(8,2) constraint curso_preco_nn not null);

create table disciplina (id_disc number(4) constraint disci_id_pk primary key,
                         nm_disc varchar(40) constraint disci_nome_nn not null
                                             constraint disci_nome_uk unique,
                         carga_hora number(3) constraint disci_carga_nn not null);

create table curso_discip (fk_curso number(3) constraint cdis_curso_fk references curso,
                           fk_discip number(4)constraint cdis_discip_fk 
                           references disciplina);

insert into curso values(1, 'Análise de Sistemas', 5000);
insert into curso values(2, 'MKT', 5000.34);
commit;
select * from curso;

insert into curso values(3, 'Defesa cibernética', 4500);
insert into curso values(4, 'Pós graduação', 6000);
select * from curso;
commit;

insert into disciplina(id_disc, nm_disc, carga_hora) values (11, 'SQL', 100);
insert into disciplina(id_disc, nm_disc, carga_hora) values (12, 'JAVA', 100);
insert into disciplina(id_disc, nm_disc, carga_hora) values (13, 'FRONT', 100);

commit;
desc curso_discip;

insert into curso_discip values(1, 11);
insert into curso_discip values(1, 12);
insert into curso_discip values(1, 13);
insert into curso_discip values(2, 11);
insert into curso_discip values(2, 12);
insert into curso_discip values(2, 13); -- permite ser inseridas diversas vezes
insert into curso_discip values(3, 11);
insert into curso_discip values(3, 12);
insert into curso_discip values(3, 13);
insert into curso_discip values(4, 11);
insert into curso_discip values(4, 12);
insert into curso_discip values(4, 13);

select * from curso_discip;

commit;

create table teste (codigo number(2) primary key, nome varchar(10), dt_nasc date);

insert into teste values (1, 'fernando', sysdate);
insert into teste values (2, 'josé', '01-feb-2025');
insert into teste values (3, 'joão', null); -- dois modos
insert into teste (codigo, nome) values (4, 'maria'); -- pra fazer a mesma coisa
commit;
select * from teste;

create table teste1 (
    codigo number(2) primary key,
    uf char(2) constraint testecheck check(uf in('SP', 'MG'))
);

insert into teste1 values(1, 'SP');
insert into teste1 values(2, 'RJ'); -- nao vai rodar, RJ fora do check


/*
atualização de dados

sintaxe:
update table_name set column_name = novo_valor [where column_name operator valor];

operadores > aritméticos: + - * / ()
           > relacionais: > >= < <= = <> ou !=
           > lógicos: and or not
           > bd: between in like
*/

select * from teste;
-- atualizar dt_nasc de todos na tabela
update teste set dt_nasc = sysdate;

-- atualizar a data de nascimento para 05-abril-1900 do codigo 1

update teste set dt_nasc = '05-APR-1900' where codigo = 1;
select * from teste;

select * from curso;

update curso set preco = 1500.00 where id_curso = 1 or id_curso = 4;
select * from curso;

update curso set preco = preco*1.1 where id_curso = 2 or id_curso = 3;
select * from curso;

/*
eliminando dados

sintaxe:
delete from table_name [where column_name operator valor];

operadores > relacionais: > >= < <= = <> ou !=
           > lógicos: and or not
           > bd: between in like
*/
select * from curso_discip;

delete from curso_discip where fk_curso = 1;
-- se fizer apenas delete from table_name, ele irá deletar tudo da tabela
select * from curso_discip;

commit;


