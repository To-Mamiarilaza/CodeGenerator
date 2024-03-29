
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function TutorialsForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [error, setError] = useState(null);
  
  
  const [id, setId] = useState();
  const [title, setTitle] = useState();
  const [description, setDescription] = useState();
  const [level, setLevel] = useState();
  const [published, setPublished] = useState();

  document.title = "Nouvelle Tutorials";
  document.body.style.backgroundColor = "#161616";

  // tutorialss data api url
  const API_BASE_URL = "http://localhost:8080";

  // fetching all foreign key and tutorials if edit
  useEffect(() => {
    

    if (id !== undefined) {
      axios
        .get(API_BASE_URL + "/tutorialss/" + id, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          setId(response.data.id);
          setTitle(response.data.title);
          setDescription(response.data.description);
          setLevel(response.data.level);
          setPublished(response.data.published);
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
  

  // save the tutorials
  const saveTutorials = (e) => {
    e.preventDefault();

    const newTutorials = {
      "id": id,
      "title": title,
      "description": description,
      "level": level,
      "published": published
    };

    if (id === undefined) {
      axios
      .post(API_BASE_URL + "/tutorialss", newTutorials, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          navigate("/tutorialss");
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
        .put(API_BASE_URL + "/tutorialss/" + id, newTutorials, {headers: {"Authorization": "Bearer " + localStorage.getItem("apktoken")}})
        .then((response) => {
          navigate("/tutorialss");
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

      <nav class="navbar navbar-dark bg-dark">
        <div class="container-fluid">
          <span class="navbar-brand mb-0 h1 ms-5">
            <i class="fas fa-kiwi-bird me-4"></i>Lazy coding
          </span>
        </div>
      </nav>

      <div class="container mt-4" data-bs-theme="dark">
        <div class="mx-auto col-md-8 px-3 mb-4">
          <h4 class="text-white">Nouvelle tutorials</h4>
          <div class="form">
            <form>
              <div class="mb-3">
                <label for="title" class="form-label text-white">Title</label>
                <input id="title" value={title} onChange={(e) => setTitle(e.target.value)} type="text" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="description" class="form-label text-white">Description</label>
                <input id="description" value={description} onChange={(e) => setDescription(e.target.value)} type="text" class="form-control"/>
              </div>
              <div class="mb-3">
                <label for="level" class="form-label text-white">Level</label>
                <input id="level" value={level} onChange={(e) => setLevel(e.target.value)} type="number" class="form-control"/>
              </div>
              <div class="mb-3">
                <input class="form-check-input" type="checkbox" id="published" value={published} onChange={(e) => setPublished(e.target.value)} />
                <label class="form-check-label text-white" for="published">Published</label>
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
                  onClick={(e) => saveTutorials(e)}
                >
                  Enregistrer
                </button>
                <button
                  class="btn btn-outline-light px-5"
                  onClick={() => navigate("/tutorialss")}
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