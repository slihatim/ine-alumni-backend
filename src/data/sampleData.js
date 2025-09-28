// Sample data for testing components
export const sampleCompanies = [
  {
    id: "1",
    name: "Tech Solutions Inc.",
    logoUrl: null, // Will show initials
    domain: "Technologie",
    laureatesCount: 3,
    location: "Rabat, Maroc",
    website: "www.tech-solutions.com",
    description:
      "Tech Solutions Inc. est une entreprise technologique de premier plan, spécialisée dans les solutions logicielles innovantes pour les entreprises. Grâce à son expertise en technologies de pointe et à son engagement envers l'excellence, elle s'efforce de renforcer ses clients et de favoriser leur succès à l'ère du numérique.",
    presentation:
      "Tech Solutions Inc. est une entreprise technologique de premier plan, spécialisée dans le développement de solutions logicielles innovantes pour les entreprises. Grâce à son expertise en technologies de pointe, elle s'est imposée comme un partenaire de confiance pour optimiser et favoriser leur succès à l'ère du numérique. L'entreprise valorise l'excellence, l'innovation et la collaboration, offrant un environnement stimulant où les talents peuvent s'épanouir et contribuer à des projets transformateurs.",
  },
  {
    id: "2",
    name: "Digital Innovations",
    logoUrl: null,
    domain: "Digital",
    laureatesCount: 5,
    location: "Casablanca, Maroc",
    website: "www.digital-innovations.com",
    description:
      "Une entreprise spécialisée dans la transformation digitale et l'innovation technologique.",
    presentation:
      "Digital Innovations accompagne les entreprises dans leur transformation digitale en proposant des solutions innovantes et sur mesure. Notre équipe d'experts développe des applications web et mobiles de haute qualité.",
  },
  {
    id: "3",
    name: "Data Analytics Pro",
    logoUrl: null,
    domain: "Data Science",
    laureatesCount: 2,
    location: "Rabat, Maroc",
    website: "www.data-analytics-pro.com",
    description:
      "Spécialiste en analyse de données et intelligence artificielle.",
    presentation:
      "Data Analytics Pro est une entreprise leader dans le domaine de l'analyse de données et de l'intelligence artificielle. Nous aidons nos clients à exploiter leurs données pour prendre des décisions éclairées.",
  },
  {
    id: "4",
    name: "Cloud Systems",
    logoUrl: null,
    domain: "Cloud Computing",
    laureatesCount: 4,
    location: "Marrakech, Maroc",
    website: "www.cloud-systems.com",
    description: "Solutions cloud et infrastructure informatique.",
    presentation:
      "Cloud Systems propose des solutions cloud complètes pour moderniser l'infrastructure informatique des entreprises. Notre expertise couvre l'architecture cloud, la migration et la sécurité.",
  },
  {
    id: "5",
    name: "Fintech Solutions",
    logoUrl: null,
    domain: "Finance",
    laureatesCount: 3,
    location: "Casablanca, Maroc",
    website: "www.fintech-solutions.com",
    description: "Technologies financières et solutions de paiement.",
    presentation:
      "Fintech Solutions développe des technologies financières innovantes pour moderniser les services bancaires et les systèmes de paiement. Notre mission est de rendre les services financiers plus accessibles et efficaces.",
  },
  {
    id: "6",
    name: "AI Research Lab",
    logoUrl: null,
    domain: "Intelligence Artificielle",
    laureatesCount: 6,
    location: "Rabat, Maroc",
    website: "www.ai-research-lab.com",
    description: "Recherche et développement en intelligence artificielle.",
    presentation:
      "AI Research Lab est un laboratoire de recherche spécialisé dans le développement de solutions d'intelligence artificielle. Nous travaillons sur des projets de pointe en machine learning et deep learning.",
  },
  {
    id: "7",
    name: "Cybersecurity Corp",
    logoUrl: null,
    domain: "Cybersécurité",
    laureatesCount: 2,
    location: "Casablanca, Maroc",
    website: "www.cybersecurity-corp.com",
    description: "Solutions de cybersécurité et protection des données.",
    presentation:
      "Cybersecurity Corp protège les entreprises contre les menaces cybernétiques grâce à des solutions de sécurité avancées. Notre expertise couvre la détection d'intrusions, la protection des données et la conformité.",
  },
  {
    id: "8",
    name: "Mobile App Studio",
    logoUrl: null,
    domain: "Développement Mobile",
    laureatesCount: 4,
    location: "Agadir, Maroc",
    website: "www.mobile-app-studio.com",
    description: "Développement d'applications mobiles innovantes.",
    presentation:
      "Mobile App Studio crée des applications mobiles innovantes pour iOS et Android. Notre équipe de développeurs expérimentés transforme vos idées en applications performantes et conviviales.",
  },
  {
    id: "9",
    name: "E-commerce Plus",
    logoUrl: null,
    domain: "E-commerce",
    laureatesCount: 3,
    location: "Tanger, Maroc",
    website: "www.ecommerce-plus.com",
    description: "Plateformes e-commerce et solutions de vente en ligne.",
    presentation:
      "E-commerce Plus développe des plateformes e-commerce complètes pour aider les entreprises à réussir dans le commerce en ligne. Nos solutions incluent la gestion des stocks, le paiement sécurisé et l'analytics.",
  },
];

