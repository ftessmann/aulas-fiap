idade = int(input("Qual a sua idade? "))

if idade == 16 or idade == 17:
    print("Você tem autorização dos pais? (S/N)")
    resposta = input()
    if resposta == "s" or resposta == "S":
        print("Pode doar sangue.")
    else:
        print("Só poderá doar com autorização dos pais")
elif idade >= 18 and idade <= 60:
    print("Pode doar sangue.")
elif idade > 60 and idade <= 69:
    print("Você já doador? (S/N)")
    resposta = input()
    if resposta == "s" or resposta == "S":
        print("Pode doar sangue.")
    else:
        print("Você precisava ser doador")
else:
    print("Você não pode doar sangue")
    

# Refatorado

podeDoar = False

if idade >= 18 and idade <= 60:
    podeDoar = True
elif idade == 16 or idade == 17:
    resposta = input("Você tem autorização dos pais? S/N ")
    if resposta == "s" or resposta == "S":
        podeDoar = True
elif idade > 60 and idade <= 69:
    resposta = input("Você já doou anteriormente? S/N")
    if resposta == "s" or resposta == "S":
        podeDoar = True
        
if podeDoar:
    print("Você pode doar sangue.")
else:
    print("Você não pode doar")