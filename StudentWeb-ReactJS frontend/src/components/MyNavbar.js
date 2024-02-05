import React, { useContext, useState } from "react";
import { NavLink as ReactNavLink, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
} from "reactstrap";

//import { UserContext } from "../App";
import UserContext from "../context/UserContext";
import { doLogoutFunc } from "../services/helper";

function MyNavbar(props) {
  const navigate = useNavigate();
  const { userState,setUserState} = useContext(UserContext);

  const [isOpen, setIsOpen] = useState(false);
  const toggle = () => setIsOpen(!isOpen);

  function handleLogout() {
    doLogoutFunc(() => {
      setUserState({data:{},loggedIn:false});
      navigate("/login");
      toast.success("Uspešno ste odjavljeni");
    });
  }

  return (
    <div className="MyNavbar">
      <Navbar color="dark" dark expand="md">
        <NavbarBrand tag={ReactNavLink} to="/home">
          <i className="fa-sharp fa-solid fa-marker"></i>STUDENTWEB
        </NavbarBrand>
        <NavbarToggler onClick={toggle} />
        <Collapse isOpen={isOpen} navbar>
          <Nav className="me-auto" navbar>
            <NavItem>
              <NavLink tag={ReactNavLink} to="/home">
                POČETNA
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={ReactNavLink} to="/about">
                O STUDENTWEB-U
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={ReactNavLink} to="/posts/category/All">
                SVE OBJAVE
              </NavLink>
            </NavItem>
            {userState.loggedIn && <></>}
          </Nav>

          <Nav className="ms-auto" navbar>
            {userState.loggedIn ? (
              <>
                <NavItem>
                  <NavLink
                    tag={ReactNavLink}
                    to={`/user/${userState?.data?.username}/profile`}
                  >
                    {userState?.data.username?.toUpperCase()}
                  </NavLink>
                </NavItem>
                <NavItem>
                  <NavLink
                    tag={ReactNavLink}
                    to={`/user/${userState?.data?.username}/addpost`}
                  >
                    MOJE OBJAVE
                  </NavLink>
                </NavItem>
                <NavItem>
                  <NavLink onClick={handleLogout}>ODJAVI SE</NavLink>
                </NavItem>
              </>
            ) : (
              <>
                <NavItem>
                  <NavLink tag={ReactNavLink} to="/login">
                    ULOGUJ SE
                  </NavLink>
                </NavItem>
                <NavItem>
                  <NavLink tag={ReactNavLink} to="/signup">
                    REGISTRUJ SE
                  </NavLink>
                </NavItem>
              </>
            )}
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
}

export default MyNavbar;
