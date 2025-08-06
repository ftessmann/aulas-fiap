import Image from 'next/image';

export const Product = () => {
    return (
        <div>
            <h1>Product page</h1>
            <Image 
                src="/images/claptrap.png"
                alt="Claptrap"
                width={230}
                height={300}
            />
        </div>
    )
}