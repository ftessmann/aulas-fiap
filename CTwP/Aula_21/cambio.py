import json
import requests

def buscar_dados():
    request = requests.get("https://api.exchangerate-api.com/v4/latest/USD")
    dados = json.loads(request.content)
    print("exchange rate for USD for following date:", dados["date"])
    print("Enter the currency code")
    currency = input()

    print("1 USD =", dados['rates'][currency], currency)
    
buscar_dados()
    
