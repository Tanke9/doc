# -*- coding: utf-8 -*-

def foreignt_exchange_calculator(amount):
    eur_to_usd_rate = 1.09

    return amount * eur_to_usd_rate

def run():
    print (' CALCULADORA DE DIVISAS');
    print ('Convierte euros a dolares')
    print ('')

    amount = float(raw_input('Ingresa la cantidad de euros a convertir '))

    result = foreignt_exchange_calculator(amount)

    print ('{} euros son ${} dolares'.format(amount, result))


if __name__=='__main__':
    run()
