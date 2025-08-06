'use client';

import { useEffect, useState } from "react";

export default function Home() {

  const [valor, setValor] = useState(0);

  const [users, setUsers] = useState<string[]>([]);

  useEffect( () => {
    document.title = `Contador: ${valor}`;
  }, [ valor ]);

  useEffect(() => {
    const listaUsuarios = async () => {
      const response = await fetch('https://jsonplaceholder.typicode.com/users');
      const data = await response.json();
      setUsers(data.map((user : {
        name: string
      }) => user.name));
    }

    listaUsuarios();

  });

  return (
    <div>
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
      <div className="m-4">
        <h1>Lista de usuários com useEffect</h1>
        <ul>
          {users.map((user, index) => (
            <li key={index}>{user}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}
