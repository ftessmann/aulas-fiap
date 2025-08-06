# programa que calcula a sequencia de fibonacci até a sequencia indicada

print("Sequencia de Fibonacci")

print("--" * 15)

sequencia = int(input("Quantas sequencias você que mostrar? "))

print("--" * 15)

primeiroTermo = 0
segundoTermo = 1

print(primeiroTermo, segundoTermo, end=" ")

contador = 3

while contador <= sequencia:
    terceiroTermo = primeiroTermo + segundoTermo
    print(terceiroTermo, end=" ")
    primeiroTermo = segundoTermo
    segundoTermo = terceiroTermo
    contador += 1

print("\n" + ("--" * 15) + "\nPrograma finalizado")