export const companyFilters = [
  {
    key: "domain",
    label: "Domaine",
    type: "select",
    options: [
      { value: "technologie", label: "Technologie" },
      { value: "finance", label: "Finance" },
      { value: "consulting", label: "Consulting" },
    ],
  },
  {
    key: "location",
    label: "Localisation",
    type: "select",
    options: [
      { value: "rabat", label: "Rabat" },
      { value: "casablanca", label: "Casablanca" },
      { value: "marrakech", label: "Marrakech" },
    ],
  },
  {
    key: "hasContact",
    label: "Contient un contact RH",
    type: "checkbox",
  },
  {
    key: "hasDuration",
    label: "Contient des durées",
    type: "checkbox",
  },
];

export const sampleLaureates = [
  {
    id: "1",
    name: "Yassine",
    photoUrl: "/api/placeholder/80/80",
    promotion: "2024",
    title: "Ingénieur Logiciel",
    major: "ASEDS",
    company: "Tech Solutions Inc.",
    location: "Rabat, Maroc",
    linkedinUrl: "https://linkedin.com/in/fatima-zahra",
    email: "fatima.zahra@email.com",
  },
  {
    id: "2",
    name: "Fatima Zahra",
    photoUrl: "/api/placeholder/80/80",
    promotion: "2024",
    title: "Ingénieur Logiciel",
    major: "ASEDS",
    company: "Tech Solutions Inc.",
    location: "Rabat, Maroc",
    linkedinUrl: "https://linkedin.com/in/fatima-zahra",
    email: "fatima.zahra@email.com",
  },
  {
    id: "3",
    name: "Fatima Zahra",
    photoUrl: "/api/placeholder/80/80",
    promotion: "2025",
    title: "Ingénieur Logiciel",
    major: "ASEDS",
    company: "Tech Solutions Inc.",
    location: "Rabat, Maroc",
    linkedinUrl: "https://linkedin.com/in/fatima-zahra",
    email: "fatima.zahra@email.com",
  },
  {
    id: "4",
    name: "Fatima Zahra",
    photoUrl: "/api/placeholder/80/80",
    promotion: "2025",
    title: "Ingénieur Logiciel",
    major: "ASEDS",
    company: "Tech Solutions Inc.",
    location: "Rabat, Maroc",
    linkedinUrl: "https://linkedin.com/in/fatima-zahra",
    email: "fatima.zahra@email.com",
  },
  {
    id: "5",
    name: "Fatima Zahra",
    photoUrl: "/api/placeholder/80/80",
    promotion: "2025",
    title: "Ingénieur Logiciel",
    major: "ASEDS",
    company: "Tech Solutions Inc.",
    location: "Rabat, Maroc",
    linkedinUrl: "https://linkedin.com/in/fatima-zahra",
    email: "fatima.zahra@email.com",
  },
  {
    id: "6",
    name: "Fatima Zahra",
    photoUrl: "/api/placeholder/80/80",
    promotion: "2025",
    title: "Ingénieur Logiciel",
    major: "ASEDS",
    company: "Tech Solutions Inc.",
    location: "Rabat, Maroc",
    linkedinUrl: "https://linkedin.com/in/fatima-zahra",
    email: "fatima.zahra@email.com",
  },
];

