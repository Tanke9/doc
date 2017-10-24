const Usuario = require('./models/Usuario')
const bcrypt= require ('bcrypt')
const jwt=require('jsonwebtoken')
const lodash =require('lodash')

const links = [
  {
    id: 1,
    url: 'http://graphql.org/',
    description: 'The Best Query Language'
  },
  {
    id: 2,
    url: 'http://dev.apollodata.com',
    description: 'Awesome GraphQL Client'
  },
];

const resolvers = {


  Query:{
    me: (_,args, {USER})=>{
      if (USER) {
        return Usuario.query().findById(USER.id);
      }
      return null;
    } ,
   usuarios: () => Usuario.query(),
  }


  Mutation: {
    usuarioAdd: (_, args) => {
      return Usuario.query().insert(args.usuario)
    },
    usuarioEdit: (_,args)=>{
      return Usuario.query().patchAndFetchById(args.usuarioid, args.usuario)
    },
    usuarioDelete: (_, args) =>{
        return Usuario.query().findById(args.usuarioid).then((usuario) => {
        return Usuario.query().deleteById(args.usuarioid).then((filasBorradas) => {
           if (filasBorradas>0) return usuario
           throw new Error (`El usuario con id ${args.usuarioid} no se puede eliminar `)
        })
      })
    },

    register: async (_, args) => {
        const newUser = args;
        newUser.password= await bcrypt.hash(newUser.password, 12)
        return Usuario.query().insert(newUser);
      },
     login: async(_,args, {SECRET}) => {
        const newLogin = args;
         const newUser= await Usuario.query().where('email',newLogin.email)
         const valid = await bcrypt.compare(newLogin.password, newUser[0].password);
         console.log(valid);
         if (!valid){
            throw new Error (`Password Incorrect `)
         }
         //token = 'dfsdfdsafdsfdsiufisdiosdufsoaufiops.fjsdfjsalfjas.fjsdjfsaldkjfals'
         //verify: needs secret | use me for autentication
         //decode: no secret | use on the client side
          const token = jwt.sign(
            {
            user: lodash.pick(newUser[0], ['id', 'nombre'])
            } ,
            SECRET,  {
             expiresIn: '1y',
           } )
           return token;

}
  }
}

module.exports = resolvers
