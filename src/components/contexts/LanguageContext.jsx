import { createContext, useContext, useState } from 'react';

// Translation keys
const translations = {
  en: {
    // Navigation
    'nav.jobs': 'Jobs',
    'nav.submit': 'Submit',
    
    // Common
    'common.new': 'New',
    'common.remote': 'Remote',
    'common.apply': 'Apply',
    'common.cancel': 'Cancel',
    'common.submit': 'Submit',
    'common.reset': 'Reset',
    'common.loading': 'Loading...',
    
    // Job types
    'type.stage': 'Internship',
    'type.alternance': 'Apprenticeship',
    'type.emploi': 'Job',
    'type.autre': 'Other',
    
    // Jobs page
    'jobs.title': 'Job Opportunities',
    
    // Submit page
    'submit.title': 'Submit a Job Offer',
    'submit.job_title': 'Job Title',
    'submit.company_name': 'Company Name',
    'submit.location': 'Location',
    'submit.type': 'Job Type',
    'submit.custom_type': 'Custom Type',
    'submit.custom_type.placeholder': 'e.g., Mission, Freelance, Project',
    'submit.duration': 'Duration',
    'submit.description': 'Description',
    'submit.link': 'External Link',
    'submit.success': 'Job offer submitted successfully!',
    
    // Job details
    'job.description': 'Job Description',
    'job.apply.title': 'Apply for this position',
    'job.apply.name': 'Full Name',
    'job.apply.email': 'Email',
    'job.apply.phone': 'Phone',
    'job.apply.cv': 'CV/Resume',
    'job.apply.message': 'Cover Letter',
    'job.apply.success': 'Application submitted successfully!',
    
    // Validation
    'validation.required': 'Please fill in all required fields',
    'validation.custom_type': 'Please specify the custom job type',
  },
  fr: {
    // Navigation
    'nav.jobs': 'Offres',
    'nav.submit': 'Soumettre',
    
    // Common
    'common.new': 'Nouveau',
    'common.remote': 'Télétravail',
    'common.apply': 'Postuler',
    'common.cancel': 'Annuler',
    'common.submit': 'Soumettre',
    'common.reset': 'Réinitialiser',
    'common.loading': 'Chargement...',
    
    // Job types
    'type.stage': 'Stage',
    'type.alternance': 'Alternance',
    'type.emploi': 'Emploi',
    'type.autre': 'Autre',
    
    // Jobs page
    'jobs.title': 'Offres d\'emploi',
    
    // Submit page
    'submit.title': 'Soumettre une offre',
    'submit.job_title': 'Titre du poste',
    'submit.company_name': 'Nom de l\'entreprise',
    'submit.location': 'Localisation',
    'submit.type': 'Type d\'offre',
    'submit.custom_type': 'Type personnalisé',
    'submit.custom_type.placeholder': 'ex: Mission, Freelance, Projet',
    'submit.duration': 'Durée',
    'submit.description': 'Description',
    'submit.link': 'Lien externe',
    'submit.success': 'Offre soumise avec succès !',
    
    // Job details
    'job.description': 'Description du poste',
    'job.apply.title': 'Postuler à ce poste',
    'job.apply.name': 'Nom complet',
    'job.apply.email': 'Email',
    'job.apply.phone': 'Téléphone',
    'job.apply.cv': 'CV/Resume',
    'job.apply.message': 'Lettre de motivation',
    'job.apply.success': 'Candidature envoyée avec succès !',
    
    // Validation
    'validation.required': 'Veuillez remplir tous les champs obligatoires',
    'validation.custom_type': 'Veuillez spécifier le type d\'offre personnalisé',
  }
};

const LanguageContext = createContext(undefined);

export const LanguageProvider = ({ children }) => {
  const [language, setLanguage] = useState('fr');

  // Translation function
  const t = (key) => {
    return translations[language]?.[key] || key;
  };

  return (
    <LanguageContext.Provider value={{ language, setLanguage, t }}>
      {children}
    </LanguageContext.Provider>
  );
};

export const useLanguage = () => {
  const context = useContext(LanguageContext);
  if (context === undefined) {
    throw new Error('useLanguage must be used within a LanguageProvider');
  }
  return context;
}; 