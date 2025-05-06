import { Link, Outlet } from 'react-router-dom';
import logo from '../../styles/images/logo.png';
// import HomeImage from "../../styles/images/home.png"; // Adjust the path based on the actual directory structure

import { IonIcon } from '@ionic/react';
import { homeOutline, personOutline, notificationsOutline, logOutOutline, bookmarkOutline, chatbubbleOutline, bookOutline, arrowRedoOutline, settingsOutline } from "ionicons/icons"
import { useLocation } from 'react-router-dom';
import '../../AdminDashboardcss/Dashboard.css'


function AdminDashboardNav() {
    const location = useLocation();
    console.log(location.state);

    return (
        <div className="container" id="container">
            <div className="navigation">
                <ul>
                    <li>
                        <Link to="/admindashboard">
                            <span> <img className="user_logo" src={logo} alt="logo" /></span>
                            <span className="title_title">Hariny Library</span>
                        </Link>
                    </li>
                    <li>
                        <Link to="/admindashboard">
                            <span className="icon"><IonIcon icon={homeOutline}></IonIcon></span>
                            <span className="title">Dashboard</span>
                        </Link>
                    </li>

                    <li>
                        <Link to="/admindashboard/users">
                            <span className="icon"><IonIcon icon={personOutline}></IonIcon></span>
                            <span className="title">User Details</span>
                        </Link>
                    </li>
                    <li>
                        <Link to="/admindashboard/addbooks">
                            <span className="icon"><IonIcon icon={bookOutline}></IonIcon></span>
                            <span className="title">Add Books</span>
                        </Link>
                    </li>
                    <li>
                        <Link to="/admindashboard/bookrequests">
                            <span className="icon"><IonIcon icon={notificationsOutline}></IonIcon></span>
                            <span className="title">Book Requests</span>
                        </Link>
                    </li>
                    
                    <li>
                        <Link to="/admindashboard/bookissuehistory">
                            <span className="icon"><IonIcon icon={bookmarkOutline}></IonIcon></span>
                            <span className="title">Book Issue History</span>
                        </Link>
                    </li>
                    <li>
                        <Link to="/admindashboard/setting">
                            <span className="icon"><IonIcon icon={settingsOutline}></IonIcon></span>
                            <span className="title">Settings</span>
                        </Link>
                    </li>
                    <li>
                        <Link to="/admindashboard/logout">
                            <span className="icon"><IonIcon icon={logOutOutline}></IonIcon></span>
                            <span className="title">Log Out</span>
                        </Link>
                    </li>
                </ul>
            </div>
            <Outlet />
        </div>
    );
}
export default AdminDashboardNav;