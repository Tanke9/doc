const { makeExecutableSchema, addMockFunctionsToSchema  } = require ('graphql-tools')
const casual = require('casual')

const typeDefs = `
# Esto es un curso en el sistema
  type Curso {
      id: ID!
      titulo: String!
      # Esta es la descripcion del curso
      descripcion: String!
      profesor: Profesor
      rating: Float @deprecated(reason: "no queremos puntuar mas")
      comentarios: [Comentario]
  }

  type Profesor{
    id: ID!
    nombre: String!
    nacionalidad: String!
    genero: Genero
    cursos: [Curso]
  }

  enum Genero{
    MASCULINO
    FEMENINO
  }

  type Comentario{
    id: ID!
    nombre: String!
    cuerpo: String!
  }

  type Query{
    cursos: [Curso]
    profesores: [Profesor]
    curso(id: Int): Curso
    profesor(id: Int): Profesor
  }
`
const resolvers = {
  Query:{
    cursos: () =>{
      return [{
          id: 1,
          titulo: 'Curso de GraphQL',
          descripcion: 'Aprendiendo GraphQL'
      }, {
          id: 2,
          titulo: 'Curso de Java',
          descripcion: 'Aprendiendo Java'
      } ]
    }
  },
  Curso: {
    profesor: () =>{
      return{
        nombre: 'Pedro',
        nacionalidad: 'Argentina'
      }
    },
    comentarios: () =>{
      return[{
        nombre: 'Juan',
        cuerpo: 'Buen video'
      }]
    }
  }
}

const schema = makeExecutableSchema({
  typeDefs,
  resolvers
})

addMockFunctionsToSchema ({
   schema,
   mocks:{
     Curso: ()=>{
       return{
         id: casual.uuid,
         titulo: casual.title,
         descripcion: casual.text
       }
     },
     Profesor: ()=>{
       return{
         nombre:casual.name,
         nacionalidad:casual.country
       }
     }
   },
   preserveResolvers:true
})

module.exports = schema
