const buttonElement = document.getElementById("button");
const quadroElement = document.getElementById("quadro");

function changePosition() {
    var pos = 0;
    var direcao = "direita"
    tempo = setInterval(animation, 1);

    function animation() {
        if (direcao == "direita") {
            quadroElement.style.left = pos + "px";
            pos++;
            if (pos == 860) {   
                direcao = "baixo"
                pos = 0
                
            }
        }
        if (direcao == "baixo") {
            quadroElement.style.top = pos + "px"
            pos++;
            if (pos == 860) {
                direcao = "esquerda";
            }
        }
        if (direcao == "esquerda") {
            quadroElement.style.left = pos + "px"
            pos--;
            if (pos == 0) {
                direcao = "topo";
                pos = 860;
            }
        }
        if (direcao == "topo") {
            quadroElement.style.top = pos + "px"
            pos--;
            if (pos == 0) {
                clearInterval(tempo)
                changePosition();
            }
        }
    }
}

buttonElement.addEventListener("click", changePosition)

const cpf = document.getElementById("cpf");
const cep = document.getElementById("cep");
const tel = document.getElementById("tel");



function mascara(formato, campo) {
    obj = eval(campo);

    sep1 = ".";
    sep2 = "-";
    sep3 = "(";
    sep4 = ")";
    
    if (formato == 'cpf') {
        if (obj.value.length == 3) {
            obj.value = obj.value + sep1
        } else {
            if (obj.value.length == 7) {
                obj.value = obj.value + sep1
            } else {
                if (obj.value.length == 11) {
                    obj.value = obj.value + sep2
                }
            }
        }  
    }

    if (formato == 'cep') {
        if (obj.value.length == 5) {
            obj.value = obj.value + sep2
        }
    }

    if (formato == 'tel') {
        if (obj.value.length == 0) {
            obj.value = obj.value + sep3;
        } else if (obj.value.length == 3) {
            obj.value = obj.value + sep4;
        } else if (obj.value.length == 9) {
            obj.value = obj.value + sep2;
        }
    }
}