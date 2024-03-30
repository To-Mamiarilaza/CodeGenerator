
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function PaysForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [error, setError] = useState(null);
  
  
  const [idPays, setIdPays] = useState();
  const [paysName, setPaysName] = useState();

  document.title = "Nouvelle Pays";
  document.body.style.backgroundColor = "#161616";

  // payss data api url
  const API_BASE_URL = "http://localhost:8080";

  // fetching all foreign key and pays if edit
  useEffect(() => {
    

    if (id !== undefined) {
      axios
        .get(API_BASE_URL + "/payss/" + id, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          setIdPays(response.data.idPays);
          setPaysName(response.data.paysName);
        })
        .catch((error) => {
          if (error.response.status === 401) {
            localStorage.removeItem("apktoken");
            navigate('/login');
          }
          setError(error.message);
        });
    }
  }, []);
  

  // save the pays
  const savePays = (e) => {
    e.preventDefault();

    const newPays = {
      "idPays": idPays,
      "paysName": paysName
    };

    if (id === undefined) {
      axios
      .post(API_BASE_URL + "/payss", newPays, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          navigate("/payss");
        })
        .catch((error) => {
          if (error.response.status === 401) {
            localStorage.removeItem("apktoken");
            navigate('/login');
          }
          setError(error.message);
        });
      } else {
      axios
        .put(API_BASE_URL + "/payss/" + id, newPays, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          navigate("/payss");
        })
        .catch((error) => {
          if (error.response.status === 401) {
            localStorage.removeItem("apktoken");
            navigate('/login');
          }
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

      <nav className="navbar navbar-dark bg-dark">
        <div className="container-fluid">
          <span className="navbar-brand mb-0 h1 ms-5">
            <a href="/home" className="text-decoration-none text-white">
              <i className="fas fa-kiwi-bird me-4"></i>Lazy coding
            </a>
          </span>
        </div>
      </nav>

      <div class="container mt-4" data-bs-theme="dark">
        <div class="mx-auto col-md-8 px-3 mb-4">
          <h4 class="text-white">Nouvelle pays</h4>
          <div class="form">
            <form>
              <div class="mb-3">
                <label for="paysName" class="form-label text-white">PaysName</label>
                <input id="paysName" value={paysName} onChange={(e) => setPaysName(e.target.value)} type="text" class="form-control"/>
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
                  onClick={(e) => savePays(e)}
                >
                  Enregistrer
                </button>
                <button
                  class="btn btn-outline-light px-5"
                  onClick={() => navigate("/payss")}
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