# precedencia de operadores
# avalia expressoes da esquerda para direita
# () expressões dentro do parenteses
# ** potenciação
# +x / -x positivo, negativo e negaçao bit a bit (~x)
# * / % @ // multiplicaçao, multi de matrizes, divisao, divisao pelo piso e modulo
# +/- adiçao e subtraçao
# << >> deslocamentos
# & and bit a bit
# ^ ou exclusivo
# | ou bit a bit
# in, not in, is, is not, < <= == != > >= comparações
# not x negação booleana
# and e booleano
# or ou booleano
# if - else expressão condicional
# expressoes lambda
# = atribuição

# media harmonica

x1 = int(input("Digite o primeiro número:"))
x2 = int(input("Digite o segundo número:"))
x3 = int(input("Digite o terceiro número:"))
x4 = int(input("Digite o quarto número:"))

n = 4

H = n / (1 / x1 + 1 / x2 + 1 / x3 + 1 / x4)

h1 = round(H, 2)

print(h1)

x1_inv = 1 / x1
x2_inv = 1 / x2
x3_inv = 1 / x3
x4_inv = 1 / x4

denominador = x1_inv + x2_inv + x3_inv + x4_inv

otherH = n / denominador

print(otherH)

# Estruturas de controle de fluxo ou condicionais
if H > 10: # se a média for maior que 10, então True e printa, se não False e proximo passo
    print("Média harmonica maior que 10")
elif H == 10: # se a média for igual a 10, então True e printa, se não False e proximo passo
    print("Media igual a 10")
elif H < 0: # se a média for negativa, então True e printa, se não False e proximo passo
    print("Média negativa")
else: # se a média for maior ou igual a 0 e menor que 10, printa automaticamente se a condições anteriores forem False
    print("Média menor que 10")

# sempre será executado pois está fora da identação do if/else
print("Fora do if/elif/else")