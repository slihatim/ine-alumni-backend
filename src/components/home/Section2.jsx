import React from 'react'
import { Link } from 'react-router'

const Card = ({img, title, description, linkTitle, link}) => {
    return (
        <div className='p-8 shadow-md flex flex-col gap-2 rounded-2xl bg-slate-50/50 max-w-md w-sm items-start'>
            <img src={img} alt="icon" className='h-6'/>
            <h2 className='font-bold'>{title}</h2>
            <p>{description}</p>
            <Link to={link}><p className='underline font-bold text-[#5691cb]'>{linkTitle}</p></Link>
        </div>
    )
}

const Section2 = () => {
  return (
    <div>
        <div className='mt-16 mx-[12vw] flex flex-wrap gap-y-12 gap-12 justify-center'>

            <Card img='/src/assets/icons/education.svg' title='Offres de Stage' description='Accédez aux opportunités de stages.' linkTitle='Voir les offres' link='/stages'/>

            <Card img='/src/assets/icons/education2.svg' title="Offres d'Emploi" description="Découvrez les opportunités d'emploi pour les jeunes diplômés" linkTitle='Explorer les offres' link='/emplois'/>

            <Card img='/src/assets/icons/education3.svg' title='Événements' description="Calendrier des activités et événements de l'INPT." linkTitle='Voir le calendrier' link='/evenements'/>

        </div>

        <h1 className='text-3xl font-extrabold text-gray-700 mt-14 text-center'>Ressources et Annuaires</h1>

        <div className='mt-10 mx-[12vw] flex flex-wrap gap-y-12 gap-12 justify-center'>

            <Card img='/src/assets/icons/education4.svg' title='Bibliothèque en Ligne' description='Accédez à notre collection de ressources pédagogiques.' linkTitle='Voir les ressources' link='/ressources'/>

            <Card img='/src/assets/icons/education.svg' title="Annuaire des Lauréats" description="Retrouvez et connectez-vous avec les anciens de l'INPT." linkTitle='Explorer' link='/laureats'/>

            <Card img='/src/assets/icons/education2.svg' title='Annuaire des Entreprises' description="Consultez le répertoire d’entreprises de marché." linkTitle='Consulter' link='/entreprises'/>

        </div>
    </div>
  )
}

export default Section2