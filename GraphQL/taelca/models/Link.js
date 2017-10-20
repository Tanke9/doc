const { Model } = require('objection')
const path = require('path')

class Link extends Model {
  static get tableName () {
    return 'links'
  }

}

module.exports = Link
