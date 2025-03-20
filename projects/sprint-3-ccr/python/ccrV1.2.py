import csv
from variaveis import BEM_VINDO, MENU_PRINCIPAL, INFO_ESMERALDA, INFO_DIAMANTE, OPCAO_INVALIDA, SUBMENU, TARIFA, CCR

print(BEM_VINDO)

nome = input("Para melhor experiência, me informe seu nome, por favor: ")

# define o caminho do arquivo CSV para armazenar os incidentes
arquivo_csv = "incidentes.csv"

# função para criar um novo incidente e armazená-lo em um arquivo CSV
def criarIncidente():
    email = input("Nos informe seu email, por favor: \n")
    descricao = input("Informe o que aconteceu: \n")
    categoria = input("Informe a categoria do incidente (ex: Segurança, Manutenção, etc.): \n")
    
    # solicita a gravidade do incidente
    while True:
        gravidade = input("Informe a gravidade do incidente (Baixa, Média, Alta): \n").capitalize()
        if gravidade in ["Baixa", "Média", "Alta"]:
            break
        else:
            print("Por favor, escolha uma gravidade válida: Baixa, Média ou Alta.")
    
    # armazena o incidente em um arquivo CSV
    with open(arquivo_csv, mode="a", newline='') as file:
        writer = csv.writer(file)
        writer.writerow([email, descricao, categoria, gravidade])
    
    print("Incidente registrado com sucesso!")

# função para visualizar todos os incidentes armazenados no arquivo CSV
def visualizarIncidente():
    try:
        with open(arquivo_csv, mode="r") as file:
            reader = csv.reader(file)
            incidentes = list(reader)
            
            if not incidentes:
                print("Nenhum incidente registrado até o momento.")
                return
            
            print("\nIncidentes registrados:")
            for i, incidente in enumerate(incidentes, start=1):
                print(f"[ {i} ] Incidente criado por {nome}, Email: {incidente[0]} - Categoria: {incidente[2]}, Gravidade: {incidente[3]}")
            
            escolha = int(input("\nSelecione o número do incidente que deseja visualizar: "))
            
            if 1 <= escolha <= len(incidentes):
                incidenteEscolhido = incidentes[escolha - 1]
                print(f"\nDetalhes do Incidente {escolha}:")
                print(f"Email: {incidenteEscolhido[0]}")
                print(f"Descrição: {incidenteEscolhido[1]}")
                print(f"Categoria: {incidenteEscolhido[2]}")
                print(f"Gravidade: {incidenteEscolhido[3]}\n")
            else:
                print("Escolha inválida.")
    
    except FileNotFoundError:
        print("Nenhum incidente registrado até o momento.")

# função para deletar um incidente do arquivo CSV
def deletarIncidente():
    try:
        with open(arquivo_csv, mode="r") as file:
            incidentes = list(csv.reader(file))
        
        if not incidentes:
            print("Nenhum incidente registrado para deletar.")
            return
        
        print("\nIncidentes registrados:")
        for i, incidente in enumerate(incidentes, start=1):
            print(f"[ {i} ] Incidente criado por {nome}, Email: {incidente[0]} - Categoria: {incidente[2]}, Gravidade: {incidente[3]}")
        
        escolha = int(input("\nDigite o número do incidente que deseja deletar: "))
        
        if 1 <= escolha <= len(incidentes):
            del incidentes[escolha - 1]
            with open(arquivo_csv, mode="w", newline='') as file:
                writer = csv.writer(file)
                writer.writerows(incidentes)
            print("Incidente deletado com sucesso!")
        else:
            print("Escolha inválida.")
    
    except FileNotFoundError:
        print("Nenhum incidente registrado para deletar.")

# menu principal
def main():
    opcaoMenu = int  # inicializa opcaoMenu para a estrutura de repetição
    while opcaoMenu != 0:
        print(MENU_PRINCIPAL)
        opcaoMenu = int(input("Selecione a opção desejada: "))
        
        if opcaoMenu == 1:
            opcaoSubMenu = int  # inicializa opcaoSubMenu para a estrutura de repetição
            while opcaoSubMenu != 0:
                print(SUBMENU)
                opcaoSubMenu = int(input("Selecione a opção desejada: "))
                
                if opcaoSubMenu == 1:
                    print(INFO_ESMERALDA)
                elif opcaoSubMenu == 2:
                    print(INFO_DIAMANTE)
                elif opcaoSubMenu == 0:
                    print("Retornando para o menu principal.")
                else:
                    print(OPCAO_INVALIDA)
        
        elif opcaoMenu == 2:
            print(TARIFA)
            
        elif opcaoMenu == 3:
            print(CCR)
            
        elif opcaoMenu == 4:
            criarIncidente()

        elif opcaoMenu == 5:
            visualizarIncidente()

        elif opcaoMenu == 6:
            deletarIncidente()

        elif opcaoMenu == 0:
            print("Obrigado por escolher a CCR-ViaMobilidade")
        
        else:
            print(OPCAO_INVALIDA)

# executando a função principal do programa
main()
# encerrando o programa
print("Programa encerrado.")
