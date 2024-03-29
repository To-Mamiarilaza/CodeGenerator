
import axios from 'axios';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Signup() {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    document.title = "Inscription";

    const handleSubmit = (event) => {
        event.preventDefault();
        const utilisateur = {
            "username": username,
            "password": password
        };

        axios.post(API_BASE_URL + "/auth/signup", utilisateur)
        .then(response => {
            navigate("/login");
        })
        .catch(error => {
            setError(error);
        })

        setUsername('');
        setPassword('');
    };


    const API_BASE_URL = "http://localhost:8080";

    return (
        <>
            <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <link
                    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
                    rel="stylesheet"
                    integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
                    crossorigin="anonymous"
                />
                <link
                    rel="stylesheet"
                    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
                    integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
                    crossorigin="anonymous"
                    referrerpolicy="no-referrer"
                />
            </head>

            <nav class="navbar navbar-dark bg-dark">
                <div class="container-fluid">
                    <span class="navbar-brand mb-0 h1 ms-5">
                        <i class="fas fa-kiwi-bird me-4"></i>Lazy coding
                    </span>
                </div>
            </nav>

            <div className="login-container">
                <h2>Inscription</h2>
                <form onSubmit={handleSubmit}>
                    {error !== '' && <span style={"color: red"}>{{ error }}</span>}
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(event) => setUsername(event.target.value)}
                        required
                    />
                    <br/>
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                        required
                    />
                    <br/>
                    <button type="submit">s'inscrire</button>
                </form>
                <a href='login'>se connecter</a>
            </div>
        </>
    );
}

export default Signup;