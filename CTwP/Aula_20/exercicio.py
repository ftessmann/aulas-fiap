# programa que lê um inteiro N e imprimi todos os numeros pares de 0 até N

n = int(input())

for i in range(n+1):
    if ((i % 2) == 0):
        print(i)