import { myaxios } from "./helper";


//this post function will send data as form data along with imagedata
export const AddNewPostWithFormDataFunc = (
  postdata,
  imagedata,
  categoryname,
  username
) => {
  //console.log(postdata)
  const url = `/api/users/${username}/posts/${categoryname}`;
  //console.log(url);
  const formdata = new FormData();
  formdata.append("post", JSON.stringify(postdata));
  formdata.append("image", imagedata);
  return myaxios.post(url, formdata);
};


export const UpdatePostFunc = (postdata,username,postid) => {
  //console.log(postdata)
  const url = `/api/users/${username}/posts/${postid}`;
  return myaxios.put(url, postdata);
};

export const LoadAllPostsByCategoryFunc = (
  categoryname,
  pagenumber = 0,
  pagesize = 5,
  mostrecentfirst
) => {
  const url = `/api/posts/category/${categoryname}?pagenumber=${pagenumber}&pagesize=${pagesize}&mostrecentfirst=${mostrecentfirst}`;
  return myaxios.get(url).then((response) => {
    return response.data;
  });
};


export const LoadAllPostsByUsernameFunc = (username, mostrecentfirst) => {
  const url = `/api/users/${username}/posts?mostrecentfirst=${mostrecentfirst}`;
  return myaxios.get(url).then((response) => {
    return response.data;
  });
};


export const LoadPostByPostIdFunc = (postid) => {
  const url = `/api/posts/${postid}`;
  return myaxios.get(url).then((response) => {
    return response.data;
  });
};


export const AddNewCommentToPostFunc = (comment, username, postid) => {
  const url = `/api/users/${username}/posts/${postid}/comments`;
  return myaxios.post(url, comment);
};



export const DeletePostByPostIdFunc = (username, postid) => {
  const url = `/api/users/${username}/posts/${postid}`;
  return myaxios.delete(url).then((response) => {
    return response.data;
  });
};
