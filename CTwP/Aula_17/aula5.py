arquivo = open('othertxt.txt', 'r')

totalLinhas = 0
linhasComBom = 0

for linha in arquivo:
    linha = linha.strip()
    totalLinhas += 1
    if "bom" in linha:
        print(linha)
        linhasComBom += 1
        
print(f"Numero de linhas: {totalLinhas}")

# backslash serve pra indicar que o caractere que vem logo após não deve ser interpretado como parte da sintaxe 
print(f"Numero de linhas que \"bom\" aparece: {linhasComBom}")

arquivo.close()