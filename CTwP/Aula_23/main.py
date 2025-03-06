import json

json_path = "CTwP/Aula_23/cambio.json"

with open(json_path) as json_file:
    cambio = json.load(json_file)
    
moedas_mais_valiosas = {moeda: taxa for moeda, taxa in cambio["rates"].items() if taxa < 1 and moeda != "USD"}

moedas_ordenadas = dict(sorted(moedas_mais_valiosas.items(), key=lambda item: item[1]))

print("Moedas que valem mais que o dólar (ordenadas da mais valiosa para menos valiosa):")
for moeda, taxa in moedas_ordenadas.items():
    print(f"{moeda}: {taxa}")