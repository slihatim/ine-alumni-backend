import React from 'react'
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuIndicator,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger,
  NavigationMenuViewport,
} from "@/components/ui/navigation-menu"
import { Link } from 'react-router'
import { Button } from "@/components/ui/button"


const NavBar = () => {
  return (
    <nav className='mb-16'>
      <div className='flex justify-center items-center h-16 fixed top-0  w-full  border-b border-gray-200 bg-white/50 backdrop-blur-xl z-30 transition-all'>
        <div className='flex justify-between items-center w-full mx-[12.5vw]'>
          <img src="/src/assets/ine_alumni.jpg" alt="logo" className='h-16'/>
          
          <div className='flex'>
          <NavigationMenu>
            <NavigationMenuList className="space-x-4">

              <NavigationMenuItem>
                <Link to="/" className="text-sm font-medium hover:underline">
                  Accueil
                </Link>
              </NavigationMenuItem>

              <NavigationMenuItem>
                <Link to="/evenements" className="text-sm font-medium hover:underline">
                  Événements
                </Link>
              </NavigationMenuItem>

              <NavigationMenuItem>
                <Link to="/emplois" className="text-sm font-medium hover:underline">
                  Emplois
                </Link>
              </NavigationMenuItem>

              <NavigationMenuItem>
                <Link to="/stages" className="text-sm font-medium hover:underline">
                  Stages
                </Link>
              </NavigationMenuItem>

              <NavigationMenuItem>
                <NavigationMenuTrigger className='bg-transparent hover:bg-transparent hover:underline hover:cursor-pointer data-[state=open]:bg-transparent px-0'>Annuaires</NavigationMenuTrigger>
                <NavigationMenuContent>
                  <ul className="grid p-2 w-[280px]">
                    <li className='hover:bg-gray-100 p-4 rounded-sm'>
                      <Link to="/laureats" className="block space-y-1">
                        <div className="text-sm font-medium leading-none">Lauréats</div>
                        <p className="text-sm text-muted-foreground">
                          Liste des anciens étudiants INE.
                        </p>
                      </Link>
                    </li>
                    <li className='hover:bg-gray-100 p-4 rounded-sm'>
                      <Link to="/entreprises" className="block space-y-1">
                        <div className="text-sm font-medium leading-none">Entreprises</div>
                        <p className="text-sm text-muted-foreground">
                          Répertoire des entreprises partenaires.
                        </p>
                      </Link>
                    </li>
                  </ul>
                </NavigationMenuContent>
              </NavigationMenuItem>

              <NavigationMenuItem>
                <Link to="/ressources" className="text-sm font-medium hover:underline">
                  Ressources
                </Link>
              </NavigationMenuItem>

            </NavigationMenuList>
          </NavigationMenu>

          <Button className='rounded-2xl font-bold inline cursor-pointer ml-4 shadow-md bg-[#5691cb] hover:bg-[#0c5f95]'>Se connecter</Button>
          </div>
        </div>
      </div>
    </nav>
  )
}

export default NavBar