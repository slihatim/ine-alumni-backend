import React from 'react'
import {
    Carousel,
    CarouselContent,
    CarouselItem,
    CarouselNext,
    CarouselPrevious,
  } from "@/components/ui/carousel"
import {Button} from '@/components/ui/button'
  

const Section1 = () => {
  return (
    <div className='relative w-full h-full'>
        <div className='absolute inset-0 bg-[url("/src/assets/bg.jpg")] bg-cover bg-center opacity-10'></div>

        <div className='relative z-10 mx-[12vw] flex justify-center items-center gap-12 py-20 max-lg:flex-col'>
            <div className='max-w-150'>
                <h1 className='text-4xl font-extrabold text-gray-700'>Bienvenue sur la plateforme des Alumni INPT</h1>
                <p className='mt-4 text-gray-600'>Connectez-vous avec la communauté INPT, accédez aux opportunités professionnelles, et bénéficiez des ressources partagées.</p>
                <Button className='rounded-2xl font-bold cursor-pointer mt-4 shadow-md focus:border-2 focus:border-[#0c5f95] flex bg-[#5691cb] hover:bg-[#0c5f95] text-white'>Rejoignez-nous</Button>
            </div>

            <Carousel className='max-w-120' opts={{
    align: "center",
    loop: true,
  }}>
                <CarouselContent>
                    <CarouselItem><img className='rounded-2xl h-68 max-sm:h-auto' src="/src/assets/inpt1.jpg" alt="inpt1" /></CarouselItem>
                    <CarouselItem><img className='rounded-2xl h-68 max-sm:h-auto' src="/src/assets/inpt2.jpg" alt="" /></CarouselItem>
                    <CarouselItem><img className='rounded-2xl h-68 max-sm:h-auto' src="/src/assets/inpt3.jpg" alt="" /></CarouselItem>
                </CarouselContent>
                <CarouselPrevious />
                <CarouselNext />
            </Carousel>

        </div>
    </div>
  )
}

export default Section1