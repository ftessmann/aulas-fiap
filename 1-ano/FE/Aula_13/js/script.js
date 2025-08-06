// alert("eai");

// js não espera tipagem de dados

var num1; 

let num2;

const pi = 3.14; // const são valores imutáveis

function boasVindas() {
    alert("Bem vindo");
    document.write("eai meu truta");
}

function somar() {
    // let valor01 = parseInt(document.getElementById("txtValor1").value);
    // let valor02 = parseInt(document.getElementById("txtValor2").value);

    valor1 = document.getElementById("txtValor1");
    valor2 = document.getElementById("txtValor2");
    result = document.getElementById("result");

    if (valor1.value == "" || valor2.value == "") {
        alert("Campo obrigatorio");
        if (valor1.value == "") {
            valor1.focus();
        }
        if (valor2.value == "") {
            valor2.focus();
        }
        return false;
    }

    let soma = parseInt(valor1.value) + parseInt(valor2.value);
    
    result.innerText = soma;
    
}