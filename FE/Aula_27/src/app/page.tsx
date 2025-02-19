"use client";

import { Button } from "./components/button";
import { Card } from "./components/card";
import { ToDo } from "./components/to-do";

function Home() {
  return (
    <div className="bg-slate-700 p-4">
      <h1 className="text-3xl">Hello World</h1>
      <Card />
      <Button 
        text="Click me"
        className="text-red-500"
        onClick={() => alert("Button clicked")}
      />

      <ToDo />
    </div>
  );
}

export default Home;
