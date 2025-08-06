# n = 5
# while n > 1:
#     print(n)
#     n -= 1

# i = 4
# while i < 11:
#     print(i)
#     i = i + 2
# print("fim")
# print(i)

# for i in range(4, 11, 2):
#     print(i)

sabores = ["baunilha", "chocolate", "morango"]
coberturas = ["granulado", "calda", "farofa"]

print("Temos os seguintes sabores: ")
for sabor in sabores:
    for cobertura in coberturas:
        print(f"{sabor} com {cobertura}")
    print("------")
novoSabor = input("Digite um novo sabor")

sabores.append(novoSabor)

print(sabores)