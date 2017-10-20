module.exports =`

type Usuario{
  id: ID!
  nombre: String!
  email:String!
  password: String!
}

input NuevoUsuario{
  nombre: String!
}

input UsuarioEditable{
  nombre: String
}

`
