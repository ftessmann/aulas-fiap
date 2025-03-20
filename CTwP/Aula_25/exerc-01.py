import json
import os

FILE_PATH = "CTwP\Aula_25\chaves_pix.json"
def carregar_dados():
    if os.path.exists(FILE_PATH):
        try:
            with open(FILE_PATH, 'r', encoding='utf-8') as arquivo:
                return json.load(arquivo)
        except json.JSONDecodeError:
            print("Erro ao carregar o arquivo. Criando um novo banco de dados.")
            return {}
    return {}

def salvar_dados(dados):
    with open(FILE_PATH, 'w', encoding='utf-8') as arquivo:
        json.dump(dados, arquivo, ensure_ascii=False, indent=4)

def cadastrar_chave_pix(chaves_pix):
    print("\n=== Cadastro de Chave PIX ===")
    
    chave_pix = input("Digite a chave PIX: ").strip()
    
    if chave_pix in chaves_pix:
        print("Erro: Esta chave PIX já está cadastrada!")
        return
    
    nome = input("Nome do titular: ").strip()
    banco = input("Nome do banco: ").strip()
    numero_conta = input("Número da conta: ").strip()
    
    dados_conta = {
        "nome": nome,
        "banco": banco,
        "numero_conta": numero_conta
    }
    
    chaves_pix[chave_pix] = dados_conta
    
    salvar_dados(chaves_pix)
    
    print("Chave PIX cadastrada com sucesso!")

def consultar_chave_pix(chaves_pix):
    """Consulta os dados de uma conta a partir da chave PIX."""
    print("\n=== Consulta de Chave PIX ===")
    
    chave_pix = input("Digite a chave PIX que deseja consultar: ").strip()
    
    if chave_pix not in chaves_pix:
        print("Erro: Chave PIX não encontrada!")
        return
    
    dados_conta = chaves_pix[chave_pix]
    
    print("\nDados da conta:")
    print(f"Nome do titular: {dados_conta['nome']}")
    print(f"Banco: {dados_conta['banco']}")
    print(f"Número da conta: {dados_conta['numero_conta']}")

def menu_principal():
    """Exibe o menu principal e gerencia as opções."""
    chaves_pix = carregar_dados()
    
    while True:
        print("\n=== Sistema de Gerenciamento de Chaves PIX ===")
        print("1. Cadastrar Chave PIX")
        print("2. Consultar Chave PIX")
        print("3. Sair")
        
        opcao = input("\nEscolha uma opção: ").strip()
        
        if opcao == '1':
            cadastrar_chave_pix(chaves_pix)
        elif opcao == '2':
            consultar_chave_pix(chaves_pix)
        elif opcao == '3':
            print("Saindo do sistema.")
            break
        else:
            print("Opção inválida. Por favor, tente novamente.")

if __name__ == "__main__":
    menu_principal()
