# -- coding: utf-8 -*-

def factorial(number):
    if number == 0:
        return 1

    return number * factorial(number-1)

def main():
    number = int(raw_input('Escribe un numero: '))
    resultado = factorial (number)
    print ('El factorial de {} es {}'.format(number,resultado))

if __name__ == '__main__':
    main()
