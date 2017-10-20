module.exports = {

  development: {
    client: 'mysql',
    connection: {
      host: 'localhost',
      user: 'root',
      password: 'root',
      database: 'taelca'
    },
    useNullAsDefault: true
  },

  production: {
    // Acá irían los datos para la conexión
    // en un ambiente de producción
  }
}
