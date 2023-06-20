import { default as axios } from 'axios';

const IsdnBookDetails = async (isbnKey) => {
  let res = await axios.get("https://api2.isbndb.com/book/"+isbnKey,{
    headers: {"Authorization": '49994_49890d11b9afe71fb00be4ff35ad2893',"Accept":"*/*"},
  });
  console.log(res.data);
};
export { IsdnBookDetails}