export const sampleExperiences = [
  {
    id: "1",
    title: "Ingénieur Logiciel Senior",
    company: "Tech Solutions Inc.",
    startDate: "2022",
    endDate: null,
    current: true,
    description:
      "Développement d'applications web modernes avec React et Node.js. Encadrement d'équipes junior et participation à l'architecture technique.",
  },
  {
    id: "2",
    title: "Stagiaire Développeur",
    company: "StartupTech",
    startDate: "2021",
    endDate: "2022",
    current: false,
    description:
      "Stage de fin d'études focalisé sur le développement mobile avec React Native.",
  },
];

export const sampleSkills = [
  "JavaScript",
  "React",
  "Node.js",
  "Python",
  "Machine Learning",
  "SQL",
  "Git",
  "Docker",
  "AWS",
  "Agile",
];

export const sampleEducation = [
  {
    id: "1",
    degree: "Ingénieur d'État en Informatique",
    institution: "Institut National des Postes et Télécommunications (INPT)",
    startDate: "2018",
    endDate: "2021",
    description:
      "Spécialisation en ASEDS (Architecture des Systèmes Embarqués et Distribués)",
    location: "Rabat, Maroc",
  },
  {
    id: "2",
    degree: "Classes Préparatoires Scientifiques",
    institution: "Lycée Mohammed V",
    startDate: "2016",
    endDate: "2018",
    description: "Mathématiques et Physique (MP)",
    location: "Casablanca, Maroc",
  },
];

export const sampleReviews = [
  {
    id: "1",
    author: "Fatima Zahra",
    timeAgo: "il y a 2 mois",
    rating: 4,
    comment:
      "Excellente culture d'entreprise et projets stimulants. J'ai beaucoup appris et évolué professionnellement.",
    authorAvatar: "/api/placeholder/40/40",
  },
  {
    id: "2",
    author: "Mohammed Alami",
    timeAgo: "il y a 4 mois",
    rating: 5,
    comment:
      "Environnement de travail fantastique avec des collègues très compétents. Les défis techniques sont passionnants.",
    authorAvatar: "/api/placeholder/40/40",
  },
];

export const sampleHRContacts = [
  {
    title: "Email",
    description: "Contactez-nous directement",
    email: "recrutement@techsolutions.com",
  },
  {
    title: "LinkedIn",
    description: "Suivez-nous sur LinkedIn",
    linkedin: "https://linkedin.com/company/techsolutions",
  },
];

export const laureateFilters = [
  {
    key: "promotion",
    label: "Promotion",
    type: "select",
    options: [
      { value: "2024", label: "2024" },
      { value: "2023", label: "2023" },
      { value: "2022", label: "2022" },
    ],
  },
  {
    key: "poste",
    label: "Poste",
    type: "select",
    options: [
      { value: "ingenieur-logiciel", label: "Ingénieur Logiciel" },
      { value: "data-scientist", label: "Data Scientist" },
      { value: "chef-projet", label: "Chef de Projet" },
    ],
  },
  {
    key: "filiere",
    label: "Filière",
    type: "select",
    options: [
      { value: "aseds", label: "ASEDS" },
      { value: "gti", label: "DATA" },
      { value: "rt", label: "ICCN" },
      { value: "e", label: "CLOUD" },
      { value: "re", label: "SESNUM" },
      { value: "s", label: "AMOA" },
      { value: "B", label: "SMART ICT" },
    ],
  },
  {
    key: "entreprise",
    label: "Entreprise",
    type: "select",
    options: [
      { value: "tech-solutions", label: "Tech Solutions Inc." },
      { value: "ai-innovations", label: "AI Innovations" },
    ],
  },
  {
    key: "localisation",
    label: "Localisation",
    type: "select",
    options: [
      { value: "rabat", label: "Rabat" },
      { value: "casablanca", label: "Casablanca" },
      { value: "marrakech", label: "Marrakech" },
    ],
  },
  {
    key: "domaine",
    label: "Domaine",
    type: "select",
    options: [
      { value: "technologie", label: "Technologie" },
      { value: "finance", label: "Finance" },
      { value: "consulting", label: "Consulting" },
    ],
  },
  {
    key: "hasLinkedin",
    label: "A un profil LinkedIn",
    type: "checkbox",
  },
  {
    key: "hasEmail",
    label: "A une adresse email",
    type: "checkbox",
  },
];
