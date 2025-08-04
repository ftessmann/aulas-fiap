const botao = document.getElementById("botao-andar")
const campo = document.getElementById("campo-andar")
const cachorro = document.getElementById("cachorro")
const container = document.getElementById("container")
const botaoTexto = document.getElementById("botao-span")

function mudarPosicao() {
    var pos = 0
    var direcao = "direita"
    tempo = setInterval(animacao, 1)

    function animacao() {
        if (direcao == "direita") {
            botao.disabled = true
            botaoTexto.innerHTML = "Andando"
            cachorro.src = "images/cachorro_andando_direita.gif"
            container.style.left = pos + "px"
            pos++;
            if (pos == 1027) {
                direcao = "esquerda"
                pos = 1027
            }
        }
        if (direcao == "esquerda") {
            cachorro.src = "images/cachorro_andando_esquerda.gif"
            container.style.left = pos + "px";
            pos--;
            if (pos == 0) {
                cachorro.src = "images/cachorro_parado.gif";
                direcao = "reset";
                botao.disabled = false
                botaoTexto.innerHTML = "Andar"
                clearInterval(tempo)
            }
        }
    }
}

botao.addEventListener("click", mudarPosicao)