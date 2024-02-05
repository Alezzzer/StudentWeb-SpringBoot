import React from "react";
import { Card } from "reactstrap";
import BaseComponent from "../components/BaseComponent";
import Typewriter from "typewriter-effect";
import blog from "../images/blog.jpg";
import { Link } from "react-router-dom";
function Home() {
  return (
    <BaseComponent>
      <div className="Home container" style={{ minHeight: "500px" }}>
        <Card
          className="p-3 m-3"
          style={{ backgroundColor: "rgb(240, 255, 255)" }}
        >
        <h3 style={{
              textAlign: "center",
              color: "rgb(0, 255, 255)",
              fontStyle:"italic"
            }}></h3>
          <h1
            style={{
              textAlign: "center",
              color: " rgb(0, 255, 255) ",
              fontStyle:"italic"
            }}
            className="m-2 rounded-5"
          >
            <Typewriter 
              options={{
                
                strings: [
                  "Dobro došli na StudentWeb",
                 
                ],
                autoStart: true,
                loop: true,
              }}
            />
          </h1>
          <div className="container text-center">
            <img
              src={blog}
              alt="Blog Image"
              height={"250px"}
              width={"300px"}
              className="rounded-5"
             
            />
            <br />
            <Link to="/signup" className="btn btn-sm  mt-2 mb-4"  style={{ backgroundColor: "rgb(125, 249, 255)" }} >
              Registruj se
            </Link>
           
          </div>
        </Card>
      </div>
    </BaseComponent>
  );
}

export default Home;
