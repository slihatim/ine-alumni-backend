import React from 'react'
import { Link } from 'react-router'
import { useNavigate } from 'react-router';
import { useContext } from 'react';
import { useAlert } from '../../SharedLayout.jsx';
import { useFormik } from 'formik';
import { signinSchema } from '../../schemas/signinSchema.js';
import { login } from '../../services/auth-service.js'
import { useAuth } from './AuthenticationProvider';

const Login = () => {
    const navigate = useNavigate();
    const {addAlert} = useAlert();
    const {setAuth, setAuthIsLoading} = useAuth();

    const {values, errors, touched, isSubmitting, handleChange, handleSubmit} = useFormik({
            initialValues: {
                email: "",
                password: ""
            },
            validationSchema: signinSchema,
            onSubmit: async (values, actions) => {
                try{    
                    setAuthIsLoading(true);
                    const response = await login(values.email, values.password);

                    if(response.data.isSuccess){
                        actions.resetForm();
                        setAuth(response.data.response);
                        addAlert(true, response.data.message);
                        navigate("/evenements");
                    }else{
                        addAlert(false, "Données invalides.");
                    }
                }catch(error) {
                    actions.resetForm();
                    addAlert(false, "Données invalides.");
                }finally{
                    setAuthIsLoading(false);
                }
            }
        });

  return (
    <section className="bg-gray-50 dark:bg-gray-900">
        <div className="flex flex-col items-center justify-center px-6 py-20 mx-auto">
            <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                    <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-700 md:text-2xl dark:text-white">
                        Se connecter 
                    </h1>
                    <form noValidate onSubmit={handleSubmit} className="space-y-4 md:space-y-6" action="#">
                        <div>
                            <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Email</label>
                            <input value={values.email} onChange={handleChange} type="email" name="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="name@mail.com" required="" />
                            {errors.email && touched.email && <p className='text-red-400 text-sm mt-1'>{errors.email}</p>}
                        </div>
                        <div>
                            <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Mot de passe</label>
                            <input value={values.password} onChange={handleChange} type="password" name="password" id="password" placeholder="••••••••" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required="" />
                            {errors.password && touched.password && <p className='text-red-400 text-sm mt-1'>{errors.password}</p>}
                        </div>
                        <div className="flex items-center justify-between">
                            <div className="flex items-start">
                                {/* <div className="flex items-center h-5">
                                    <input id="remember" aria-describedby="remember" type="checkbox" className="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800" required="" />
                                </div>
                                <div className="ml-3 text-sm">
                                    <label htmlFor="remember" className="text-gray-500 dark:text-gray-300">Rester connecté</label>
                                </div> */}
                            </div>
                            <a href="#" className="text-sm font-medium text-gray-600 hover:underline dark:text-primary-500">Mot de passe oublié ?</a>
                        </div>
                        <button disabled={isSubmitting===true} type="submit" className="w-full text-white bg-[#5691cb] hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800 cursor-pointer hover:bg-[#0c5f95]">Se connecter</button>
                        <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                            Vous n'avez pas encore de compte ? <Link to="/nouveau-compte" className="font-medium text-primary-600 hover:underline dark:text-primary-500">S'inscrire</Link>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </section>
  )
}

export default Login