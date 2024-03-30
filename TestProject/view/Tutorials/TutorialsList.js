
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function TutorialsList() {
  const navigate = useNavigate();
  const [tutorialss, setTutorialss] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPage, setTotalPage] = useState(0);

  document.title = "Tutorialss";
  document.body.style.backgroundColor = "#161616";

  // fetching tutorialss data
  const API_BASE_URL = "http://localhost:8080";

  useEffect(() => {
    axios.get(API_BASE_URL + "/tutorialss?page=" + currentPage + "&size=5", {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}}).then((response) => {
      if (totalPage === 0) {
        setTotalPage(response.data.totalPages);
      }

      setTutorialss(response.data.content);
    }).catch((error) => {
      if (error.response.status === 401) {
        localStorage.removeItem("apktoken");
        navigate('/login');
      }
    });
  }, [currentPage]);

  // delete action
  const deleteTutorials = (id) => {
    axios.delete(API_BASE_URL + "/tutorialss/" + id, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}}).then((response) => {
        const newTutorialssList = tutorialss.filter(tutorials => tutorials.id != id)
        setTutorialss(newTutorialssList);
    }).catch((error) => {
      if (error.response.status === 401) {
        localStorage.removeItem("apktoken");
        navigate('/login');
      }
    })
  }

  const paginationButtons = []
  for (let i = 0; i < totalPage; i++) {
    paginationButtons.push(
      <li class="page-item"><button class="page-link" onClick={() => setCurrentPage(i)}>{i + 1}</button></li>
    )
  }

  const rows = [];
  tutorialss.forEach((tutorials) => {
    rows.push(
      <tr key={tutorials.id}>
        <td>{tutorials.id}</td>
        <td>{tutorials.title}</td>
        <td>{tutorials.description}</td>
        <td>{tutorials.level}</td>
        <td>{tutorials.published}</td>
        <td>
          <a type="button" onClick={() => navigate("/tutorialss/" + tutorials.id)}><i className="fas fa-edit mx-3"></i></a>
          <a type="button" onClick={() => deleteTutorials(tutorials.id)}><i className="fas text-danger fa-trash mx-3"></i></a>
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
            <a href="/home" className="text-decoration-none text-white">
              <i className="fas fa-kiwi-bird me-4"></i>Lazy coding
            </a>
          </span>
        </div>
      </nav>
      
      <div className="container row mt-4 mx-auto" data-bs-theme="dark">
        <div className="col-md-12">
          <h5 className="text-white">Liste des tutorialss</h5>
          <button
            className="btn btn-outline-secondary mt-3 px-4"
            onClick={() => navigate("/tutorialss/new")}
          >
            <i className="fas fa-plus me-3"></i>Nouvelle tutorials
          </button>
          <div className="mt-4">
            <table className="table table-dark table-hover">
              <thead>
                <tr>
						<th>Id </th>
						<th>Title </th>
						<th>Description </th>
						<th>Level </th>
						<th>Published </th>
						<th></th>
                </tr>
              </thead>
              <tbody>
                {rows}
              </tbody>
            </table>

            <nav aria-label="Page navigation example">
              <ul class="pagination">
                <li class="page-item"><button class="page-link" disabled={currentPage === 0} onClick={() => setCurrentPage(currentPage - 1)}>Previous</button></li>
                {paginationButtons}
                <li class="page-item"><button class="page-link" disabled={currentPage === totalPage - 1} onClick={() => setCurrentPage(currentPage + 1)}>Next</button></li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
    </>
  );
}