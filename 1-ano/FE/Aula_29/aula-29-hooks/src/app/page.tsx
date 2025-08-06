'use client'

import React, { useState } from "react";

export default function Home() {

  const [valor, setValor] = useState(0)

  const [dados, setDados] = useState('')

  const [nome, setNome] = useState('')

  const [nomeEnviado, setNomeEnviado] = useState('')

  const [mensagem, setMensagem] = useState("Mensagem")

  const handleMessage = () => {
    if (mensagem === "Mensagem") {
      setMensagem("Mensagem Alterada");
    } else {
      setMensagem("Mensagem");
    }
  }

  const handleName = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNome(event.target.value)
  }

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDados(event.target.value)
  }

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setNomeEnviado(nome);
  }

  return (
    <div className="bg-white text-black m-4">
      <div className="m-4">
        <h1>Trabalhando com useState</h1>

        <h2>Exemplo 01:</h2>

        <button 
          className='m-2 p-2 border rounded-md hover:cursor-pointer'
          onClick={ () => { setValor(valor + 1) } }
        >
            Incrementar
        </button>

        <p className="m-2">Valor: { valor }</p>

        <button
          className='m-2 p-2 border rounded-md hover:cursor-pointer'
          onClick={ () => { setValor(valor - 1) } }
          disabled={valor <= 0}
        >
          Decrementar
        </button>
        <p></p>
        <button
          className='m-2 p-2 border rounded-md hover:cursor-pointer'
          onClick={ () => { setValor(0)}}
        >
          Zerar
        </button>
      </div>
      <div className='m-4'>
        <h2>Exemplo 02:</h2>
          <p>
            Dados
          </p>
          <input 
            className='m-2 p-2 border rounded-md hover:cursor-text'
            type="text" 
            value={dados} 
            onChange={ handleChange}
          />
          <p>
            Dado digitado: {dados}
          </p>
      </div>
      <div className='m-4'>
        <h2>Exemplo 03:</h2>
        <form onSubmit={ handleSubmit }>
            <p>
              Nome:
            </p>

            <input 
              className='m-2 p-2 border rounded-md hover:cursor-text'
              type="text" 
              value={nome} 
              onChange={ handleName }
            />

            <button 
              className='m-2 p-2 border rounded-md hover:cursor-pointer'
              onClick={ () => {} }
              type='submit'
            >
              Enviar
            </button>

            <p>
              Nome digitado: {nomeEnviado}
            </p>
          </form>
      </div>

      <div className='m-4'>
        <h2>Exemplo 04:</h2>
          <p>
            {mensagem}
          </p>
          <button
            className='m-2 p-2 border rounded-md hover:cursor-pointer'
            onClick={ handleMessage }
          >
            Mudar mensagem
          </button>
      </div>
    </div>
  );
}
