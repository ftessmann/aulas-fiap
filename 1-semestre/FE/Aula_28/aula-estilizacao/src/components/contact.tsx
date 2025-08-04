"use client";
import { useState } from "react";

export const Contact = () => {

    const [ count, setCount] = useState(0)

    return (
        <div>
            <p>Contador: {count}</p>
            <button onClick={() => setCount( count + 1)}>Contar</button>
            <h1>Contact Page</h1>
        </div>
    );
};