import random

print("<-- Bem vindo ao Cassino do Tigrinho! -->")

saldo = int(input("Digite o saldo inicial do jogador: "))

while saldo > 0:
    print(f"\nSaldo atual: {saldo:.2f}")
    aposta = int(input("Digite o valor da aposta (máximo 10 créditos): "))

    if aposta > saldo:
        print("Você não tem créditos suficientes para essa aposta!")
        continue

    if aposta > 10:
        print("A aposta máxima permitida é 10 créditos!")
        continue

    # gerar os três valores aleatórios de 1 a 7
    resultado = [random.randint(0, 5) for i in range(3)]
    print(f"Resultado: {resultado}")

    # verificar se os três valores são iguais
    if resultado[0] == resultado[1] == resultado[2]:
        premio = aposta * 10
        print(f"Parabéns! Você ganhou {premio} créditos!")
        saldo += premio
    else:
        print("Você perdeu.")
        saldo -= aposta

    if saldo <= 0:
        print("A casa sempre ganha.")