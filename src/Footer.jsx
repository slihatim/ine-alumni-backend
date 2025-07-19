import React from 'react'
import { Link } from 'react-router'

const Footer = () => {
  return (
    <div className='bg-slate-900 px-[12vw] pt-11 pb-6 text-white'>
      <div className='flex gap-8 justify-between flex-wrap gap-y-6'>
        <div>
          <img src="/assets/ine_alumni.jpg" alt="ine alumni logo" className='h-20'/>
          <h1 className='mt-4 text-xl font-bold'>Ine Alumni</h1>
          <p className='max-w-45 mt-2 text-sm'>Votre réseau professionnel commence ici.</p>
        </div>

        <div>
          <h1 className='font-bold'>Liens rapides</h1>
          <div className='flex gap-8'>
            <div>
              <Link to='/'><p className='mt-2 text-sm hover:underline'>Accueil</p></Link>
              <Link to='/evenements'><p className='mt-2 text-sm hover:underline'>Événements</p></Link>
              <Link to='/emplois'><p className='mt-2 text-sm hover:underline'>Emplois</p></Link>
              <Link to='/stages'><p className='mt-2 text-sm hover:underline'>Stages</p></Link>
            </div>
            <div>
              <Link to='/laureats'><p className='mt-2 text-sm hover:underline'>Laureats</p></Link>
              <Link to='/entreprises'><p className='mt-2 text-sm hover:underline'>Entreprises</p></Link>
              <Link to='/ressources'><p className='mt-2 text-sm hover:underline'>Ressources</p></Link>
            </div>
          </div>
        </div>

        <div>
          <h1 className='font-bold'>Contact</h1>
          <a href='tel:212512345678' className='mt-2 block underline'>+212 5 12 34 56 78</a>
          <a href='mailto:info@inealumni.ac.ma' className='mt-0 underline'>info@inealumni.ac.ma</a>
        </div>

      </div>
      <div className='h-[1px] bg-slate-200 w-[76vw] text-center mt-8'></div>
      <p className='mt-4 text-gray-300 text-sm text-center'>	&#169; 2025 INPT ALUMNI. Tous droits réservés.</p>
    </div>
  )
}

export default Footer