const { makeExecutableSchema, addMockFunctionsToSchema  } = require ('graphql-tools')
const resolvers = require('../resolvers')
const Profesor = require('./Profesor')
const Curso = require('./Curso')

const rootQuery = `

  union resultadoBusqueda= Profesor | Curso

  type Query{
    cursos: [Curso]
    profesores: [Profesor]
    curso(id: Int): Curso
    profesor(id: Int): Profesor
    buscar(query: String!): [resultadoBusqueda]
  }

type Mutation{
  profesorAdd(profesor: NuevoProfesor): Profesor
  profesorEdit(profesorid: Int!, profesor: ProfesorEditable): Profesor
  profesorDelete(profesorid: Int): Profesor
  cursoAdd(curso: NuevoCurso): Curso
  cursoEdit(cursoid: Int!, curso:CursoEditable): Curso
  cursoDelete(cursoid:Int!): Curso
}


`
const schema = makeExecutableSchema({
  typeDefs: [rootQuery, Profesor, Curso],
  resolvers
})



module.exports = schema
