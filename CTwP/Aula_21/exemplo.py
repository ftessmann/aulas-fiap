# dicionário em python
import json

pessoa = {}

pessoa['nome'] = "Fernando"
pessoa['cidade'] = "São Paulo"
pessoa['anoNascimento'] = 1994

print(pessoa)

with open("texto.txt") as arquivo:
    linhas = arquivo.readlines()
    
for linha in linhas:
    print(linha)
    
with open("members.json") as arquivo:
    membros = json.load(arquivo)

nomes_membros = []

for membro in membros:
    nomes_membros.append(membro["ParliamentaryName"])
    
print(nomes_membros)

nomes_ordem = sorted(nomes_membros)

for nome in nomes_ordem:
    print(nome)