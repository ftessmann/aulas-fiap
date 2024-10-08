for i in range(2, 30, 3):
    print(i)

for j in range(3, 10):
    print(j)
    
for h in range(10):
    print(h)

for a in range(10, 0, -1):
    print(a)
    
for n in range(1, 11):
    print(f"Vou mostrar a tabuada do {n}")
    for multiplo in range(1, 11):
        print(f"{n} x {multiplo} = {n*multiplo}")
    print("-" * 30)
    
text = "hello world"
text2 = "lorem ipsum dolor hello world"

for c in text:
    print(c)
    
blank = 0
for c in text2:
    if c == "o":
        blank += 1
        
print(f"O texto tem {blank} letras O")

# Listas

compras = ["arroz", "feijão", "carne", "tomate", "cebola"]

for item in enumerate(compras):
    print(item)
    
compras.append("coquinha")

for item in compras:
    print(item)
    
linguagens = []

linguagens.append("C")
linguagens.append("Java")
linguagens.append("Python")
linguagens.append("Rust")
linguagens.append("Golang")

for l in linguagens:
    print(f"A seguinte linguagem foi add: {l}")
    
print(f"Foram adicionadas {len(linguagens)} linguagens")

listaCompras = []
print("Criando lista de compras")
n = int(input("Quantos itens deseja comprar? "))

for i in range(n):
    listaCompras.append(input("Qual item deseja comprar? "))

print("A sua lista ficou assim:")
for item in listaCompras:
    print(item)