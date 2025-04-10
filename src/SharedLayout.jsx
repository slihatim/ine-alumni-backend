import React from 'react'
import NavBar from './NavBar';
import Footer from './Footer';
import { Outlet } from 'react-router';

const SharedLayout = () => {
    return (
        <main className='my-20 bg-amber-300'>
            <NavBar />
            <Outlet />
            <Footer />
        </main>
      )
    }
    
export default SharedLayout;