# programa que lê um inteiro N e imprimi todos os numeros pares de 0 até N

def verifica_par(n):
    if n % 2 == 0:
        return True
    return False
    
n = int(input())

for i in range(n+1):
    if verifica_par(i):
        print(i)

for i in range(n+1):
    if ((i % 2) == 0):
        print(i)
        
for i in range(0, n+1, 2):
    print(i)