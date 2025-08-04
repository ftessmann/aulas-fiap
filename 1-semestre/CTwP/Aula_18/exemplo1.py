nome_arquivo = 'livro.txt'
arquivo = open(nome_arquivo)

print('Abrimos o arquivo', nome_arquivo)
print('Digite o termo a ser buscado no arquivo:')
termo = input()

# acessar o arquivo como uma lista de linhas
linhas = arquivo.readlines()

print('Linhas do arquivo', nome_arquivo, 'contendo', termo)
for linha in linhas:
    if termo in linha: # se o termo buscado ocorre nessa linha
        print(linha.strip())