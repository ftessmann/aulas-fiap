tabuleiro = []

i = 0

while i < 3:
    tabuleiro.append([''] * 3)
    i += 1
    
tabuleiro[0][0] = "x"
tabuleiro[1][1] = "o"
tabuleiro[2][2] = "x"

for linha in tabuleiro:
    print(linha)
    
mat = []

j = 0

while j < 4:
    mat.append([0] * 5)
    j += 1
    
contador = 1

for m in range(len(mat)):
    for n in range(len(mat[m])):
        mat[m][n] = contador
        contador += 1
        if contador > 20:
            break
    if contador > 20:
        break
        
for lin in mat:
    print(lin)