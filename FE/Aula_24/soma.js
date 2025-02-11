function somar (a, b) {
    return a + b
}

document.getElementById("formSoma").addEventListener('submit', function (event) {
    event.preventDefault();

    let num1 = parseFloat(document.getElementById("valorA").value);
    let num2 = parseFloat(document.getElementById("valorB").value);

    let resultado = somar(num1, num2);

    document.getElementById("resultado").innerText = `A soma é ${resultado}`;
})
