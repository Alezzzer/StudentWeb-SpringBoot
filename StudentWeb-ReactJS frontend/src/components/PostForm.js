import React, { useContext, useEffect, useRef, useState } from "react";
import JoditEditor from "jodit-react";
import {
  Card,
  CardHeader,
  CardBody,
  Form,
  FormGroup,
  Label,
  Input,
  Button,
  Container,FormText
} from "reactstrap";
import { LoadAllCategoriesFunc } from "../services/category-service";
import { toast } from "react-toastify";
import {  AddNewPostWithFormDataFunc } from "../services/post-service";

import UserContext from "../context/UserContext";

function PostForm({ LoadPostsByUsername }) {
  const { userState } = useContext(UserContext);
  const [imagedata,setImagedata]=useState(null);
  const [stateData, setStateData] = useState({
    title: "",
    content: "",
    categoryname: "",
  });

  const textAreaEditor = useRef(null);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    //console.log("INSIDE EFFECT OF LOAD CATEGORIES");
    LoadAllCategoriesFunc()
      .then((data) => {
        setCategories([...data]);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  function handleFileChange(event){
    console.log(event.target.files[0]);
    setImagedata(event.target.files[0]);

  }

  function handleChange(event) {
    if (event?.target)
      setStateData({ ...stateData, [event.target.name]: event.target.value });
    else setStateData({ ...stateData, content: event });
  }
  
  function handleSubmit(event) {
    event.preventDefault();
    console.log(stateData);
    if (stateData.categoryname.trim() === "") {
      toast.error("Izaberi kategoriju!");
      return;
    }
    if (stateData.title.trim() === "") {
      toast.error("Naslov ne može biti prazan!");
      return;
    }
    if (stateData.content.trim() === "") {
      toast.error("Sadržaj ne može biti prazan!");
      return;
    }

    AddNewPostWithFormDataFunc(stateData,imagedata, stateData.categoryname, userState.data.username)
      .then((response) => {
        //console.log(response.data);
        setStateData({
          title: "",
          content: "",
          categoryname: "",
        });
        setImagedata(null);
        document.getElementById("image").value=null;
        LoadPostsByUsername(userState.data.username, true);
        toast.success("Objava je uspešno objavljena!");
      })
      .catch((error) => {
        console.log(error);
        toast.error("Nešto nije dobro!");
      });
  }
  function handleReset(event) {
    setStateData({
      title: "",
      content: "",
      categoryname: "",
    });
    setImagedata(null);
  }

  return (
    <div className="PostForm container">
      <div className="row">
        <div className="col-md-10 offset-md-1">
          <Card className="my-2 shadow">
            <CardHeader className="text-center">
              <h3>Napiši novu objavu</h3>
            </CardHeader>
            <CardBody>
              <Form onSubmit={handleSubmit}>
                <FormGroup>
                  <Label for="category">Izaberi</Label>
                  <Input
                    id="categoryname"
                    name="categoryname"
                    type="select"
                    onChange={handleChange}
                    defaultValue={0}
                  >
                    <option disabled value={0}>
                      --Izaberi kategoriju--
                    </option>
                    {categories.map((category) => (
                      <option key={category.cid} value={category.name}>
                        {category.name}
                      </option>
                    ))}
                  </Input>
                </FormGroup>
                <FormGroup>
                  <Label for="title">Naslov</Label>
                  <Input
                    id="title"
                    name="title"
                    placeholder="Unesi naslov objave"
                    type="text"
                    value={stateData.title}
                    onChange={handleChange}
                  />
                </FormGroup>
                <FormGroup>
                  <Label for="content">Tekst</Label>
                  <JoditEditor
                    ref={textAreaEditor}
                    value={stateData.content}
                    //
                    onChange={handleChange}
                  />
                </FormGroup>
                <FormGroup>
                  <Label for="image">Izaberi sliku objave</Label>
                  <Input id="image" name="image" type="file" onChange={handleFileChange}/>
                  <FormText>
                   Slika iznad će postati glavna slika tvoje objave!
                  </FormText>
                </FormGroup>
                <Container className="text-center">
                  <Button className="btn btn-sm">Objavi</Button>
                  <Button
                    
                    type="reset"
                    onClick={handleReset}
                    className="btn btn-sm ms-2 " style={{ backgroundColor: "rgb(125, 249, 255)" }} 
                  >
                    Osveži
                  </Button>
                </Container>
              </Form>
            </CardBody>
          </Card>
        </div>
      </div>
    </div>
  );
}

export default PostForm;
