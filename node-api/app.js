import express from 'express'
import { IsdnBookDetails } from './core/API/isbn.js'
import{CreateBook, mongoClientConnect} from './core/DB/MongoDB.js'
const app = express()
const port = 4000

app.get('/', (req, res) => {
  let client = mongoClientConnect()
  res.send('Hello World!')
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})