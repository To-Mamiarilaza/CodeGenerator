
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function FicheList() {
  const navigate = useNavigate();
  const [fiches, setFiches] = useState([]);

  document.title = "Fiches";
  document.body.style.backgroundColor = "#161616";

  // fetching fiches data
  const API_BASE_URL = "#apiUrl#";

  useEffect(() => {
    axios.get(API_BASE_URL + "/fiches").then((response) => {
      setFiches(response.data);
    }).catch((error) => {
        alert(error)
    });
  }, []);

  // delete action
  const deleteFiche = (idFiche) => {
    axios.delete(API_BASE_URL + "/fiches/" + idFiche).then((response) => {
        const newFichesList = fiches.filter(fiche => fiche.idFiche != idFiche)
        setFiches(newFichesList);
    }).catch((error) => {
        alert(error)
    })
  }

  const rows = [];
  fiches.forEach((fiche) => {
    rows.push(
      <tr key={fiche.idFiche}>
        <td>{fiche.idFiche}</td>
        <td>{fiche.name}</td>
        <td>{fiche.firstname}</td>
        <td>{fiche.address}</td>
        <td>{fiche.mail}</td>
        <td>{fiche.etat}</td>
        <td>{fiche.salaire}</td>
        <td>{fiche.admin}</td>
        <td>
          <a type="button" onClick={() => navigate("/fiches/" + fiche.idFiche)}><i className="fas fa-edit mx-3"></i></a>
          <a type="button" onClick={() => deleteFiche(fiche.idFiche)}><i className="fas text-danger fa-trash mx-3"></i></a>
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
          <h5 className="text-white">Liste des fiches</h5>
          <a
            className="btn btn-outline-secondary mt-3 px-4"
            href="#"
            type="button"
            onClick={() => navigate("fiches/new")}
          >
            <i className="fas fa-plus me-3"></i>Nouvelle fiche
          </a>
          <div className="mt-4">
            <table className="table table-dark table-hover">
              <thead>
                <tr>
						<th>Id Fiche </th>
						<th>Name </th>
						<th>Firstname </th>
						<th>Address </th>
						<th>Mail </th>
						<th>Etat </th>
						<th>Salaire </th>
						<th>Admin </th>
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