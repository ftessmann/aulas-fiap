const readline = require('readline');

const CONDICOES_DE_VITORIA = [
    [1, 2, 3], [4, 5, 6], [7, 8, 9],
    [1, 4, 7], [2, 5, 8], [3, 6, 9],
    [1, 5, 9], [3, 5, 7]
]

class JogoDaVelha {
    constructor() {
        this.jogador = "X";
        this.tabuleiro = {
            1: " ", 2: " ", 3: " ",
            4: " ", 5: " ", 6: " ",
            7: " ", 8: " ", 9: " "
        };
        this.rl = readline.createInterface({
            input: process.stdin,
            output: process.stdout
        });
    }

    mostrarTabuleiro() {
        console.log(
            ` ${this.tabuleiro[1]} | ${this.tabuleiro[2]} | ${this.tabuleiro[3]} \n` +
            "------------\n" +
            ` ${this.tabuleiro[4]} | ${this.tabuleiro[5]} | ${this.tabuleiro[6]} \n` +
            "------------\n" +
            ` ${this.tabuleiro[7]} | ${this.tabuleiro[8]} | ${this.tabuleiro[9]} \n`
        );
    }

    jogar(posicao) {
        if (this.tabuleiro[posicao] === ' ') {
            this.tabuleiro[posicao] = this.jogador;
            return true
        }
        return false;
    }

    jogarComputador() {
        const jogadaVitoria = this.encontrarJogadaVitoria('O');
        if (jogadaVitoria) {
            this.jogar(jogadaVitoria);
            return;
        }

        const jogadaBloqueio = this.encontrarJogadaVitoria('X');
        if (jogadaBloqueio) {
            this.jogar(jogadaBloqueio);
            return;
        }
        
        let posicoesDisponiveis = Object.keys(this.tabuleiro).filter(posicao => this.tabuleiro[posicao] === ' ');

        if (posicoesDisponiveis.length > 0) {
            let posicaoAleatoria = posicoesDisponiveis[Math.floor(Math.random() * posicoesDisponiveis.length)];
            this.jogar(posicaoAleatoria);
        }
    }

    verificarVitoria() {
        for (let condicao of CONDICOES_DE_VITORIA) {
            if (this.tabuleiro[condicao[0]] !== ' ' &&
                this.tabuleiro[condicao[0]] === this.tabuleiro[condicao[1]] &&
                this.tabuleiro[condicao[1]] === this.tabuleiro[condicao[2]]) {
                return true;
            }
        }
        return false;
    }

    encontrarJogadaVitoria(jogador) {
        for (let condicao of CONDICOES_DE_VITORIA) {
            let jogadasNaLinha = condicao.map(pos => this.tabuleiro[pos]);
            let jogadasDoJogador = jogadasNaLinha.filter(val => val === jogador).length;
            let espacosVazios = jogadasNaLinha.filter(val => val === ' ').length;
    
            if (jogadasDoJogador === 2 && espacosVazios === 1) {
                return condicao[jogadasNaLinha.indexOf(' ')];
            }
        }
        return null;
    }

    isTabuleiroCheio() {
        return !Object.values(this.tabuleiro).includes(' ');
    }
    
    rodarJogo(contraComputador) {
        this.mostrarTabuleiro();
        
        if (contraComputador && this.jogador === "O") {
            this.jogarComputador();
            
            if (this.verificarVitoria()) {
                this.mostrarTabuleiro();
                console.log(`Jogador ${this.jogador} venceu!`);
                this.rl.close();
                return;
            }
            
            if (this.isTabuleiroCheio()) {
                this.mostrarTabuleiro();
                console.log("Empate!");
                this.rl.close();
                return;
            }
            
            this.jogador = "X";
            this.rodarJogo(contraComputador);
            return;
        }
        
        this.rl.question(`Jogador ${this.jogador}, escolha uma posição(1-9): `, (posicao) => {
            if (this.jogar(posicao)) {
                this.mostrarTabuleiro();
                
                if (this.isTabuleiroCheio() && !this.verificarVitoria()) {
                    console.log("Empate!");
                    this.rl.close();
                    return;
                }
    
                if (this.verificarVitoria()) {
                    console.log(`Jogador ${this.jogador} venceu!`);
                    this.rl.close();
                    return;
                }
    
                this.jogador = this.jogador === "X" ? "O" : "X";
                this.rodarJogo(contraComputador);
            } else {
                console.log("Posição inválida!");
                this.rodarJogo(contraComputador);
            }
        });
    }
    
    start() {
        console.log("Bem-vindo ao Jogo da Velha!");
        console.log("[1] - Jogar contra outro jogador");
        console.log("[2] - Jogar contra o computador");

        this.rl.question("Escolha uma opção: ", (opcao) => {
            const contraComputador = opcao === "2";
            this.rodarJogo(contraComputador);
        });
    }
}

const jogo = new JogoDaVelha();
jogo.start();