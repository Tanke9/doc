const Curso = require('./models/Curso')
const Profesor = require('./models/Profesor')

const resolvers = {
  Query:{
   cursos: () => Curso.query().eager('[profesor, comentarios]'),
    profesores: () => Profesor.query().eager('cursos'),
    curso: (rootValue, args) => Curso.query().eager('[profesor, comentarios]').findById(args.id),
    profesor: (rootValue,args) => Profesor.query().eager('cursos').findById(args.id),
    buscar: (_, args) => {
      return [
        Profesor.query().findById(3),
        Curso.query().findById(3)
      ]
    }
  },
  resultadoBusqueda: {
    __resolveType: (obj) => {
     if (obj.nombre) return 'Profesor'
     return 'Curso'
    }
  },
  Mutation: {
    profesorAdd: (_, args) => {
      return Profesor.query().insert(args.profesor)
    },
    profesorEdit: (_,args)=>{
      return Profesor.query().patchAndFetchById(args.profesorid, args.profesor)
    },
    profesorDelete: (_, args) =>{
        return Profesor.query().findById(args.profesorid).then((profesor) => {
        return Profesor.query().deleteById(args.profesorid).then((filasBorradas) => {
           if (filasBorradas>0) return profesor
           throw new Error (`El pforesor con id ${args.profesorid} no se puede eliminar `)
        })
      })
    },
      cursoAdd: (_, args) =>{
        return Curso.query().insert(args.curso)
      },
      cursoEdit: (_, args) =>{
        return Curso.query().patchAndFetchById(args.cursoid, args.curso)
      },
      cursoDelete: (_, args) => {
          Curso.query().findById(args.cursoid).then ((curso) => {
            Curso.query().deleteById(args.cursoid).then(() => curso)
          } )
      }
  }
}

module.exports = resolvers
