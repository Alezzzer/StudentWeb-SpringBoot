import React, { useContext } from "react";
import { Link } from "react-router-dom";
import {
  Card,
  CardTitle,
  CardBody,
  CardSubtitle,
  CardText,
  Button,
} from "reactstrap";
import UserContext from "../context/UserContext";
import {
  POST_IMAGE_SERVE_URL,
  CustomDateFormatterFunc,
  CustomTextColorWrapper,
} from "../services/helper";

function PostView({ post, handlePostDelete }) {
  const { userState } = useContext(UserContext);
  return (
    <div className="PostView mb-3">
      <Card>
        <img
          alt={`${POST_IMAGE_SERVE_URL}/${post.image}`}
          src={`${POST_IMAGE_SERVE_URL}/${post.image}`}
          height="200px"
        />
        <CardBody>
          <CardTitle tag="h5">{post.title}</CardTitle>
          <CardSubtitle className="mb-2 text-muted" tag="h6">
            Kategorija : {post.category.name}
          </CardSubtitle>
          <CardText
            dangerouslySetInnerHTML={{
              __html: post.content.substring(0, 70) + "...",
            }}
          ></CardText>
          <CardText>
            {" "}
            <CustomTextColorWrapper>
              <b  style={{ color: "rgb(0, 255, 255)" }}>{post?.user?.name}</b>
            </CustomTextColorWrapper>
            {"  "}
           
            <CustomTextColorWrapper>
              <b  style={{ color: "rgb(0, 255, 255)" }}> {CustomDateFormatterFunc(post.date)}</b>
            </CustomTextColorWrapper>
            
          </CardText>
          <CardText>
            <b>Ukupno komentara : {post.comments.length}</b>
          </CardText>
          <CardText>
            {" "}
            <Link className="btn btn-sm "   style={{ backgroundColor: "rgb(125, 249, 255)" }}to={`/posts/${post.pid}`}>
              Pročitaj više
            </Link>
            {userState.loggedIn && post.user.uid === userState.data.uid && (
              <>
                <Button
                  className="btn btn-sm  ms-2 "  style={{ backgroundColor: "rgb(125, 249, 255)" }}
                  onClick={() => {
                    handlePostDelete(userState.data.username, post.pid);
                  }}
                >
                  <i className="fa-solid fa-trash"></i>
                </Button>
                <Button
                  tag={Link}
                  to={`/user/${userState.data.username}/editpost/${post.pid}`}
                  className="btn btn-sm  ms-2"   style={{ backgroundColor: "rgb(125, 249, 255)" }}
                >
                  <i className="fa-solid fa-pencil"></i>
                </Button>
              </>
            )}
          </CardText>
        </CardBody>
      </Card>
    </div>
  );
}

export default PostView;
