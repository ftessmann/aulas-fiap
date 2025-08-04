# programa diz a idade máxima da pessoa

nome = str(input("Qual o seu nome? "))
sobrenome = str(input("Qual o seu sobrenome? "))

print("Bem vindo", nome, sobrenome)

anoNascimento = int(input("Que ano você nasceu? "))

idade = 2024 - anoNascimento

if anoNascimento > 2024:
    print("Nem nasceu ainda")
elif idade > 120:
    print("Confia")
else:
    print("Sua idade máxima é", idade, "anos")
    
if anoNascimento >= 1901 and anoNascimento <= 2000:
    print("Você nasceu no século 20")
elif anoNascimento >= 2001 and anoNascimento <= 2100:
    print("Você nasceu no século 21")
else:
    print("Confia")