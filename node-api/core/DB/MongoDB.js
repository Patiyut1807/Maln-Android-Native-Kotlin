import { MongoClient } from "mongodb";

const uri =
  "mongodb+srv://64010479:Ten180745@malndb.vuerkny.mongodb.net/?retryWrites=true&w=majority";
let mongoClientConnect = async () => {
  const client = new MongoClient(uri);
  try {
    await client.connect();
  } catch (e) {
    console.error(e);
  } finally {
    await client.close();
  }
  CreateBook(client)
};
async function listDatabases(client) {
  let databasesList = await client.db().admin().listDatabases();

  console.log("Databases:");
  databasesList.databases.forEach((db) => console.log(` - ${db.name}`));
}
let CreateBook = async (client) => {
  let MalnDB = client.db("Maln");
  let Books = MalnDB.collection("Books");
  Books.insertOne({ _id:1,test: "test" });
};


export { mongoClientConnect ,CreateBook};
