def temEspaco(mat: list) -> bool:
    for lin in mat:
        for cel in lin:
            if cel == ' ':
                return True
    return False

def haGanhador(mat: list) -> bool:
    for lin in mat:
        if lin[0] == lin[1] == lin[2] and lin[0] != ' ':
            return True
    for col in range(3):
        if mat[0][col] == mat[1][col] == mat[2][col] and mat[0][col] != ' ':
            return True
    if mat[0][0] == mat[1][1] == mat[2][2] and mat[0][0] != ' ':
        return True
    if mat[0][2] == mat[1][1] == mat[2][0] and mat[0][2] != ' ':
        return True
    return False

def joga(mat: list, lin: int, col: int, jog: str) -> bool:
    if lin < 0 or lin > 2 or col < 0 or col > 2:
        return False
    
    if mat[lin][col] != ' ':
        return False
    
    mat[lin][col] = jog
    return True

def criaTabuleiro() -> list:
    return [[' ' for _ in range(3)] for _ in range(3)]

def trocaJogador(jog: str) -> str:
    return 'O' if jog == 'X' else 'X'

def exibirTabuleiro(mat: list):
    print("┌───┬───┬───┐")
    print(f"│ {mat[0][0]} │ {mat[0][1]} │ {mat[0][2]} │")
    print("├───┼───┼───┤")
    print(f"│ {mat[1][0]} │ {mat[1][1]} │ {mat[1][2]} │")
    print("├───┼───┼───┤")
    print(f"│ {mat[2][0]} │ {mat[2][1]} │ {mat[2][2]} │")
    print("└───┴───┴───┘")
    
def main():
    print("Bem-vindo ao Jogo da Velha!")
    print("Para jogar, digite a linha (0-2) e a coluna (0-2) da sua jogada.")
    
    tabuleiro = criaTabuleiro()
    jogador_atual = 'X'
    
    while temEspaco(tabuleiro) and not haGanhador(tabuleiro):
        exibirTabuleiro(tabuleiro)
        print(f"Vez do jogador '{jogador_atual}'")
        
        try:
            linha = int(input("Linha (0-2): "))
            coluna = int(input("Coluna (0-2): "))
            
            if joga(tabuleiro, linha, coluna, jogador_atual):
                if haGanhador(tabuleiro):
                    exibirTabuleiro(tabuleiro)
                    print(f"Jogador '{jogador_atual}' venceu!")
                    break
                
                if not temEspaco(tabuleiro):
                    exibirTabuleiro(tabuleiro)
                    print("Empate!")
                    break
                
                jogador_atual = trocaJogador(jogador_atual)
            else:
                print("Jogada inválida! Tente novamente.")
        except ValueError:
            print("Entrada inválida! Digite números de 0 a 2.")
    
    print("Fim do jogo!")

if __name__ == "__main__":
    main()