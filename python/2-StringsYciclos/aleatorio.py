# -- coding: utf-8 -*-
import random

def run():
    number_found = False
    random_number = random.randint(0,20)

    while not number_found:
        number = int(raw_input('Intenta un numero: '))

        if number == random_number:
            print ('Felicades!! Encontraste el numero. El {}'.format(number))
            number_found = True
        elif number> random_number:
            print('El numer es mas pequeño')
        else:
            print('El numero es mas grande')

if __name__ == '__main__':
    run()
