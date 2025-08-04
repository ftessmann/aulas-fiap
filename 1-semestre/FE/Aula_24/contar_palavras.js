const formPalavras = document.getElementById('formPalavras');
const fraseInput = document.getElementById('frase');
const quantidadePalavras = document.getElementById('quantidadePalavras');
const quantidadeCaractere = document.getElementById('quantidadeCaractere')

function contarCaracteres(texto) {
    if (!texto) return 0;

    if (texto.length > 200) {
        texto.substring(0, 199);
        
        return quantidadeCaractere.innerText = "Quantidade de caracteres inválida, mantenha no máximo 200"
    }

    return texto.length
}

function contarPalavras(texto) {
    if (!texto.trim()) return 0;
    return texto.trim().split(/\s+/).length;
};

formPalavras.addEventListener('input', (event) => {
    event.preventDefault();
    const frase = fraseInput.value;
    const numeroPalavras = contarPalavras(frase);
    const numeroCaractere = contarCaracteres(frase);
    quantidadePalavras.innerText = `Número de palavras: ${numeroPalavras}`;
    quantidadeCaractere.innerText = `Número de caracteres: ${numeroCaractere}`
});
