import json
import requests

def buscar_dados():
    print("Select the currency you want to show the exchange rate for(BRL, GBP, EUR, JPY, etc):")
    currency = input()
    
    url = f"https://api.exchangerate-api.com/v4/latest/{currency}"
    res = requests.get(url)
    data = json.loads(res.content)
    
    print(f"Exchange rate for {exchange} for following date:", data["date"])
    print("Enter the currency code(BRL, GBP, EUR, JPY, etc):")
    
    exchange = input()

    print(f"1 {currency} =", data['rates'][exchange], exchange)
    
buscar_dados()