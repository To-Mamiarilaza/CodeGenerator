
function Error404() {

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
                        <i class="far fa-grin-tongue-squint fs-1 text-white me-5"></i>
                    </div>
                    <div>
                        <h1 className="fs-1 text-white">Page Not Found !</h1>
                    </div>
                </div>
                <hr className="text-white"/>
            </div>

        </>
    )
}

export default Error404;