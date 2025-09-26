import { useFormik } from 'formik'
import React, { useContext } from 'react'
import { Link, useNavigate } from 'react-router'
import { signupSchema } from '../../schemas/signupSchema.js'
import axios from 'axios'
import { login, register } from '../../services/auth-service.js'
import { useAlert } from '../../SharedLayout.jsx'
import { useAuth } from './AuthenticationProvider.jsx'

const Signup = () => {
    const navigate = useNavigate();
    const {addAlert} = useAlert();
    const {setAuth} = useAuth();
        
    const {values, errors, touched, isSubmitting, handleChange, handleSubmit} = useFormik({
        initialValues: {
            fullName: "",
            email: "",
            password: "",
            confirmPassword: "",
            major: "ASEDS",
            graduationYear: new Date().getFullYear() + 2,
            phoneNumber: "",
            gender: "MALE",
            country: "",
            city: ""
        },
        validationSchema: signupSchema,
        onSubmit: async (values, actions) => {
            try{
                const response = await register(values.fullName, values.email, values.password, 
                values.major, values.graduationYear, values.phoneNumber, 
                values.gender, values.country, values.city);

                if(response.data.isSuccess){
                    const loginResponse = await login(values.email, values.password);
                    actions.resetForm();
                    addAlert(true, response.data.message);
                    if(loginResponse.data.isSuccess){
                        setAuth(loginResponse.data.response);
                        navigate("/verification-email");
                    }
                    
                }else{
                    addAlert(false, response.data.message);
                }
            }catch(error) {
                actions.resetForm();
                addAlert(false, error.response?.data?.message || "Une erreur est survenue. Veuillez réessayer plus tard.");
            }
        }
    });

  return (
    <section className="bg-gray-50 dark:bg-gray-900">
        <div className="flex flex-col items-center justify-center px-6 py-20 mx-auto">
            <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-xl xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                    <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-700 md:text-2xl dark:text-white">
                        Créer un compte
                    </h1>
                    <form noValidate onSubmit={handleSubmit} className="space-y-4 md:space-y-6">
                        <div className='max-sm:space-y-4 sm:flex sm:flex-row sm:gap-6'>
                            <div className='flex-2.5'>
                                <label htmlFor="fullName" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Nom complet <span className='text-red-400'>*</span></label>
                                <input value={values.fullName} onChange={handleChange} type="text" name="fullName" id="fullname" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder='Nom' />
                                {errors.fullName && touched.fullName && <p className='text-red-400 text-sm mt-1'>{errors.fullName}</p>}
                            </div>
                            <div className='flex-3'>
                                <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Email <span className='text-red-400'>*</span></label>
                                <input value={values.email} onChange={handleChange} type="email" name="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="name@mail.com" />
                                {errors.email && touched.email && <p className='text-red-400 text-sm mt-1'>{errors.email}</p>}
                            </div>
                        </div>

                        <div className='max-sm:space-y-4 sm:grid sm:grid-cols-[5fr_6fr] sm:gap-6'>
                            <div>
                                <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Mot de passe <span className='text-red-400'>*</span></label>
                                <input value={values.password} onChange={handleChange} type="password" name="password" id="password" placeholder="••••••••••••••" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                                {errors.password && touched.password && <p className='text-red-400 text-sm mt-1'>{errors.password}</p>}
                            </div>
                            <div>
                                <label htmlFor="confirmPassword" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Confirmer le mot de passe <span className='text-red-400'>*</span></label>
                                <input value={values.confirmPassword} onChange={handleChange} type="password" id="confirmPassword" placeholder="••••••••••••••" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                                {errors.confirmPassword && touched.confirmPassword && <p className='text-red-400 text-sm mt-1'>{errors.confirmPassword}</p>}
                            </div>
                        </div>

                        <div className='max-sm:space-y-4 sm:flex sm:flex-row sm:gap-6'>
                            <div className='flex-auto'>
                                <label htmlFor="major" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Filière <span className='text-red-400'>*</span></label>
                                <select value={values.major} onChange={handleChange} name="major" id="major" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" >
                                    <option value="ASEDS">ASEDS</option>
                                    <option value="SMART">SMART</option>
                                    <option value="DATA">DATA</option>
                                    <option value="CLOUD">CLOUD</option>
                                    <option value="SESNUM">SESNUM</option>
                                    <option value="AMOA">AMOA</option>
                                    <option value="CYBER_SECURITY">CYBER SECURITY</option>
                                    <option value="OTHER">Autre</option>
                                </select>
                                {errors.major && touched.major && <p className='text-red-400 text-sm mt-1'>{errors.major}</p>}
                            </div>
                            <div className='flex-auto'>
                                <label htmlFor="graduationYear" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Promotion <span className='text-red-400'>*</span></label>
                                <input value={values.graduationYear} onChange={handleChange} type="number" name="graduationYear" id="graduationYear" min="1961" max="2200" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                                {errors.graduationYear && touched.graduationYear && <p className='text-red-400 text-sm mt-1'>{errors.graduationYear}</p>}
                            </div>
                        </div>

                        <div className='max-sm:space-y-4 sm:flex sm:flex-row sm:gap-6'>
                            <div className='flex-1'>
                                <label htmlFor="phoneNumber" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Numéro du teléphone</label>
                                <input value={values.phoneNumber} onChange={handleChange} type="text" name="phoneNumber" id="phoneNumber" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder='0612345678' />
                                {errors.phoneNumber && touched.phoneNumber && <p className='text-red-400 text-sm mt-1'>{errors.phoneNumber}</p>}
                            </div>
                            <div className='flex-1'>
                                <label htmlFor="gender" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Sexe <span className='text-red-400'>*</span></label>
                                <select value={values.gender} onChange={handleChange} name="gender" id="gender" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" >
                                    <option value="MALE">Homme</option>
                                    <option value="FEMALE">Femme</option>
                                </select>
                                {errors.gender && touched.gender && <p className='text-red-400 text-sm mt-1'>{errors.gender}</p>}
                            </div>
                        </div>

                        <div className='max-sm:space-y-4 sm:flex sm:flex-row sm:gap-6'>
                            <div className='flex-1'>
                                <label htmlFor="country" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Pays</label>
                                <select value={values.country} onChange={handleChange} name="country" id="country" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" >
                                    <option value="">--</option>
                                    <option value="Maroc">Maroc</option>
                                </select>
                            </div>
                            <div className='flex-1'>
                                <label htmlFor="city" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Ville</label>
                                <select value={values.city} onChange={handleChange} name="city" id="city" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" >
                                    <option value="" >--</option>
                                    <option value="Fes">Fes</option>
                                </select>
                            </div>
                        </div>

                        
                        <button disabled={isSubmitting===true} type="submit" className="w-full text-white bg-[#5691cb] mt-3 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-lg text-sm px-5 py-3 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800 hover:bg-[#0c5f95] cursor-pointer">S'inscrire</button>
                        <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                            Vous avez déjà un compte ? <Link to="/se-connecter" className="font-medium text-primary-600 hover:underline dark:text-primary-500 ">Se connecter</Link>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </section>
  )
}

export default Signup