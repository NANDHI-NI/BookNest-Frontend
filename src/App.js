import './App.css';
import Landing from './pages/Landing';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AdminLogin from './pages/AdminDashboard/AdminLogin';
import Login from './pages/UserDashboard/Login';
import DashboardNav from './pages/UserDashboard/DashboardNav';
import MyAccount from './pages/UserDashboard/MyAccount';
import Dashboard from './pages/UserDashboard/Dashboard';
import Error from './pages/404';

import AdminDashboardNav from './pages/AdminDashboard/AdminDashboardNav';
import AddBooks from './pages/AdminDashboard/AddBooks';
import AdminDashboard from './pages/AdminDashboard/AdminDashboard';
import BookIssueHistory from './pages/AdminDashboard/bookuserhistory'
import Setting from './pages/AdminDashboard/Setting';
import AddAdmin from './pages/AdminDashboard/addadmin';
import Users from './pages/AdminDashboard/Users';
import LogOut from './pages/AdminDashboard/logout';
import UserLogOut from './pages/UserDashboard/userlogout';


import BookRequests from './pages/AdminDashboard/BookRequests';
import MyBorrowedBook from './pages/UserDashboard/MyBorrowedBook';
import BorrowedBooks from './pages/AdminDashboard/bookuserhistory';

import IssuedBooks from './pages/UserDashboard/Issuebook';


function App() {
  return (
    <div className="">
      <Router>
        <Routes>
          <Route path="/" element={<Landing />} />
          <Route path="/dashboard" element={<DashboardNav />} >
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/dashboard/myaccount" element={<MyAccount />} />
            <Route path="/dashboard/myborrowedbooks" element={<MyBorrowedBook />} />
            <Route path="/dashboard/userlogout" element={<UserLogOut />} />
            <Route path='/dashboard/issuebooks' element={<IssuedBooks/>}/>
          </Route>

          <Route path="/admindashboard" element={<AdminDashboardNav />} >
            <Route path='/admindashboard' element={<AdminDashboard />} />
            <Route path='/admindashboard/users' element={<Users />} />
            <Route path='/admindashboard/addbooks' element={<AddBooks />} />
            <Route path='/admindashboard/bookrequests' element={<BookRequests />} />
            <Route path='/admindashboard/bookissuehistory' element={<BookIssueHistory />} />
            <Route path='/admindashboard/addadmin' element={<AddAdmin />} />
            <Route path='/admindashboard/logout' element={<LogOut />} />
            <Route path='/admindashboard/setting' element={<Setting />} />
          </Route>

          <Route path="/admin/*" element={<AdminLogin />} />
          <Route path="/login/*" element={<Login />} />
          <Route path="/*" element={<Error />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
