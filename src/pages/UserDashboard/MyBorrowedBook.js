import "../../styles/BorrowedBooks.css";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from "react-hot-toast";
import logo from '../../styles/images/user.png';

function MyBorrowedBook() {
    const [bookTitle, setBookTitle] = useState({});
    const [borrow, setBorrow] = useState([]);
    const userId = parseInt(sessionStorage.getItem('userId'));

    useEffect(() => {
        axios.get(`http://localhost:8080/api/borrow/user/${userId}`)
            .then(response => {
                setBorrow(response.data);
                const bookIds = response.data.map(br => br.book.id);
                const uniqueBookIds = [...new Set(bookIds)];
                Promise.all(uniqueBookIds.map(getBook))
                    .then(books => {
                        const updatedBookTitles = {};
                        books.forEach(book => updatedBookTitles[book.id] = book.title);
                        setBookTitle(updatedBookTitles);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            })
            .catch(error => {
                console.log(error);
            });
    }, [userId]);

    const getBook = async (id) => {
        try {
            const response = await axios.get('http://localhost:8080/api/books/' + id);
            return { id, title: response.data.bookTitle };
        } catch (error) {
            console.log(error);
        }
    }

    const handleReturn = (borrwID, bookID, userID) => {
        axios.put('http://localhost:8080/api/borrow/return', { borrowId: borrwID, bookId: bookID, userId: userID })
            .then(res => {
                axios.get('http://localhost:8080/api/requests')
                    .then(response => {
                        const requests = response.data;
                        let returnId = null;
                        for (let i = 0; i < requests.length; i++) {
                            const req = requests[i];
                            if (req.user.id === userID && req.book.id === bookID) {
                                returnId = req.id;
                                break;
                            }
                        }
                        if (returnId !== null) {
                            axios.put(`http://localhost:8080/api/requests_return/${returnId}`, { user: { id: userID }, book: { id: bookID } })
                                .then(response => {
                                    toast.success("Book return success");
                                })
                                .catch(error => {
                                    toast.error("Failed to return");
                                    console.log(error);
                                });
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }).catch(err => console.log(err));
    }

    return (
        <div class="main">
            <div class="topbar">
                <div class="toggle"></div>
                <div class="user">
                    <img class="navLogo" src={logo} alt="logo" />
                </div>
            </div>
            <div id="dashboard-container" style={{ marginTop: "2rem" }}>
                <table class="dashboard-table" id="book-list">
                    <thead>
                        <tr>
                            <th>Borrow Id</th>
                            <th>Book Id</th>
                            <th>Book Title</th>
                            <th>Borrow Date</th>
                            <th>Due Date</th>
                            <th>Return Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {borrow.map(br => (
                            <tr key={br.id}>
                                <td>{br.borrowId}</td>
                                <td>{br.book.id}</td>
                                <td>{bookTitle[br.book.id]}</td>
                                <td>{br.borrowDate}</td>
                                <td>{br.dueDate}</td>
                                <td>{br.returnDate}</td>
                                <td>
                                    {br.status === 'ACCEPTED' ? (
                                        <button onClick={() => handleReturn(br.borrowId, br.book.id, userId)}>Return</button>
                                    ) : (
                                        br.status
                                    )}
                                </td>
                            </tr>))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default MyBorrowedBook;