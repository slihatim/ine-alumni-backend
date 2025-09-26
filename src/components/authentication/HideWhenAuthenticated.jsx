import React from 'react'
import { useAuth } from './AuthenticationProvider'
import { Navigate, Outlet } from 'react-router';

const HideWhenAuthenticated = () => {
    const {auth, authIsLoading} = useAuth();
  return (
    authIsLoading ? null :
    !auth? <Outlet /> : <Navigate to="/evenements" replace/>
  )
}

export default HideWhenAuthenticated