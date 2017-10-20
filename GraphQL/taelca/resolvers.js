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
   usuarios: () => Usuario.query(),
    usuario: (rootValue,args) => Usuario.query().findById(args.id),
    allLinks:() => links

  },
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
    createLink: (_, args) => {
      const newLink = Object.assign({id: links.length + 1}, args);
      links.push(newLink);
      return newLink;
    },
    register: async (_, args) => {
        const newUser = args;
        newUser.password= await bcrypt.hash(newUser.password, 12)
        return Usuario.query().insert(newUser);
      },
     login: async(_,args, {SECRET}) => {
        const newLogin = args;
         const newUser= await Usuario.query().where('email',newLogin.email)
         console.log(newUser);
         console.log(newUser[0].password);
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
            myname: 'gallego',
            } ,
            SECRET,  {
             expiresIn: '1y',
           } )
           return token;

}
// login: (_,args, {SECRET}) => {
//     const usuariop = Usuario.query().where('email',args.email)
//     console.log(usuariop);
//     console.log(args.password);
//     console.log(usuariop.password);
//      const valid =  bcrypt.compare(args.password, usuariop.password, function(err, res){
//        if(err) {
//            console.log('Comparison error: ', err);
//        }
//        const token = jwt.sign(
//          {
//            usuariop: lodash.pick(usuariop, ['id','nombre' ])
//          } ,
//          SECRET,  {
//           expiresIn: '1y',
//         } )
//         return token;
//      });
// }

      // console.log('*************************************************************************');
      // console.log(SECRET);
      // console.log(args.email);
      // const variable8 = 8
      //  console.log(Usuario.query().findById(variable8))
      // const userBD = Usuario.query().where('email',args.email)
      // const usermail=userBD.email;
      // console.log(usermail);
      // console.log('**************************************************************************');
      // if(!userBD){
      //   throw new Error ('Not user with  ${args.email} email ')
      // }
      // const valid = bcrypt.compare(args.password, userBD.password)
      // if(!valid){
      //     throw new Error ('Incorrect password')
      // }
    // }
  }
}

module.exports = resolvers
