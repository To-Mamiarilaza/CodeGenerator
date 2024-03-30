
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function FicheForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [error, setError] = useState(null);
  
  
  const [idFiche, setIdFiche] = useState();
  const [name, setName] = useState();
  const [firstname, setFirstname] = useState();
  const [address, setAddress] = useState();
  const [mail, setMail] = useState();
  const [etat, setEtat] = useState();
  const [salaire, setSalaire] = useState();
  const [admin, setAdmin] = useState();

  document.title = "Nouvelle Fiche";
  document.body.style.backgroundColor = "#161616";

  // fiches data api url
  const API_BASE_URL = "http://localhost:8080";

  // fetching all foreign key and fiche if edit
  useEffect(() => {
    

    if (id !== undefined) {
      axios
        .get(API_BASE_URL + "/fiches/" + id, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          setIdFiche(response.data.idFiche);
          setName(response.data.name);
          setFirstname(response.data.firstname);
          setAddress(response.data.address);
          setMail(response.data.mail);
          setEtat(response.data.etat);
          setSalaire(response.data.salaire);
          setAdmin(response.data.admin);
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
  

  // save the fiche
  const saveFiche = (e) => {
    e.preventDefault();

    const newFiche = {
      "idFiche": idFiche,
      "name": name,
      "firstname": firstname,
      "address": address,
      "mail": mail,
      "etat": etat,
      "salaire": salaire,
      "admin": admin
    };

    if (id === undefined) {
      axios
      .post(API_BASE_URL + "/fiches", newFiche, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          navigate("/fiches");
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
        .put(API_BASE_URL + "/fiches/" + id, newFiche, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          navigate("/fiches");
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
          <h4 class="text-white">Nouvelle fiche</h4>
          <div class="form">
            <form>
              <div class="mb-3">
                <label for="name" class="form-label text-white">Name</label>
                <input id="name" value={name} onChange={(e) => setName(e.target.value)} type="text" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="firstname" class="form-label text-white">Firstname</label>
                <input id="firstname" value={firstname} onChange={(e) => setFirstname(e.target.value)} type="text" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="address" class="form-label text-white">Address</label>
                <input id="address" value={address} onChange={(e) => setAddress(e.target.value)} type="text" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="mail" class="form-label text-white">Mail</label>
                <input id="mail" value={mail} onChange={(e) => setMail(e.target.value)} type="text" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="etat" class="form-label text-white">Etat</label>
                <input id="etat" value={etat} onChange={(e) => setEtat(e.target.value)} type="number" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="salaire" class="form-label text-white">Salaire</label>
                <input id="salaire" value={salaire} onChange={(e) => setSalaire(e.target.value)} type="text" class="form-control"/>
              </div>
              <div class="mb-3">
                <input class="form-check-input" type="checkbox" id="admin" value={admin} onChange={(e) => setAdmin(e.target.value)} />
                <label class="form-check-label text-white" for="admin">Admin</label>
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
                  onClick={(e) => saveFiche(e)}
                >
                  Enregistrer
                </button>
                <button
                  class="btn btn-outline-light px-5"
                  onClick={() => navigate("/fiches")}
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