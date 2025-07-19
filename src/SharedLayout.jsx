import React from 'react'
import NavBar from './NavBar';
import Footer from './Footer';
import { Outlet } from 'react-router';

const SharedLayout = () => {
    return (
        <main className='mt-16'>
            <NavBar />
            <Outlet/>
            <Footer />
        </main>
      )
    }
    
export default SharedLayout;