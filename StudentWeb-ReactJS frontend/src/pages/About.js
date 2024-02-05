import React from "react";
import { Link } from "react-router-dom";
import { Card } from "reactstrap";
import BaseComponent from "../components/BaseComponent";
function About() {
  return (
    <BaseComponent>
      <div className="About" style={{ minHeight: "500px" }}>
        <Card
          className="p-3 m-3"
          style={{ backgroundColor: "rgb(240, 255, 255)" }}
        >
          <h3
            style={{
              textAlign: "center",
              color: "rgb(0, 255, 255)",
              fontStyle: "italic",
            }}
          >
            Šta je StudentWeb?
          </h3>
          <div className="container text-center" >
            <p style={{textAlign:"justify",
    paddingTop: '50px',
    paddingLeft:'350px',
    paddingRight:'350px',
    boxSizing: 'content-box',
  }}  >
             
StudentWeb je forum koji predstavlja vitalnu online zajednicu gde se dele obaveštenja o aktuelnim događajima, pruža podrška studentima i podstiče živahne diskusije o raznovrsnim temama koje su relevantne za studentski život. Sa bogatim sadržajem koji obuhvata od organizacije događaja do promišljenih rasprava, ovaj sajt postaje izvor informacija, inspiracije i zajedništva među studentima.
            </p>
           
           
          </div>
        </Card>
      </div>
    </BaseComponent>
  );
}

export default About;
