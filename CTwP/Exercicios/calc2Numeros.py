# programa que lê dois valores e dá opções de somar, subtrair, multiplicar, dividir ou verificar qual é maior

numero1 = int(input("Digite o primeiro numero: "))
numero2 = int(input("Digite o segundo numero: "))

opcaoMenu = 0

while opcaoMenu != 7:
    print('''
        [ 1 ] Somar
        [ 2 ] Multiplicar
        [ 3 ] Dividir
        [ 4 ] Subtrair
        [ 5 ] Verificar qual é o maior
        [ 6 ] Digitar novos números
        [ 7 ] Sair
    ''')
    opcaoMenu = int(input("Escolha a sua opção: "))
    if opcaoMenu == 1:
        resultado = numero1 + numero2
        print(f"Sua soma é: ", resultado)
    elif opcaoMenu == 2:
        resultado = numero1 * numero2
        print(f"Sua multiplicação é: ", resultado)
    elif opcaoMenu == 3:
        resultado = numero1 / numero2
        print(f"Sua divisão é: ", resultado)
    elif opcaoMenu == 4:
        resultado = numero1 - numero2
        print(f"Sua subtração é: ", resultado)
    elif opcaoMenu == 5:
        if numero1 > numero2:
            maior = numero1
        else:
            maior = numero2
        print(f"O número de maior valor é: ", maior)
    elif opcaoMenu == 6:
        print("Informe os numeros novamente: ")
        numero1 = int(input("Primeiro número: "))
        numero2 = int(input("Segundo número: "))
    elif opcaoMenu == 7:
        print("Finalizando")
    else:
        print("Opção inválida, tente novamente")
        
print("Fim do programa.")


