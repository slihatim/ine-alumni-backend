import React, { useState, useContext } from 'react'
import NavBar from './NavBar';
import Footer from './Footer';
import { Outlet } from 'react-router';
import Alerts from './components/alerts/Alerts';

const AlertContext = React.createContext();

export const useAlert = () => {
    return useContext(AlertContext);
}

const SharedLayout = () => {
    const [alerts, setAlerts] = useState([]);

    const addAlert = (success, description) => {
        const newAlert = {id: Date.now(), success, description};
        setAlerts([...alerts, newAlert]);

        const timeoutId = setTimeout(() => {
            setAlerts((prevAlerts) => prevAlerts.filter(alert => alert.id !== newAlert.id));
        }, 8000);

        return () => clearTimeout(timeoutId);
    }

    return (
        <AlertContext.Provider value={{addAlert}}>
            <main className='mt-16'>
                <NavBar />
                <div className='min-h-[calc(100vh-373px)]'>
                    <Outlet />
                </div>
                <Footer />
                <Alerts alerts={alerts}/>
            </main>
        </AlertContext.Provider>
      )
    }
    
export default SharedLayout;