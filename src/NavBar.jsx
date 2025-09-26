import React from 'react'
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuList,
  NavigationMenuTrigger,
} from "@/components/ui/navigation-menu"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Menu } from "lucide-react"
import { Link } from 'react-router'
import { Button } from "@/components/ui/button"
import { useAuth } from './components/authentication/AuthenticationProvider'
import { logout } from './services/auth-service'


const NavBar = () => {
  const {auth} = useAuth();
  return (
    <nav >
      <div className='flex justify-center items-center h-16 fixed top-0  w-full  border-b border-gray-200 bg-white/50 backdrop-blur-xl z-30 transition-all'>
        <div className='flex justify-between items-center w-full mx-[12.5vw] max-lg:mx-[2vw]'>
          <Link to='/'>
          <img src="/assets/ine_alumni.jpg" alt="logo" className='h-16'/>
          </Link>
          
          <div className='flex'>
          <NavigationMenu className='max-md:hidden'>
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
                          Répertoire des entreprises de marché.
                        </p>
                      </Link>
                    </li>
                  </ul>
                </NavigationMenuContent>
              </NavigationMenuItem>

              <NavigationMenuItem>
                <Link to="/ressources/textuelles" className="text-sm font-medium hover:underline">
                  Ressources
                </Link>
              </NavigationMenuItem>

            </NavigationMenuList>
          </NavigationMenu>

          <HamburgerMenu />

          { !auth && <Link to="/se-connecter">
              <Button className='rounded-2xl font-bold cursor-pointer ml-4 shadow-md focus:border-2 focus:border-[#0c5f95] flex bg-[#5691cb] hover:bg-[#0c5f95] text-white'>Se connecter</Button>
          </Link> }

          { auth && <Button className='rounded-2xl font-bold cursor-pointer ml-4 shadow-md focus:border-2 focus:border-[#0c5f95] flex bg-[#5691cb] hover:bg-[#0c5f95] text-white' onClick={logout}>Se déconnecter</Button>}
          </div>
        </div>
      </div>
    </nav>
  )
}

export default NavBar

function HamburgerMenu() {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger className="md:hidden p-2">
        <Menu className="h-6 w-6" />
      </DropdownMenuTrigger>
      <DropdownMenuContent className="w-48 mt-2">
        <DropdownMenuItem>
          <Link to="/" className="w-full">Accueil</Link>
        </DropdownMenuItem>
        <DropdownMenuItem>
          <Link to="/evenements" className="w-full">Événements</Link>
        </DropdownMenuItem>
        <DropdownMenuItem>
          <Link to="/emplois" className="w-full">Emplois</Link>
        </DropdownMenuItem>
        <DropdownMenuItem>
          <Link to="/stages" className="w-full">Stages</Link>
        </DropdownMenuItem>
        <DropdownMenuItem>
          <Link to="/laureats" className="w-full">Laureats</Link>
        </DropdownMenuItem>
        <DropdownMenuItem>
          <Link to="/entreprises" className="w-full">Entreprises</Link>
        </DropdownMenuItem>
        <DropdownMenuItem>
          <Link to="/ressources" className="w-full">Ressources</Link>
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}