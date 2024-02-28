
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function TypeLanguageForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [error, setError] = useState(null);
  
  
  const [idTypeLanguage, setIdTypeLanguage] = useState();
  const [typeLanguageName, setTypeLanguageName] = useState();
  const [status, setStatus] = useState();

  document.title = "Nouvelle TypeLanguage";
  document.body.style.backgroundColor = "#161616";

  // typeLanguages data api url
  const API_BASE_URL = "http://localhost:8080";

  // fetching all foreign key and typeLanguage if edit
  useEffect(() => {
    

    if (id !== undefined) {
      axios
        .get(API_BASE_URL + "/typeLanguages/" + id)
        .then((response) => {
          setIdTypeLanguage(response.data.idTypeLanguage);
          setTypeLanguageName(response.data.typeLanguageName);
          setStatus(response.data.status);
        })
        .catch((error) => {
          setError(error.message);
        });
    }
  }, []);
  

  // save the typeLanguage
  const saveTypeLanguage = (e) => {
    e.preventDefault();

    const newTypeLanguage = {
      "idTypeLanguage": idTypeLanguage,
      "typeLanguageName": typeLanguageName,
      "status": status
    };

    if (id === undefined) {
      axios
      .post(API_BASE_URL + "/typeLanguages", newTypeLanguage)
        .then((response) => {
          navigate("/typeLanguages");
        })
        .catch((error) => {
          setError(error.message);
        });
      } else {
      axios
        .put(API_BASE_URL + "/typeLanguages/" + id, newTypeLanguage)
        .then((response) => {
          navigate("/typeLanguages");
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
          <h4 class="text-white">Nouvelle typeLanguage</h4>
          <div class="form">
            <form>
              <div class="mb-3">
                <label for="typeLanguageName" class="form-label text-white">TypeLanguageName</label>
                <input id="typeLanguageName" value={typeLanguageName} onChange={(e) => setTypeLanguageName(e.target.value)} type="text" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="status" class="form-label text-white">Status</label>
                <input id="status" value={status} onChange={(e) => setStatus(e.target.value)} type="number" class="form-control"/>
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
                  onClick={(e) => saveTypeLanguage(e)}
                >
                  Enregistrer
                </button>
                <button
                  class="btn btn-outline-light px-5"
                  onClick={() => navigate("/typeLanguages")}
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