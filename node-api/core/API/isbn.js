import { default as axios } from 'axios';

const IsdnBookDetails = async (isbnKey) => {
  let res = await axios.get("https://api2.isbndb.com/book/"+isbnKey,{
    headers: {"Authorization": 'key ที่หายไป',"Accept":"*/*"},
  });
  console.log(res.data);
};
export { IsdnBookDetails}
