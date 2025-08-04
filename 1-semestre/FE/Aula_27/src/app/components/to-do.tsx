import { Button } from "./button";
import { useState } from "react";

export const ToDo = () => {
    const [tasks, setTasks] = useState<string[]>([]); // cria um estado com um array de strings vazio
    const [newTask, setNewTask] = useState(""); // primeiro elemento é o estado, segundo é a função que atualiza o estado

    const addTask = () => {
        if (newTask.trim() !== "") {
            setTasks([...tasks, newTask]);
            setNewTask("");
        }
    }

    const removeTask = (index: number) => {
        setTasks(tasks.filter((_, i) => i !== index));
    }

    return (
        <div>
            <input 
                type="text" 
                className="p-2 m-4 text-black"
                value={ newTask }
                onChange={(e) => {setNewTask(e.target.value)}}
            />
            <Button 
                text="Adicionar tarefa"
                className="bg-emerald-700 text-white"
                onClick={ addTask}
            />

            <ul className="text-black">
                {tasks.map((task, index) => (
                    <li key={index}>
                        {task}
                        <Button 
                            text="Remover tarefa"
                            className="bg-red-700"
                            onClick={ () => removeTask(index) }
                        />
                </li>
                ))}
            </ul>

        </div>
    );
};