import datetime

# ano = datetime.datetime.now()

# anoAtual = ano.year

# anoNascimento = int(input("Qual seu ano de nascimento? "))

# idade = anoAtual - anoNascimento

# print("Sua idade é", idade)

# Melhorar programa para perguntar data de nascimento e calcular precisamente a idade


diaNascimento = int(input("Digite o dia do seu nascimento (dd): "))
mesNascimento = int(input("Digite o mês do seu nascimento (mm): "))
anoNascimento = int(input("Digite o ano do seu nascimento (aaaa): "))

data = datetime.datetime.now()
diaAtual = data.day
mesAtual = data.month
anoAtual = data.year

idade = anoAtual - anoNascimento


if mesAtual < mesNascimento or (mesAtual == mesNascimento and diaAtual < diaNascimento):
    idade -= 1
    fezAniversario = False
else:
    fezAniversario = True


print("Você tem", idade, "anos")
if fezAniversario and (mesAtual == mesNascimento and diaAtual == diaNascimento):
    print("Hoje é seu anivesário, parabéns!")
elif fezAniversario:
    print("Você já fez anivesário esse ano")     
else:
    print("Você ainda não fez aniversário esse ano.")
    
