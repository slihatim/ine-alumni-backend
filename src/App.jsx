import { Routes, Route } from "react-router";
import "./App.css";
import SharedLayout from "./SharedLayout";
import Home from "./components/home/Home";
import Evenements from "./components/evenements/Evenements";
import Emplois from "./components/emplois/Emplois";
import Stages from "./components/stages/Stages";
import Enreprises from "./components/entreprises/Entreprises";
import Laureats from "./components/laureats/Laureats";
import RessourcesLayout from "./components/ressources/RessourcesLayout";
import OutilsPratiques from "./components/ressources/OutilsPratiques";
import RscCertification from "./components/ressources/RscCertification";
import RscTextuelles from "./components/ressources/RscTextuelles";
import RscInteractives from "./components/ressources/RscInteractives";
import Login from "./components/login/Login";
import Signup from "./components/signup/Signup";
import NotFound from "./components/NotFound";

import EventDetails from "./components/eventdetails/EventDetails";

function App() {
  return (
    <Routes>
      <Route path="/" element={<SharedLayout />}>
        <Route index element={<Home />} />
        <Route path="evenements" element={<Evenements />} />
        <Route path="emplois" element={<Emplois />} />
        <Route path="stages" element={<Stages />} />
        <Route path="entreprises" element={<Enreprises />} />
        <Route path="laureats" element={<Laureats />} />
        <Route path="ressources" element={<RessourcesLayout />}>
          <Route path="textuelles" element={<RscTextuelles />} />
          <Route path="interactives" element={<RscInteractives />} />
          <Route path="outils" element={<OutilsPratiques />} />
          <Route path="certification" element={<RscCertification />} />
        </Route>
        <Route path="se-connecter" element={<Login />} />
        <Route path="nouveau-compte" element={<Signup />} />
        <Route path="evenements/:id" element={<EventDetails />} />
        <Route path="*" element={<NotFound />}></Route>
      </Route>
    </Routes>
  );
}

export default App;
