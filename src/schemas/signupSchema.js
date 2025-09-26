import * as yup from 'yup';

export const signupSchema = yup.object().shape({
    fullName: yup.string().required("Le nom complet est requis"),
    email: yup.string().email("Email invalide").required("L'email est requis"),
    password: yup.string().min(8, "Le mot de passe doit contenir au moins 8 caractères").required("Le mot de passe est requis"),
    confirmPassword: yup.string().oneOf([yup.ref('password'), null], "Les mots de passe ne correspondent pas").required("La confirmation du mot de passe est requise"),
    major: yup.string().required("La filière est requise"),
    graduationYear: yup.number().min(1961, "Année invalide").max(2200, "Année invalide").required("L'année de promotion est requise"),
    phoneNumber: yup.string().min(7, "Numéro de téléphone invalide").max(15, "Numéro de téléphone invalide"),
    gender: yup.string().required("Le sexe est requis"),
    country: yup.string(),
    city: yup.string(),
})