
import logo from "./logo.svg";
import "./App.css";
import { Component } from "react";
import {
  BrowserRouter as Router,
  Navigate,
  Route,
  Routes,
  useLocation,
  useNavigate,
} from "react-router-dom";

import Home from "./pages/Home";
import Error404 from "./pages/Error404";

import FicheList from "./pages/Fiche/FicheList";
import FicheForm from "./pages/Fiche/FicheForm";
import LanguageList from "./pages/Language/LanguageList";
import LanguageForm from "./pages/Language/LanguageForm";
import PaysList from "./pages/Pays/PaysList";
import PaysForm from "./pages/Pays/PaysForm";
import TutorialsList from "./pages/Tutorials/TutorialsList";
import TutorialsForm from "./pages/Tutorials/TutorialsForm";
import TypeLanguageList from "./pages/TypeLanguage/TypeLanguageList";
import TypeLanguageForm from "./pages/TypeLanguage/TypeLanguageForm";
import Login from "./pages/Authentification/Login";
import Signup from "./pages/Authentification/Signup";


function App() {
  return (
    <Router> 
      <Routes>
        <Route path="/" Component={Home} />
        <Route path="/home" Component={Home} />
        <Route path="/*" Component={Error404} />
        <Route path="fiches" exact Component={FicheList} />
        <Route path="fiches/new" exact Component={FicheForm} />
        <Route path="fiches/:id" exact Component={FicheForm} />
        <Route path="languages" exact Component={LanguageList} />
        <Route path="languages/new" exact Component={LanguageForm} />
        <Route path="languages/:id" exact Component={LanguageForm} />
        <Route path="payss" exact Component={PaysList} />
        <Route path="payss/new" exact Component={PaysForm} />
        <Route path="payss/:id" exact Component={PaysForm} />
        <Route path="tutorialss" exact Component={TutorialsList} />
        <Route path="tutorialss/new" exact Component={TutorialsForm} />
        <Route path="tutorialss/:id" exact Component={TutorialsForm} />
        <Route path="typeLanguages" exact Component={TypeLanguageList} />
        <Route path="typeLanguages/new" exact Component={TypeLanguageForm} />
        <Route path="typeLanguages/:id" exact Component={TypeLanguageForm} />
        <Route path="/login" exact Component={Login} />
        <Route path="/signup" exact Component={Signup} />
      </Routes>
    </Router>
  );
}

export default App;