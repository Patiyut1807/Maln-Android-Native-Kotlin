import * as fs from "fs";
import * as https from "https";
import express from "express";
import {
  CreateBook,
  AddUser,
  checkLogIn,
  getBooks,
  getBook,
  delBook,
  editBook
} from "./core/DB/MongoDB.js";

const key = fs.readFileSync("./core/cert/localhost+2-key.pem", "utf-8");
const cert = fs.readFileSync("./core/cert/localhost+2.pem", "utf-8");

const app = express();
const port = 4000;

app.get("/", (req, res) => {
  res.send("Hello World!");
});
app.get("/getbooks", async (req, res) => {
  let uid = req.query.uid;
  let books = await getBooks(uid);
  res.json(books);
});
app.get("/getbook", async (req, res) => {
  let id = req.query.id;
  let book = await getBook(id);
  res.json(book);
});
app.get("/signup", (req, res) => {
  AddUser(req.query.uname, req.query.name, req.query.lastname, req.query.pass);
  res.send("sign up successfully");
});
app.get("/signin", async (req, res) => {
  let check = await checkLogIn(req.query.uname, req.query.pass);
  res.json(check);
});
app.get("/insertbook", async (req, res) => {
  let uid = req.query.uid;
  let bname = req.query.bname;
  let author = req.query.author;
  let publisher = req.query.publisher;
  let genre = req.query.genre;
  try {
    CreateBook(uid, bname, author, publisher, genre);
    res.send("Insert successfully");
  } catch (e) {
    res.send("Insert fail");
  }
});
app.get("/delbook", async(req, res) => {
  let id = req.query.id;
  console.log(id);
  await delBook(id)
  res.send("success");
});
app.get("/editbook", async (req, res) => {
  let id = req.query.id;
  let bname = req.query.bname;
  let author = req.query.author;
  let publisher = req.query.publisher;
  let genre = req.query.genre;
  try {
    console.log(id, bname, author, publisher, genre);
    editBook(id, bname, author, publisher, genre);
    res.send("Edit successfully");
  } catch (e) {
    res.send("Edit fail");
  }
});
https.createServer({ key, cert }, app).listen(port);
