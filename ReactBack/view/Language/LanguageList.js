
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function LanguageList() {
  const navigate = useNavigate();
  const [languages, setLanguages] = useState([]);

  document.title = "Languages";
  document.body.style.backgroundColor = "#161616";

  // fetching languages data
  const API_BASE_URL = "http://localhost:8080";

  useEffect(() => {
    axios.get(API_BASE_URL + "/languages").then((response) => {
      setLanguages(response.data);
    }).catch((error) => {
        alert(error)
    });
  }, []);

  // delete action
  const deleteLanguage = (idLanguage) => {
    axios.delete(API_BASE_URL + "/languages/" + idLanguage).then((response) => {
        const newLanguagesList = languages.filter(language => language.idLanguage != idLanguage)
        setLanguages(newLanguagesList);
    }).catch((error) => {
        alert(error)
    })
  }

  const rows = [];
  languages.forEach((language) => {
    rows.push(
      <tr key={language.idLanguage}>
        <td>{language.idLanguage}</td>
        <td>{language.languageName}</td>
        <td>{language.etat}</td>
        <td>{language.typeLanguage.typeLanguageName}</td>
        <td>{language.pays.paysName}</td>
        <td>
          <a type="button" onClick={() => navigate("/languages/" + language.idLanguage)}><i className="fas fa-edit mx-3"></i></a>
          <a type="button" onClick={() => deleteLanguage(language.idLanguage)}><i className="fas text-danger fa-trash mx-3"></i></a>
        </td>
      </tr>
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
            <i className="fas fa-kiwi-bird me-4"></i>Lazy coding
          </span>
        </div>
      </nav>
      <div className="container row mt-4 mx-auto" data-bs-theme="dark">
        <div className="col-md-12">
          <h5 className="text-white">Liste des languages</h5>
          <button
            className="btn btn-outline-secondary mt-3 px-4"
            onClick={() => navigate("/languages/new")}
          >
            <i className="fas fa-plus me-3"></i>Nouvelle language
          </button>
          <div className="mt-4">
            <table className="table table-dark table-hover">
              <thead>
                <tr>
						<th>Id Language </th>
						<th>Language Name </th>
						<th>Etat </th>
						<th>Type Language </th>
						<th>Pays </th>
						<th></th>
                </tr>
              </thead>
              <tbody>
                {rows}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </>
  );
}