#operadores de comparação 
#igualdade
#sempre utilizar ==
# somente = é utilizado para atribuição
10 == 5 # False
10 == 10 # True

#diferença
10 != 5 # True
10 != 10 # False

# maior
10 > 5 # True
10 > 10 # False

# menor
10 < 5 # False
10 < 10 # False
10 < 11 # True

#maior ou igual
10 >= 5 # True
10 >= 10 # True
10 >= 11 # False

#menor ou igual
10 <= 5 # False
10 <= 10 # True
10 <= 11 # True

#Operadores Lógicos
# and: retorna True se as duas condições forem verdadeiras
# or: retorna True se pelo menos uma das condições for verdadeira
# not: inverte o valor da condição

# Operador and:
True and True # True
True and False # False
False and True # False
False and False # False
# é similar a multiplicação, pois se um dos elementos for 0, o resultado é 0

#Operador or:
True or True # True
True or False # True
False or True # True
False or False # False
# é similar a soma, pois se um dos elementos for 1, o resultado é 1

#Operador not
not True # False
not False # True
# é similar a multiplicação por -1, invertendo o sinal

peso = 80
altura = 1.75
imc = peso / (altura ** 2) # 26.12
imc < 18.5 # False
imc >= 18.5 and imc < 25 # False
imc >= 25 and imc < 30 # True
imc >= 30 # False
print(f"Seu Imc é: {imc:.2f}")

# Operações com strings
# Igualdade
"python" == "python" # True
"python" == "Python" # False

# Diferença
"python" != "python" # False
"python" != "Python" # True

# Maior
"python" > "python"  # False
"python" > "Python"  # True

# Menor
"python" < "python"  # False
"python" < "Python"  # False

# Maior ou igual
"python" >= "python"  # True
"python" >= "Python"  # True

# Menor ou igual
"python" <= "python"  # True
"python" <= "Python"  # False

# Operadores lógicos
# and
"python" == "python" and "Python" == "Python"  # True
"python" == "python" and "Python" == "python"  # False

# or
"python" == "python" or "Python" == "Python"  # True
"python" == "python" or "Python" == "python"  # True
"python" == "Python" or "Python" == "python"  # False

# not
not "python" == "python"  # False
not "python" == "Python"  # True

# Operações aritméticas com strings
# adição -> concatenação (nome mais correto tecnicamente)
"python" + " é legal"  # "python é legal"  # tinha um espaço no início de ' é legal'
"python" + "é " + "legal"  # "pythoné legal" # não tem espaço entre 'python' e 'é'

# subtração e divisão não fazem sentido para strings
# multiplicação
"python" * 3  # "pythonpythonpython", ou seja, repete a string 3 vezes,
# similar a "python" + "python" + "python"
"python " * 3  # "python python python ", ou seja, repete a string 3 vezes,
# similar a "python " + "python " + "python "
