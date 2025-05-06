import React from "react";
import BannerBackground from "../Assets/home-banner-background.png";
import Navbar from "./navbar";
import HomeImage from "../Assets/home.jpg";


const Home = () => {
  return (
    <div className="home-container">
      <Navbar />
      <div className="home-banner-container">
        <div className="home-bannerImage-container">
          <img src={BannerBackground} alt="" />
      
        </div>
        <div className="home-text-section">
          <p className="primary-subheading">LibraryWorld</p>
          <h1 className="primary-heading">
          Your Gateway to Infinite Knowledge
          </h1>
          <p className="primary-text">
          A library is a dynamic hub of knowledge and community engagement, providing access to a vast array of resources, fostering lifelong learning, and enriching lives through the democratization of information.
          </p>
          {/* <button className="secondary-button">
            Explore 
          </button> */}
        </div>
        <div className="home-image-section">
          
          <img src={HomeImage} alt="" />

        </div>
      </div>
    </div>
  );
};

export default Home;