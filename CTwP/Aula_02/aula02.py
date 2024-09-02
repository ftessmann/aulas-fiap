# x = input()

# X = x.upper() #tudo em maisculo

# print("entrada:", x)

# print("entrada em maisculo:", X)

text = "meu dado" #string
inteiro = 10 #int
real = 3.14 #float
booleanoVerdadeiro = True #boolean
booleanoFalso = False #boolean

type(text) #retorna tipo de dado

#tipos de dados reservados
# inteiro -> int
# string -> str
# real -> float
# booleano -> bool

#casting - transformação de dados
#str -> float
float("3.14")

#str -> int
int("10")

#float -> int
int(3.14) # truncate

#int -> float
float(10) # 10.0

#int -> str
str(10) # "10"

#float -> str
str(3.14) # "3.14"

#str -> bool
bool("true") # true
bool("false") # true
bool("") # false

#int -> bool
bool(1) # true
bool(0) # false
bool(-1) # true

#float -> str
bool(0.0) # false
bool(0.1) # true
bool(3.14) # true

#operações aritméticas

#soma
10 + 5 # 15

#subtração
10 - 5 # 5

#multiplicação
10 * 5 # 50

#divisão
10 / 5 # 2.0 -> resultado de divisão normal, sempre em float

#divisão inteira
10 // 5 # 2 -> resultado de divisão inteira, sempre int, ignora a casa decimal

#modulo ou resto
10 % 5 # 0

#avaliaçao se é par
10 % 2 # 0 -> é par
11 % 2 # 1 -> é impar

#potenciação
10 ** 5 # 100000

#raiz quadrada
100**0.5 #10.0

#operadores de comparação 
#igualdade
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