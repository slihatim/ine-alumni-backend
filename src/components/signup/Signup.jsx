import React from 'react'
import { Link } from 'react-router'

const Signup = () => {
  return (
    <section className="bg-gray-50 dark:bg-gray-900">
        <div className="flex flex-col items-center justify-center px-6 py-20 mx-auto">
            <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-xl xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                    <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-700 md:text-2xl dark:text-white">
                        Créer un compte
                    </h1>
                    <form className="space-y-4 md:space-y-6" action="#">
                        <div className='max-sm:space-y-4 sm:flex sm:flex-row sm:gap-6'>
                            <div className='flex-2.5'>
                                <label htmlFor="fullname" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Nom complet <span className='text-red-400'>*</span></label>
                                <input type="text" name="fullname" id="fullname" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder='Nom' required="" />
                            </div>
                            <div className='flex-3'>
                                <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Email <span className='text-red-400'>*</span></label>
                                <input type="email" name="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="name@mail.com" required="" />
                            </div>
                        </div>

                        <div>
                            <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Mot de passe <span className='text-red-400'>*</span></label>
                            <input type="password" name="password" id="password" placeholder="••••••••••••••" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required="" />
                        </div>

                        <div className='max-sm:space-y-4 sm:flex sm:flex-row sm:gap-6'>
                            <div className='flex-auto'>
                                <label htmlFor="major" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Filière <span className='text-red-400'>*</span></label>
                                <select name="major" id="major" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required="" >
                                    <option value="ASEDS">ASEDS</option>
                                    <option value="SMART">SMART</option>
                                    <option value="DATA">DATA</option>
                                    <option value="CLOUD">CLOUD</option>
                                    <option value="SESNUM">SESNUM</option>
                                    <option value="AMOA">AMOA</option>
                                    <option value="CYBER_SECURITY">CYBER SECURITY</option>
                                    <option value="OTHER">Autre</option>
                                </select>
                            </div>
                            <div className='flex-auto'>
                                <label htmlFor="graduationYear" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Promotion <span className='text-red-400'>*</span></label>
                                <input type="number" name="graduationYear" id="graduationYear" defaultValue="2027" min="1961" max="2200" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required="" />
                            </div>
                        </div>

                        <div className='max-sm:space-y-4 sm:flex sm:flex-row sm:gap-6'>
                            <div className='flex-auto'>
                                <label htmlFor="phonenumber" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Numéro du teléphone</label>
                                <input type="text" name="phonenumber" id="phonenumber" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder='0612345678' />
                            </div>
                            <div className='flex-auto'>
                                <label htmlFor="birthdate" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Année de naissance</label>
                                <input type="date" name="birthdate" id="birthdate" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                            </div>
                        </div>

                        <div className='max-sm:space-y-4 sm:flex sm:flex-row sm:gap-6'>
                            <div className='flex-1'>
                                <label htmlFor="gender" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Sexe</label>
                                <select name="gender" id="gender" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" >
                                    <option selected value>--</option>
                                    <option value="MALE">Homme</option>
                                    <option value="FEMALE">Femme</option>
                                </select>
                            </div>
                            <div className='flex-2'>
                                <label htmlFor="linkedinurl" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Lien Linkedin</label>
                                <input type="text" name="linkedinurl" id="linkedinurl" placeholder="https://www.linkedin.com/in/user-id/" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" />
                            </div>
                        </div>

                        <div className='max-sm:space-y-4 sm:flex sm:flex-row sm:gap-6'>
                            <div className='flex-1'>
                                <label htmlFor="country" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Pays</label>
                                <select name="country" id="country" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" >
                                    <option selected value>--</option>
                                    <option value="Maroc">Maroc</option>
                                </select>
                            </div>
                            <div className='flex-1'>
                                <label htmlFor="city" className="block mb-2 text-sm font-medium text-gray-700 dark:text-white">Ville</label>
                                <select name="city" id="city" className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" >
                                    <option selected value>--</option>
                                    <option value="Fes">Fes</option>
                                </select>
                            </div>
                        </div>

                        
                        <button type="submit" className="w-full text-white bg-[#5691cb] mt-3 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-lg text-sm px-5 py-3 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800 hover:bg-[#0c5f95]">S'inscrire</button>
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