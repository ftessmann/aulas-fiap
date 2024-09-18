while True:
    idade = int(input("Digite sua idade (digite 0 para sair): "))

    # Quebra o programa se resposta for 0
    if idade == 0:
        print("Saindo do programa.")
        break

    # Verifica as faixas de idade para doação de sangue
    if 16 <= idade <= 17:
        while True:
            autorizacao = input("Você tem autorização dos pais? (sim/não): ").lower()
            if autorizacao in ['sim', 'não']:
                break
            else:
                print("Opção inválida! Por favor, responda com 'sim' ou 'não'.")
        
        if autorizacao == 'sim':
            print("Você pode doar sangue.")
        else:
            print("Você não pode doar sangue sem autorização dos pais.")
    
    elif 18 <= idade <= 60:
        print("Você pode doar sangue.")
    
    elif 61 <= idade <= 69:
        while True:
            doadorAnterior = input("Você já doou sangue antes? (sim/não): ").lower()
            if doadorAnterior in ['sim', 'não']:
                break
            else:
                print("Opção inválida, por favor, responda com 'sim' ou 'não'.")
        
        if doadorAnterior == 'sim':
            print("Você pode doar sangue.")
        else:
            print("Você não pode doar sangue, pois não é doador anterior.")
    
    else:
        print("Você não pode doar sangue, pois sua idade não está dentro dos limites permitidos.")
