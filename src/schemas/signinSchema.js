import * as yup from 'yup';

export const signinSchema = yup.object().shape({
    email: yup.string().email("Email invalide").required("L'email est requis"),
    password: yup.string().min(8, "Le mot de passe doit contenir au moins 8 caractères").required("Le mot de passe est requis"),
})