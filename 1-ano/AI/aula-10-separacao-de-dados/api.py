import pickle
import numpy as np
from flask import Flask, request, jsonify

app = Flask(__name__)

with open("AI/aula-10-separacao-de-dados/finalized_model.pkl", 'rb') as f:
    modelo = pickle.load(f)

@app.route("/prever", methods=["GET"])
def prever():
    param1 = float(request.args.get("comp_abd"))
    param2 = float(request.args.get("comp_ant"))
    
    entrada = np.array([[param1, param2]])
    resultado = modelo.predict(entrada)
    
    return jsonify({"previsão": resultado.tolist()})
    

if __name__ == "__main__":
    app.run()