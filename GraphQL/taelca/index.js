  const express = require ('express')
  // This package automatically parses JSON requests.
  const bodyParser = require ('body-parser')
// This package will handle GraphQL server requests and responses
// for you, based on your schema.
  const {graphqlExpress, graphiqlExpress} = require('apollo-server-express')
  const schema = require('./schema')
  const cors = require('cors')

require('./db/setup')

const SECRET = 'fdsakfjsañd34543534fjksda'

  const app = express()


  app.use(
    '/graphql',
    bodyParser.json(),
    graphqlExpress({
      schema,
      context: {SECRET},
      formatError: (error) =>{
        return {
          codigo: 'a43',
          name: error.name,
          mensaje: error.message
        }
      }
    }))

  app.use(
    '/graphiql',
    graphiqlExpress({
      endpointURL: '/graphql'
    })
  )



  const PORT = 5680

  app.listen(PORT, ()  =>{
    console.log('Servidor corriendo en puerto 5680')
  })
