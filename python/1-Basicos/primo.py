# -- coding: utf-8 -*-

def is_prime(number):
    if number < 2:
        return False
    elif number == 2:
        return True
    elif number >2 and number %2 == 0:
        return False
    else:
        for i in range (3, number):
            if number % i == 0:
                return False;
    return True;

def run():
    number = int(raw_input('Numero: '))
    result = is_prime(number)
    if result is True:
        print ('{} es primo'.format(number))
    else:
        print ('{} no es primo'.format(number))


if __name__ == '__main__':
    run()
