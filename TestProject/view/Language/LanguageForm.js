
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function LanguageForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [error, setError] = useState(null);
  
  const [typeLanguages, setTypeLanguages] = useState([]);
  const [payss, setPayss] = useState([]);
  
  const [idLanguage, setIdLanguage] = useState();
  const [languageName, setLanguageName] = useState();
  const [etat, setEtat] = useState();
  const [idTypeLanguage, setIdTypeLanguage] = useState();
  const [idPays, setIdPays] = useState();

  document.title = "Nouvelle Language";
  document.body.style.backgroundColor = "#161616";

  // languages data api url
  const API_BASE_URL = "http://localhost:8080";

  // fetching all foreign key and language if edit
  useEffect(() => {
    
    axios.get(API_BASE_URL + "/typeLanguages", {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
    .then((response) => {
      setTypeLanguages(response.data.content)
      if(response.data.content.length > 0) {
        setIdTypeLanguage(response.data.content[0].idTypeLanguage)
      }
    })
    .catch((error) => {
      if (error.response.status === 401) {
        localStorage.removeItem("apktoken");
        navigate('/login');
      }
    })

    axios.get(API_BASE_URL + "/payss", {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
    .then((response) => {
      setPayss(response.data.content)
      if(response.data.content.length > 0) {
        setIdPays(response.data.content[0].idPays)
      }
    })
    .catch((error) => {
      if (error.response.status === 401) {
        localStorage.removeItem("apktoken");
        navigate('/login');
      }
    })


    if (id !== undefined) {
      axios
        .get(API_BASE_URL + "/languages/" + id, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          setIdLanguage(response.data.idLanguage);
          setLanguageName(response.data.languageName);
          setEtat(response.data.etat);
          setIdTypeLanguage(response.data.typeLanguage.idTypeLanguage);
          setIdPays(response.data.pays.idPays);
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
  
  const handleTypeLanguageSelectChange = (event) => {
    setIdTypeLanguage(event.target.value);
  };
  const handlePaysSelectChange = (event) => {
    setIdPays(event.target.value);
  };

  // save the language
  const saveLanguage = (e) => {
    e.preventDefault();

    const newLanguage = {
      "idLanguage": idLanguage,
      "languageName": languageName,
      "etat": etat,
      "typeLanguage": { 
        "idTypeLanguage": idTypeLanguage
      },
      "pays": { 
        "idPays": idPays
      }
    };

    if (id === undefined) {
      axios
      .post(API_BASE_URL + "/languages", newLanguage, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          navigate("/languages");
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
        .put(API_BASE_URL + "/languages/" + id, newLanguage, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          navigate("/languages");
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
  

  const typeLanguageRows = [];
  typeLanguages.forEach((typeLanguage) => {
    typeLanguageRows.push(
      <option value={typeLanguage.idTypeLanguage} selected={typeLanguage.idTypeLanguage === idTypeLanguage}>{typeLanguage.typeLanguageName}</option>
    );
  });

  const paysRows = [];
  payss.forEach((pays) => {
    paysRows.push(
      <option value={pays.idPays} selected={pays.idPays === idPays}>{pays.paysName}</option>
    );
  });

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
          <h4 class="text-white">Nouvelle language</h4>
          <div class="form">
            <form>
              <div class="mb-3">
                <label for="languageName" class="form-label text-white">LanguageName</label>
                <input id="languageName" value={languageName} onChange={(e) => setLanguageName(e.target.value)} type="text" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="etat" class="form-label text-white">Etat</label>
                <input id="etat" value={etat} onChange={(e) => setEtat(e.target.value)} type="number" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="typeLanguage" class="form-label text-white">TypeLanguage</label>
                <select id="typeLanguage" class="form-select" onChange={(e) => handleTypeLanguageSelectChange(e)}>{typeLanguageRows}</select>
              </div>
              <div class="mb-3">
                <label for="pays" class="form-label text-white">Pays</label>
                <select id="pays" class="form-select" onChange={(e) => handlePaysSelectChange(e)}>{paysRows}</select>
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
                  onClick={(e) => saveLanguage(e)}
                >
                  Enregistrer
                </button>
                <button
                  class="btn btn-outline-light px-5"
                  onClick={() => navigate("/languages")}
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