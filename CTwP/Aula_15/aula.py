# frutas = ["banana", "maçã", "uva", "pera", "manga", "melancia", "limão"]

# fruta = input("Digite o nome de uma fruta: ")

# if fruta in frutas:
#     print(f"Tem {fruta} na lista")
# else:
#     print(f"Não tem {fruta}")
    
# frutas.sort()

# print(frutas)

# print(f"As opções de frutas são {frutas}")
# frutaEscolhida = input("Escolha uma fruta: ")

# if frutaEscolhida in frutas:
#     print(f"Você escolheu {frutaEscolhida} e ela está na lista")
# else:
#     if frutaEscolhida not in frutas:
#         escolha = input("Deseja inserir a fruta? S/N ")
#         if escolha == "S":
#             frutas.append(frutaEscolhida)
#             print(f"Frutas inserida na lista {frutas}")
#         else:
#             print(f"Suas frutas são: {frutas}")


frutas2 = ["banana", "maçã", "uva", "pera", "manga"]
carrinho = []

print(f"As opções de frutas são {frutas2}")
print("Quantas frutas quer escolher?")
qntFrutas = int(input())
frutasEsc = 0
while frutasEsc < qntFrutas:
    print("Digite uma fruta: ")
    fruta = input()
    if fruta in frutas2:
        carrinho.append(fruta)
        frutasEsc += 1
    else:
        print(f"{fruta} indisponivel")
        print("Tente outra opção")
print()
print("O seu carrinho contem:")
print(carrinho)