import { Routes, Route } from "react-router";
import "./App.css";
import SharedLayout from "./SharedLayout";
import Home from "./components/home/Home";
import Evenements from "./components/evenements/Evenements";
import Jobs from './components/offers/Jobs'
import Enreprises from "./components/entreprises/Entreprises";
import Laureats from "./components/laureats/Laureats";
import RessourcesLayout from "./components/ressources/RessourcesLayout";
import OutilsPratiques from "./components/ressources/OutilsPratiques";
import RscCertification from "./components/ressources/RscCertification";
import RscTextuelles from "./components/ressources/RscTextuelles";
import RscInteractives from "./components/ressources/RscInteractives";
import Login from "./components/authentication/Login";
import Signup from "./components/authentication/Signup";
import NotFound from "./components/NotFound";
import EventDetails from "./components/eventdetails/EventDetails";
import ProtectedRoute from "./components/authentication/ProtectedRoute";
import AccountVerification from "./components/authentication/AccountVerification";
import EmailVerification from "./components/authentication/EmailVerification";
import AuthenticationProvider from "./components/authentication/AuthenticationProvider";
import HideWhenAuthenticated from "./components/authentication/HideWhenAuthenticated";

function App() {
  
  return (
    <AuthenticationProvider>
      <Routes>
        <Route path="/" element={<SharedLayout />}>
        
          {/* fully authenticated routes */}
          <Route element={<ProtectedRoute />} > 
              <Route path="evenements" element={<Evenements />} >
                <Route path=":id" element={<EventDetails />} />
              </Route>
              <Route path="jobs" element={<Jobs />} />
              <Route path="entreprises" element={<Enreprises />} />
              <Route path="laureats" element={<Laureats />} />
              <Route path="ressources" element={<RessourcesLayout />}>
                <Route path="textuelles" element={<RscTextuelles />} />
                <Route path="interactives" element={<RscInteractives />} />
                <Route path="outils" element={<OutilsPratiques />} />
                <Route path="certification" element={<RscCertification />} />
              </Route>
              <Route path="verification-email" element={<EmailVerification/>} />
              <Route path="verification-compte" element={<AccountVerification/>} />
          </Route>

          <Route index element={<Home />} />
          <Route path="*" element={<NotFound />}></Route>

          <Route element={<HideWhenAuthenticated />}>
            <Route path="se-connecter" element={<Login />} />
            <Route path="nouveau-compte" element={<Signup />} />
          </Route>

        </Route>
      </Routes>
    </AuthenticationProvider>
  );
}


export default App;