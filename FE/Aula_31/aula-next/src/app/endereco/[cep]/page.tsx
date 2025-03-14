"use client";

import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import Link from "next/link";

interface EnderecoProps {
  cep: string;
  logradouro: string;
  bairro: string;
  cidade: string;
  uf: string;
}

export default function EnderecoPage() {
  const params = useParams();
  const [endereco, setEndereco] = useState<EnderecoProps | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [erro, setErro] = useState<string>("");
  
  const cep = Array.isArray(params.cep) ? params.cep[0] : params.cep;

  useEffect(() => {
    const buscarEndereco = async () => {
      try {
        const response = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
        const data = await response.json();
        
        if (data.erro) {
          setErro("CEP não encontrado");
          setEndereco(null);
        } else {
          setEndereco({
            cep: data.cep,
            logradouro: data.logradouro,
            bairro: data.bairro,
            cidade: data.localidade,
            uf: data.uf
          });
        }
      } catch (error) {
        setErro(`Erro ao buscar o CEP: ${error instanceof Error ? error.message : String(error)}`);
      } finally {
        setLoading(false);
      }
    };

    if (cep) {
      buscarEndereco();
    }
  }, [cep]);

  if (loading) {
    return (
      <div className="w-full h-screen mx-auto bg-white text-black p-4">
        <h1 className="text-3xl font-bold mb-4">Carregando...</h1>
      </div>
    );
  }

  if (erro) {
    return (
      <div className="w-full h-screen mx-auto bg-white text-black p-4">
        <h1 className="text-3xl font-bold mb-4">Erro</h1>
        <p className="text-red-500">{erro}</p>
        <Link href="/" className="mt-4 inline-block bg-blue-500 text-white rounded-md px-4 py-2">
          Voltar
        </Link>
      </div>
    );
  }

  return (
    <div className="w-full h-screen mx-auto bg-white text-black p-4">
      <h1 className="text-3xl font-bold mb-4">Detalhes do Endereço</h1>
      
      {endereco && (
        <div className="mt-6 p-4 border rounded-md bg-gray-50">
          <h2 className="text-2xl font-semibold mb-2">Endereço encontrado</h2>
          <p><strong>CEP:</strong> {endereco.cep}</p>
          <p><strong>Logradouro:</strong> {endereco.logradouro}</p>
          <p><strong>Bairro:</strong> {endereco.bairro}</p>
          <p><strong>Cidade:</strong> {endereco.cidade}</p>
          <p><strong>UF:</strong> {endereco.uf}</p>
          
          <Link href="/" className="mt-4 inline-block bg-blue-500 text-white rounded-md px-4 py-2">
            Nova Consulta
          </Link>
        </div>
      )}
    </div>
  );
}
