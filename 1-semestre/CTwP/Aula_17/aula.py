# modo w, read only
arquivo = open('text.txt', 'w')

print(arquivo.read())

for linha in arquivo:
    print(linha)

# boas práticas, fechar arquivo após o uso
arquivo.close()