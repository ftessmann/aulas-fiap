interface ButtonProps {
    text: string;
    className?: string;
    onClick?: () => void;
};

export const Button = ({
    text,
    className,
    onClick
}: ButtonProps) => {
    return (
        <button 
            onClick={onClick} 
            className={`bg-slate-200 p-2 text-black rounded-md m-2 hover:cursor-pointer ${className}`}
        >
            {text}
        </button>
    );
};