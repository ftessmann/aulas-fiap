var opcoes = ["Papel", "Pedra", "Tesoura"];
var pontuacaoJogador = 0;
var pontuacaoComputador = 0;

function jogar(opcaoJogador) {
    var respComputador = Math.ceil(Math.random() * 3);
    var resultado = document.getElementById("resultado");

    resultado.innerText = "Você escolheu " + opcoes[opcaoJogador - 1] + ". O computador escolheu " + opcoes[respComputador - 1] + ".";

    if (opcaoJogador === respComputador) {
        resultado.innerText += "A rodada empatou.";
    } else if ((opcaoJogador === 1 && respComputador === 2) || 
               (opcaoJogador === 2 && respComputador === 3) || 
               (opcaoJogador === 3 && respComputador === 1)) {
        pontuacaoJogador++;
        resultado.innerText += " Você ganhou!";
    } else {
        pontuacaoComputador++;
        resultado.innerText += " O computador ganhou!";
    }

    document.getElementById("pontuacao-jogador").innerText = pontuacaoJogador;
    document.getElementById("pontuacao-computador").innerText = pontuacaoComputador;
}

function resetarJogo() {
    pontuacaoJogador = 0;
    pontuacaoComputador = 0;
    document.getElementById("pontuacao-jogador").innerText = pontuacaoJogador;
    document.getElementById("pontuacao-computador").innerText = pontuacaoComputador;
    document.getElementById("resultado").innerText = "";
}