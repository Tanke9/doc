const Model  = require('objection').Model;
const path = require('path')

class Usuario extends Model {
  static get tableName () {
    return 'usuario'
  }

}

module.exports = Usuario
