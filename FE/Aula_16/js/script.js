const pwEl = document.getElementById('pw');
const copyEl = document.getElementById('copy');
const lengthEl = document.getElementById('length');
const upperEl = document.getElementById('upper');
const lowerEl = document.getElementById('lower');
const numberEl = document.getElementById('numbers');
const symbolsEl = document.getElementById('symbols');
const generateEl = document.getElementById('generate');

const upperLetters = "ABCDEFGHIJKLMNOPQRSTUVXWYZ"

const lowerLetters = "abcdefghijklmnopqrstuvxwyz"

const numbers = "0123456789"

const symbols = "!@#$%&*(){}[]?/"

valorAleatorio = upperLetters[Math.floor(Math.random() * upperLetters.length)]

function getUpperCase() {
    return upperLetters[Math.floor(Math.random() * upperLetters.length)];
}

function getLowerCase() {
    return lowerLetters[Math.floor(Math.random() * lowerLetters.length)];
}

function getNumbers() {
    return numbers[Math.floor(Math.random() * numbers.length)];
}

function getSymbols() {
    return symbols[Math.floor(Math.random() * symbols.length)];
}

function generatePassword() {
    const initialPassword = [];

    if (upperEl.checked) {
        initialPassword.push(getUpperCase());
    }

    if (lowerEl.checked) {
        initialPassword.push(getLowerCase());
    }

    if (numberEl.checked) {
        initialPassword.push(getNumbers());
    }

    if (symbolsEl.checked) {
        initialPassword.push(getSymbols());
    }

    if (initialPassword.length === 0) return ""

    return initialPassword[Math.floor(Math.random() * initialPassword.length)]
}

function createPassword() {
    const length = lengthEl.value;
    var password = '';
    for (i = 0; i < length; i++) {
        password += generatePassword();
    }
    pwEl.innerHTML = password;
}

generateEl.addEventListener("click", createPassword);