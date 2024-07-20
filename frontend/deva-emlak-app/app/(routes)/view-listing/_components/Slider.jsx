import React from 'react'
import {
    Carousel,
    CarouselContent,
    CarouselItem,
    CarouselNext,
    CarouselPrevious,
} from "@/components/ui/carousel"

function Slider({ imageList }) {
    console.log('Resim liste : ', imageList);
    return (
        <div className='mt-5'>
            {imageList.length > 0 ?
                <Carousel>
                    <CarouselContent>
                        {imageList.map((url, index) => (
                            <CarouselItem key={index}>
                                <img src={url} width={800}
                                    height={300}
                                    alt='image'
                                    className='rounded-xl object-contain h-[360px] w-full'>
                                </img>
                            </CarouselItem>
                        ))}
                    </CarouselContent>
                    <CarouselPrevious />
                    <CarouselNext />
                </Carousel> :
                <div className='w-full h-[200px] bg-slate-200 animate-pulse rounded-lg'>
                </div>}
        </div>
    )
}

export default Slider;
