import React, { useState } from "react";
import BaseComponent from "../components/BaseComponent";
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Form,
  FormGroup,
  Label,
  Input,
  Button,
  FormFeedback,
} from "reactstrap";
import { NavLink } from "react-router-dom";

import { SignUpFunc } from "../services/user-service";
import { toast } from "react-toastify";

function Signup(props) {
  const [stateData, setStateData] = useState({
    name: "",
    username: "",
    password: "",
    about: "",
  });

  const [errors, setErrors] = useState({});

  function handleChange(event) {
    setStateData((oldStateData) => {
      return { ...oldStateData, [event.target.name]: event.target.value };
    });
  }

  function handleSubmit(event) {
    event.preventDefault();

    SignUpFunc(stateData)
      .then((response) => {
        toast.success("Korisnik je uspešno registrovan!");
        setErrors({});
        setStateData({
          name: "",
          username: "",
          password: "",
          about: "",
        });
      })
      .catch((error) => {
        setErrors(error.response.data.errors);
        toast.error(JSON.stringify(error.response.data.message));
      });
  }

  function handleReset(event) {
    setStateData({
      name: "",
      username: "",
      password: "",
      about: "",
    });

    document.getElementById("about").value = "";
  }

  return (
    <BaseComponent>
      <div className="Signup container" style={{minHeight:"500px"}}>
        <div className="row">
          <div className="col-md-6 offset-md-3">
            <Card
              className="my-2 shadow"
              style={{backgroundColor:"rgb(240, 255, 255)"}}
            >
              <CardHeader>
                <h2 style={{textAlign:"center"}}>REGISTRUJ SE</h2>
              </CardHeader>
              <CardBody>
                <div className="SignUpForm">
                  <Form onSubmit={handleSubmit}>
                    <FormGroup>
                      <Label for="name">Ime</Label>
                      <Input
                        id="name"
                        name="name"
                        placeholder="Unesi ime"
                        type="text"
                        value={stateData.name}
                        onChange={handleChange}
                        invalid={errors?.name ? true : false}
                      />
                      <FormFeedback>{errors?.name}</FormFeedback>
                    </FormGroup>
                    <FormGroup>
                      <Label for="username">Korisničko ime</Label>
                      <Input
                        id="username"
                        name="username"
                        placeholder="Unesi korisničko ime"
                        type="text"
                        value={stateData.username}
                        onChange={handleChange}
                        invalid={errors?.username ? true : false}
                      />
                      <FormFeedback>{errors?.username}</FormFeedback>
                    </FormGroup>
                    <FormGroup>
                      <Label for="password">Šifra</Label>
                      <Input
                        id="password"
                        name="password"
                        placeholder="Enter Password"
                        type="password"
                        value={stateData.password}
                        onChange={handleChange}
                        invalid={errors?.password ? true : false}
                      />
                      <FormFeedback>{errors?.password}</FormFeedback>
                    </FormGroup>

                    <FormGroup>
                      <Label for="about">O meni</Label>
                      <Input
                        id="about"
                        name="about"
                        type="textarea"
                        value={stateData.about}
                        onChange={handleChange}
                      />
                    </FormGroup>
                    <div className="container text-center">
                    <Button className="btn btn-sm">Registracija</Button>
                    <Button className="btn btn-sm  ms-2" style={{ backgroundColor: "rgb(125, 249, 255)" }} type="reset" onClick={handleReset}>
                     Osveži
                    </Button>
                    </div>
                   
                  </Form>
                </div>
              </CardBody>
              <CardFooter className="text-center">
                <NavLink to="/login"  style={{ color: "rgb(125, 249, 255)" }}>Već si registrovan? Prijavi se.</NavLink>
              </CardFooter>
            </Card>
          </div>
        </div>
      </div>
    </BaseComponent>
  );
}

export default Signup;
