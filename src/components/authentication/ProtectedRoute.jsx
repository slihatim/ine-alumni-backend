import React from 'react'
import { Navigate, Outlet, useLocation } from 'react-router'
import { useAuth } from './AuthenticationProvider'

// A wrapper for authenticated routes
const ProtectedRoute = () => {
  const { auth, authIsLoading } = useAuth();
  const location = useLocation();

  if(authIsLoading){
    return null;
  }

  if(!auth){
    return <Navigate to="/se-connecter" replace/>
  }

  // If email is not verified while authenticated, only allow /verification-email
  if(!auth.isEmailVerified){
    if(location.pathname !== "/verification-email"){
      return <Navigate to="/verification-email" replace/>
    }
    return <Outlet />;
  }

  // If account is not verified while authenticated, only allow /verification-compte
  if(!auth.isAccountVerified){
    if(location.pathname !== "/verification-compte"){
      return <Navigate to="/verification-compte" replace/>
    }
    return <Outlet />;
  }

  // If both are verified, restrict access to verification pages
  if(location.pathname === "/verification-email" || location.pathname === "/verification-compte"){
    return <Navigate to="/evenements" replace/>
  }

  // Otherwise, allow access
  return <Outlet />;
}

export default ProtectedRoute