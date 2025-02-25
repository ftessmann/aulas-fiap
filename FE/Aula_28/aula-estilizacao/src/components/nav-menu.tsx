import Link from "next/link";

export const NavMenu = () => {

    const routes = [
        { path: '/', label: 'Home' },
        { path: '/about', label: 'Sobre' },
        { path: '/contact', label: 'Contato' },
        { path: '/product', label: 'Produto'}
    ];

    return ( 
        <div className="flex items-center justify-center">
            <ul className="flex space-x-4 font-bold text-black">
                {routes.map(route => {
                    return (
                        <li 
                            key={route.path}
                            className="px-3 py-2 hover:pointer"
                        >
                            <Link 
                                href={route.path}
                            >
                                {route.label}
                            </Link>
                        </li>
                    )
                })}
            </ul>
        </div>
    );
};