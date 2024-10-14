function trocarImagem() {
    lampada = document.getElementById("lampada");

    if (lampada.src.match("bulbon")) {
        lampada.src = "images/pic_bulboff.gif";
        document.body.style.backgroundColor = "#000";
    } else {
        lampada.src = "images/pic_bulbon.gif";
        document.body.style.backgroundColor = "#fff";
    }
}