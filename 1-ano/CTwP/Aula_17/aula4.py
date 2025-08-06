arquivo = open('othertxt.txt', 'r')

for linha in arquivo:
    linha = linha.strip()
    if "bom" in linha:
        print(linha)
        
arquivo.close()