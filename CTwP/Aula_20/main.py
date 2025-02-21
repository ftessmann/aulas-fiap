import unittest
import time
from functools import wraps

def timer_decorator(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        start_time = time.time()
        result = func(*args, **kwargs)
        end_time = time.time()
        execution_time = end_time - start_time
        print(f"Tempo de execução de {func.__name__}: {execution_time:.6f} segundos")
        return result
    return wrapper

class TestIsPrimeFunction(unittest.TestCase):
    def test_primes(self):
        primes = [3, 5, 7, 11, 13, 17, 19, 23, 29]
        for prime in primes:
            self.assertTrue(is_prime(prime))

    def test_non_primes(self):
        non_primes = [0, 1, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20]
        for non_prime in non_primes:
            self.assertFalse(is_prime(non_prime))
            
    def test_negative_number(self):
        negative_numbers = [-1, -2, -3, -4, -5]
        for negative in negative_numbers:
            self.assertFalse(is_prime(negative))
            
    def test_zero(self):
        self.assertFalse(is_prime(0))        

def other_prime(n):
    if n <= 1:
        return False
    for i in range (2, n):
        if n % i == 0:
            return False
    return True

def is_prime(n):
    if n <= 1:
        return False
    if n % 2 == 0:
        return False
    for i in range(3, int(n**0.5) + 1, 2):
        if n % i == 0:
            return False
    return True

@timer_decorator  
def print_other_prime(n):
    for i in range (n+1):
        if other_prime(i):
            print(i)

@timer_decorator            
def print_number(n):
    for i in range(n+1):
        if is_prime(i):
            print(i)

def main():
    print("Insira um número inteiro:")
    n = int(input())
    start_time_print = time.time()
    print_other_prime(n)
    end_time = time.time()
    execution_time = end_time - start_time_print
    
    start_timer = time.time()
    print_number(n)
    end_timer = time.time()
    execution_time_other = end_timer - start_timer 
    print(f"primeira função {execution_time}")
    print(f"segunda função {execution_time_other}")
    unittest.main()
    
if __name__ == "__main__":
    main()
    
