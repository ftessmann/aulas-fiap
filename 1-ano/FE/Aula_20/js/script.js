// localStorage(key, value)

// localStorage.setItem('meu gato', "tom");
// localStorage.setItem('meu rato', 'jerry');

// localStorage.getItem('meu gato');
// localStorage.getItem("meu rato");

// sessionStorage.setItem("nome", "fernando");

const user = document.getElementById("txtUser");
const password = document.getElementById("pwdPass");
const manterLogado = document.getElementById("manterLogado");

function validar() {
    if (user.value == "admin" && password.value == "12345") {
        if (manterLogado.checked) {
            localStorage.setItem("logado", "true")
        } else {
            sessionStorage.setItem('logado', 'true');
        }

        localStorage.setItem('nome', "fernando");
        window.location.href = "profile.html"; //redireciona
        
    } else {
        localStorage.setItem('erro', '403');
        sessionStorage.setItem('logado', "false");
        window.location.href = "form-login.html";
    } 
}