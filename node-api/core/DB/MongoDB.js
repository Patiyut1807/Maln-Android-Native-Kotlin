import { MongoClient } from "mongodb";
import { v4 as uuidv4 } from "uuid";
const uri =
  "url ที่หายไป";
let mongoClientConnect = async () => {
  const client = new MongoClient(uri);
  try {
    await client.connect();
  } catch (e) {
    console.error(e);
  } finally {
    setTimeout(() => {
      client.close();
    }, 1500);
  }
};
async function listDatabases(client) {
  let databasesList = await client.db().admin().listDatabases();

  console.log("Databases:");
  databasesList.databases.forEach((db) => console.log(` - ${db.name}`));
}

let CreateBook = async (uid, bname, author, publisher, genre) => {
  const client = new MongoClient(uri);
  try {
    await client.connect();
    let Books = client.db("Maln").collection("Books");
    Books.insertOne({
      _id: uuidv4(),
      owner: uid,
      bname,
      author,
      publisher,
      genre,
    });
  } catch (e) {
    console.error(e);
  } finally {
    setTimeout(() => {
      client.close();
    }, 1500);
  }
};
let AddUser = async (uname, name, lastname, pass) => {
  const client = new MongoClient(uri);
  try {
    await client.connect();
    let User = client.db("Maln").collection("User");
    User.insertOne({ _id: uuidv4(), uname, name, lastname, pass });
  } catch (e) {
    console.error(e);
  } finally {
    setTimeout(() => {
      client.close();
    }, 1500);
  }
};
let checkLogIn = async (uname, pass) => {
  const client = new MongoClient(uri);
  try {
    await client.connect();
    let User = client.db("Maln").collection("User");
    let user = await User.findOne({ uname });
    if (!user)
      return {
        status: "failure",
      };
    if (user.pass === pass) {
      return {
        status: "success",
        value: {
          id: user["_id"],
          name: user["name"],
          lastname: user["lastname"],
          uname: user["uname"],
        },
      };
    } else {
      return {
        status: "failure",
      };
    }
  } catch (e) {
    console.error(e);
  } finally {
    setTimeout(() => {
      client.close();
    }, 1500);
  }
};
let getBooks = async (uid) => {
  const client = new MongoClient(uri);
  try {
    await client.connect();
    let Books = client.db("Maln").collection("Books");
    let res = await Books.find({ owner: uid }).toArray();
    console.log(res);
    if (res !== null)
      return res.map((res) => {
        return {
          id: res["_id"],
          owner: res.owner,
          bname: res.bname,
          author: res.author,
          publisher: res.publisher,
          genre: res.genre,
        };
      });
    return {};
  } catch (e) {
    console.error(e);
  } finally {
    setTimeout(() => {
      client.close();
    }, 1500);
  }
};
let getBook = async (id)=>{
  const client = new MongoClient(uri);
  try {
    await client.connect();
    let Books = client.db("Maln").collection("Books");
    let res = await Books.findOne({ _id: id });
    console.log(res);
    return res
  } catch (e) {
    console.error(e);
  } finally {
    setTimeout(() => {
      client.close();
    }, 1500);
  }
}
let editBook = async (id, bname, author, publisher, genre)=>{
  const client = new MongoClient(uri);
  try {
    await client.connect();
    let Books = client.db("Maln").collection("Books");
    let res = await Books.updateOne({ _id: id },{$set:{ bname, author, publisher,genre}});
    console.log(res);
    return true
  } catch (e) {
    console.error(e);
    return false
  } finally {
    setTimeout(() => {
      client.close();
    }, 1500);
  }
}
let delBook = async (id)=>{
  const client = new MongoClient(uri);
  try {
    await client.connect();
    let Books = client.db("Maln").collection("Books");
    let res = await Books.deleteOne({ _id: id });
    console.log(res);
    return true
  } catch (e) {
    console.error(e);
    return false
  } finally {
    setTimeout(() => {
      client.close();
    }, 1500);
  }
  
}
export { AddUser, CreateBook, checkLogIn, getBooks ,getBook,delBook,
  editBook};
