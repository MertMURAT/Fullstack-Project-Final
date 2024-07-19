import React from 'react'
import Image from 'next/image'
import { Bath, BedDouble, MapPin, Ruler, X } from 'lucide-react'
import { Button } from '@/components/ui/button'
import Link from 'next/link'

function MarkerListingItem({ item, closeHandler }) {
    return (
        <div>
            <div className='
            rounded-lg cursor-pointer w-[250px] border border-orange-500'>
                <div className='bg-white'>
                    <X className='text-primary' onClick={() => closeHandler()} />
                </div>

                <Image 
                // src={item?.listingImages[0].url}
                src="/placeholder.svg"
                    width={800}
                    height={150}
                    className='rounded-lg w-full object-cover h-full'
                />
                <div className='flex mt-2 flex-col gap-2 p-2 bg-white'>
                    <h2 className='font-bold text-xl'>${item?.price}</h2>
                    <h2 className='flex gap-2 text-sm text-gray-400'>
                        <MapPin className='h-4 w-4' />
                        {item?.address.split('"')[3]}</h2>
                    {/* <div className='flex gap-2 mt-2 justify-between '>
                        <h2 className='flex gap-2 text-sm bg-slate-200 
                rounded-md p-2 w-full text-gray-500 justify-center items-center'>
                            <BedDouble />
                            {item?.bedroom}
                        </h2>

                        <h2 className='flex gap-2 text-sm bg-slate-200 
                rounded-md p-2 w-full text-gray-500 justify-center items-center'>
                            <Bath />
                            {item?.bathroom}
                        </h2>
                    </div> */}
                    <div className='w-full flex justify-center'>
                        <Link href={"/view-listing/" + item.id} className='w-full'>
                            <Button size='sm' className='w-full' >
                                View Detail
                            </Button>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default MarkerListingItem
