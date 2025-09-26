import React, { useEffect, useContext, useState, createContext, Suspense } from 'react'
import { useLocation } from 'react-router';
import { getAuthenticationState } from '../../services/auth-service';

const AuthenticationContext = createContext();

export const useAuth = () => {
  return useContext(AuthenticationContext);
}

const AuthenticationProvider = ({ children }) => {
    const [auth, setAuth] = useState(null);
    const [authIsLoading, setAuthIsLoading] = useState(true);
    const location = useLocation();

    useEffect(() => {
      const syncAuthentication = async () => {
        setAuthIsLoading(true);
        try{
          const auth = await getAuthenticationState();
          setAuth(auth);
        } catch(error){
          setAuth(null);
        } finally {
          setAuthIsLoading(false);
        }
      };

      syncAuthentication();

    }, [location.pathname]);

  return (
      <AuthenticationContext.Provider value={{auth, setAuth, authIsLoading, setAuthIsLoading}}>
        {children}
      </AuthenticationContext.Provider>
  )
}

export default AuthenticationProvider