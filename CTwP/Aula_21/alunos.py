import json

with open('alunos.json') as arquivo:
    alunos = json.load(arquivo)

print(alunos)

for aluno in alunos:
    print("Nome:", aluno["nome"])
    print("RM:", aluno["rm"])
    print("Curso:", aluno["curso"])
    print("Turma:", aluno["turma"])
    print("\n")
    
    
