package com.platzi.profesoresplatzi.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

//Se va a encargar de estar llamando al objeto session. Lo vamos a llamar con la autowire. Cuando se ejecute la herencia es obligatorio
//escribit todos los metodos (abstracta)
public abstract class AbstractSession {

	@Autowired //Con autowired estoy llamando al objeto que implemenate en mi configuración
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
}
