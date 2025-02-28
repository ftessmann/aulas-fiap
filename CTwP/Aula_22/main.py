import json

valores = [3, 10, -6, 47, 8, 0, 9, 11, 5]

menores_que_10 = [valor for valor in valores if valor < 10]

valores_menores = []
for valor in valores:
    if valor < 10:
        valores_menores.append(valor)

print(valores_menores)

def is_prime(n):
    if n <= 1:
        return False
    if n % 2 == 0:
        return False
    for i in range(3, int(n**0.5) + 1, 2):
        if n % i == 0:
            return False
    return True

numeros_primos = [n for n in range(1000, 1200) if is_prime(n)]

print(numeros_primos)

path = "CTwP\Aula_22\dados.txt"

temps = []

with open(path) as file:
    for line in file:
        temps.append(float(line))

print(temps)

temperaturas_altas = [temp for temp in temps if temp > 90 and temp < 100]
temperaturas_muito_altas = [temp for temp in temps if temp > 100]

print(temperaturas_altas)
print(temperaturas_muito_altas)

print(len(temps))
print(len(temperaturas_altas))
print(len(temperaturas_muito_altas))

path_json = "CTwP/Aula_22/members.json"

with open(path_json) as json_file:
    members = json.load(json_file)
    
members_names = [ member['ParliamentaryName'] for member in members ]

print(members_names)
