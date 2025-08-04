"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

export default function Home() {
  const [inputCep, setInputCep] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);
  const [erro, setErro] = useState<string>("");
  const router = useRouter();

  const buscarCep = async () => {
    setErro("");
    const cepLimpo = inputCep.replace(/\D/g, "");
    if (cepLimpo.length !== 8) {
      setErro("CEP deve conter 8 dígitos");
      return;
    }
    
    setLoading(true);
    
    try {
      const response = await fetch(`https://viacep.com.br/ws/${cepLimpo}/json/`);
      const data = await response.json();
      
      if (data.erro) {
        setErro("CEP não encontrado");
      } else {
        router.push(`/endereco/${cepLimpo}`);
      }
    } catch (error) {
      setErro(`Erro: ${error instanceof Error ? error.message : String(error)}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="w-full h-screen mx-auto bg-white text-black p-4">
      <h1 className="text-3xl font-bold mb-4">Consulta de CEP</h1>
      
      <form className="mt-4" onSubmit={(e) => e.preventDefault()}>
        <div className="flex flex-col mb-4">
          <label htmlFor="cep" className="mb-1">CEP:</label>
          <div className="flex">
            <input 
              className="w-[240px] rounded-md border px-4 py-2"
              type="text" 
              name="cep" 
              id="cep"
              value={inputCep}
              onChange={(e) => setInputCep(e.target.value)}
              placeholder="Digite o CEP"
            />
            <button 
              className="ml-2 cursor-pointer bg-emerald-500 text-white rounded-md px-4 py-2 text-lg hover:bg-emerald-600 disabled:bg-gray-400"
              type="button"
              onClick={buscarCep}
              disabled={loading}
            >
              {loading ? "Buscando..." : "Buscar"}
            </button>
          </div>
          {erro && <p className="text-red-500 mt-1">{erro}</p>}
        </div>
      </form>
    </div>
  );
}
