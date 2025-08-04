seculo = int(input("Qual o século? "))

anoFinal = seculo * 100
anoInicial = anoFinal - 99

print("Esse século vai do ano", anoInicial, "até o ano", anoFinal)

if anoInicial < 2024 and anoFinal > 2024:
    print("Esse é o século atual")
else:
    print("Esse não é o seculo atual")