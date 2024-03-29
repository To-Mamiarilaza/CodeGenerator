
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function UtilisateurForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [error, setError] = useState(null);
  
  
  const [idUtilisateur, setIdUtilisateur] = useState();
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();

  document.title = "Nouvelle Utilisateur";
  document.body.style.backgroundColor = "#161616";

  // utilisateurs data api url
  const API_BASE_URL = "http://localhost:8080";

  // fetching all foreign key and utilisateur if edit
  useEffect(() => {
    

    if (id !== undefined) {
      axios
        .get(API_BASE_URL + "/utilisateurs/" + id)
        .then((response) => {
          setIdUtilisateur(response.data.idUtilisateur);
          setUsername(response.data.username);
          setPassword(response.data.password);
        })
        .catch((error) => {
          setError(error.message);
        });
    }
  }, []);
  

  // save the utilisateur
  const saveUtilisateur = (e) => {
    e.preventDefault();

    const newUtilisateur = {
      "idUtilisateur": idUtilisateur,
      "username": username,
      "password": password
    };

    if (id === undefined) {
      axios
      .post(API_BASE_URL + "/utilisateurs", newUtilisateur)
        .then((response) => {
          navigate("/utilisateurs");
        })
        .catch((error) => {
          setError(error.message);
        });
      } else {
      axios
        .put(API_BASE_URL + "/utilisateurs/" + id, newUtilisateur)
        .then((response) => {
          navigate("/utilisateurs");
        })
        .catch((error) => {
          setError(error.message);
        });
    }
  };
  

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

      <div class="container mt-4" data-bs-theme="dark">
        <div class="mx-auto col-md-8 px-3 mb-4">
          <h4 class="text-white">Nouvelle utilisateur</h4>
          <div class="form">
            <form>
              <div class="mb-3">
                <label for="username" class="form-label text-white">Username</label>
                <input id="username" value={username} onChange={(e) => setUsername(e.target.value)} type="text" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="password" class="form-label text-white">Password</label>
                <input id="password" value={password} onChange={(e) => setPassword(e.target.value)} type="text" class="form-control"/>
              </div>

              { error !== null && (
                <div class="mb-3">
                  <label class="form-label text-danger">
                    {error}
                  </label>
                </div>
              )}

              <div class="d-flex mt-4">
                <button
                  class="btn btn-outline-info px-5 me-3"
                  onClick={(e) => saveUtilisateur(e)}
                >
                  Enregistrer
                </button>
                <button
                  class="btn btn-outline-light px-5"
                  onClick={() => navigate("/utilisateurs")}
                >
                  Retour
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}