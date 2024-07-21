"use client"
import React, { useEffect, useState } from 'react'
import Image from 'next/image'
import { Bath, BedDouble, MapPin, Ruler, Search } from 'lucide-react'
import GoogleAddressSearch from './GoogleAddressSearch'
import { Button } from '@/components/ui/button'
import FilterSection from './FilterSection'
import Link from 'next/link'

function Listing({ listing, handleSearchClick, searchedAddress
  , setArea, setNumberOfRooms, setFloorNumber, setHomeType, setCoordinates
}) {
  const [address, setAddress] = useState();

  return (
    <div>
      <div className='p-3 flex gap-7'>
        <GoogleAddressSearch
          selectedAddress={(v) => {
            searchedAddress(v)
            setAddress(v)
          }}
          setCoordinates={setCoordinates}
        />
        <Button className='flex gap-2'
          onClick={handleSearchClick}>
          <Search className='h-4 w-4' />Search</Button>
      </div>

      <FilterSection
        setArea={setArea}
        setNumberOfRooms={setNumberOfRooms}
        setFloorNumber={setFloorNumber}
        setHomeType={setHomeType}
      />

      {address && <div className='px-3'>
        <h2 className='text-xl'>
          Found <span className='font-bold'>{listing?.length}</span> Result in <span className='text-primary font-bold'> {address?.label}</span></h2>
      </div>}

      <div className='grid grid-cols-1 md:grid-cols-2 gap-10'>
        {listing?.length > 0 ? listing.map((item, index) => (
          <Link href={'/view-listing/' + item.id}>
            <div className='p-3 hover:border hover:border-primary rounded-lg cursor-pointer'>
              <img
                src={item.images[0].imageUrl || "/placeholder.svg"}
                width={800}
                height={150}
                alt='Listing Image'
                className='rounded-lg object-contain h-[170px] w-full'
              />
              <div className='flex mt-2 flex-col gap-2'>
                <h2 className='font-bold text-xl'>${item?.price}</h2>
                <h2 className='flex gap-2 text-sm text-gray-400'>
                  <MapPin className='h-4 w-4' />
                  {item?.address}</h2>
                <div className='flex gap-2 mt-2 justify-stretch'>
                  <h2 className='flex gap-2 text-sm bg-slate-200 
                rounded-md p-2 w-full text-gray-500 justify-center items-center'>
                    <BedDouble />
                    {item?.area}
                  </h2>

                  <h2 className='flex gap-2 text-sm bg-slate-200 
                rounded-md p-2 w-full text-gray-500 justify-center items-center'>
                    <Bath />
                    {item?.numberOfRooms}
                  </h2>

                  <h2 className='flex gap-2 text-sm bg-slate-200 
                rounded-md p-2 w-full text-gray-500 justify-center items-center'>
                    <Ruler />
                    {item?.floorNumber}
                  </h2>
                </div>
              </div>
            </div>
          </Link>
        ))
          : [1, 2, 3, 4, 5, 6, 7, 8].map((item, index) => (
            <div key={index} className='h-[230px] w-full
            bg-slate-200 animate-pulse rounded-lg'>
            </div>
          ))}
      </div>
    </div>
  )
}

export default Listing
