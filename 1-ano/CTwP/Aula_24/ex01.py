import json

FILE_PATH = "CTwP\Aula_24\outro_arquivo.json"
def carregar_pacientes():
    try:
        with open(FILE_PATH) as file:
            pacientes = json.load(file)
    except:
        pacientes = []
    finally:
        return pacientes    

def cadastrar_paciente():
    pacientes = carregar_pacientes()
    paciente = {}
    
    print("Informe o nome do paciente:")
    nome = input()
    paciente['nome'] = nome
    
    print("Informe a idade do paciente:")
    idade = int(input())
    paciente['idade'] = idade
    
    id = len(pacientes) + 1
    paciente['id'] = id
    
    return paciente

def salvar_paciente(paciente, pacientes):
    pacientes.append(paciente)
    return pacientes
    
def escrever_paciente_no_arquivo(pacientes: list):
    with open(FILE_PATH, 'w') as file:
        json.dump(pacientes, file, indent=4)
    
def mostrar_nomes_pacientes(pacientes: list):
    for paciente in pacientes:
        print(paciente['nome'])
        
def mostrar_dados_pacientes(pacientes: list):
    for paciente in pacientes:
        print(f"Nome: {paciente['nome']}")
        print(f"Idade: {paciente['idade']}")
        print(f"id: {paciente['id']}")
    
def main():
    pacientes = carregar_pacientes()
    print("arquivo carregado")
    executando = True
    while executando:
        print('''
Menu - Escolha uma opção:
[1] - Mostrar dados dos pacientes
[2] - Cadastrar paciente
[0] - Sair do programa
        ''')
        opcao = int(input("Escolha a opção: "))
        
        if opcao == 0:
            executando = False
        elif opcao == 1:
            print("Dados dos pacientes:")
            mostrar_dados_pacientes(pacientes)
        elif opcao == 2:
            novo_paciente = cadastrar_paciente()
            pacientes = salvar_paciente(novo_paciente, pacientes)
        else:
            print("Opção inválida")
            
    print("Encerrando o programa.")
    escrever_paciente_no_arquivo(pacientes)
    print("Paciente salvo com sucesso")
    
if __name__ == "__main__":
    main()