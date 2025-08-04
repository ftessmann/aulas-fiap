import json
dados = [
    {
        "valor": "As",
        "naipe": "Paus"
    },
    {
        "valor": "7",
        "naipe": "Copas"
    },
    {
        "valor": "4",
        "naipe": "Ouros"
    },
]

arq = open("cartas.json", "w")
json.dump(dados, arq, indent=4)
arq.close()