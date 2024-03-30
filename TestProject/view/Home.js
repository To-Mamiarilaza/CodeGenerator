
function Home() {

    document.title = "404 Not Found";
    document.body.style.backgroundColor = "#161616";

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

            <div className="container mt-5" data-bs-theme="dark">
                <div className="mx-auto col-md-8 px-3 mb-4 d-flex justify-content-center align-items-center">
                    <div>
                        <i class="fas fa-kiwi-bird fs-1 text-white me-5"></i>
                    </div>
                    <div>
                        <h1 className="fs-1 text-white">Bienvenue dans lazy coding</h1>
                    </div>
                </div>
                <hr className="text-white"/>
                <div className="mt-4 mx-5">
                    <p className="text-white">Lazy coding est un outil qui va nous simplifier la vie, il nous suffit de créer les tables dans la base de données, configurer le fichier de configuration pour générer les <code>CRUD</code> dans votre application. Et en un seul commande, tout se créer automatiquement ! <br/> Bon codage ... !</p>
                </div>

                <div className="mt-4 mx-5">
                    <h4 className="text-primary">Listes des entités :</h4>

                    {localStorage.getItem("apktoken") != null ? (
                        <div className="row mt-4">
							<div className="col-md-4 mb-3 text-info"><a href="./fiches" className="text-decoration-none"><i class="far fa-file-alt me-3"></i>Fiche</a></div>
							<div className="col-md-4 mb-3 text-info"><a href="./languages" className="text-decoration-none"><i class="far fa-file-alt me-3"></i>Language</a></div>
							<div className="col-md-4 mb-3 text-info"><a href="./payss" className="text-decoration-none"><i class="far fa-file-alt me-3"></i>Pays</a></div>
							<div className="col-md-4 mb-3 text-info"><a href="./tutorialss" className="text-decoration-none"><i class="far fa-file-alt me-3"></i>Tutorials</a></div>
							<div className="col-md-4 mb-3 text-info"><a href="./typeLanguages" className="text-decoration-none"><i class="far fa-file-alt me-3"></i>TypeLanguage</a></div>
                        </div>
                    ): (
                        <div className="row mt-4">
                           <p className="text-info">Pour voir la liste des entités, <a href="login">connectez-vous</a>  !</p>
                        </div>
                    )}

                </div>
    
            </div>
        </>
    )
}

export default Home;