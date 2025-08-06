anoNascimento = int(input("Que ano você nasceu? "))

idade = 2024 - anoNascimento

if idade >= 16 and idade < 18:
    print("Você precisa de autorização dos pais")
elif idade >= 18 and idade <= 60:
    print("Doação liberada")
elif idade > 60 and idade <= 69:
    print("Você é doador? Se nunca doou, você não poderá doar")
else:
    print("Você não pode doar sangue")