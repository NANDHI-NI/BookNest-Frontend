import React, { useState, useEffect } from 'react';
import axios from 'axios';
import logo from '../../styles/images/user.png';
import { toast } from 'react-hot-toast';


function Dashboard() {
    const userId = sessionStorage.getItem('userId');
    const [userName, setUserName] = useState("");

    // Debug: Ensure userId is available
    console.log('userId from sessionStorage:', userId);

    // Fetch user details
    useEffect(() => {
        
        if (userId) {
            axios.get(`http://localhost:8080/api/users/${userId}`)
                .then(response => {
                    // Debug: Check the API response
                    console.log('User data:', response.data);
                    if (response.data && response.data.name) {
                        setUserName(response.data.name);
                    } else {
                        console.error('User name not found in response');
                    }
                })
                .catch(error => {
                    console.error('Error fetching user details:', error);
                });
        } else {
            console.error('User ID not found in session storage');
        }
    }, [userId]);

    // Fetch books and their statuses
    const [books, setBooks] = useState([]);
    const [bookStatuses, setBookStatuses] = useState({});
    useEffect(() => {
        axios.get('http://localhost:8080/api/books')
            .then(response => {
                setBooks(response.data);
                return axios.get('http://localhost:8080/api/requests');
            })
            .then(response => {
                const statusDict = response.data.reduce((acc, request) => {
                    const bookId = request.book.id;
                    const status = request.status;
                    acc[bookId] = status;
                    return acc;
                }, {});
                setBookStatuses(statusDict);
            })
            .catch(error => {
                console.error('Error fetching books or requests:', error);
            });
    }, []);

    
    return (
        <div className="main">
            <div className="topbar" style={{ display: 'flex', alignItems: 'center' }}>
                <div style={{ flexGrow: 1 }}>
                    <h3 style={{ margin: '4px', padding: '4px', color: '#2d6d05' }}>Hello, {userName}</h3>
                    <p style={{ margin: '4px', padding: '4px', color: '#2d6d05' }}><strong>YOUR ID: {userId}</strong></p>
                </div>
                <div className="user">
                    <img className="navLogo" src={logo} alt="logo" />
                </div>
            </div>
            <div id="dashboard-container" style={{ marginTop: "2rem" }}>
                <table className="dashboard-table" id="book-list">
                    <thead>
                        <tr>
                            <th>Book ID</th>
                            <th>Book Name</th>
                            <th>Quantity</th>
                            <th>Availability</th>
                            <th>Description</th>
                            {/* <th>Ratings</th> */}
                        </tr>
                    </thead>
                    <tbody>
                        {books.map(book => (
                            <tr key={book.id}>
                                <td>{book.id}</td>
                                <td>{book.bookTitle}</td>
                                <td>{book.quantity}</td>
                                <td>{book.availability}</td>
                                <td>{book.description}</td>
                                
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Dashboard;
