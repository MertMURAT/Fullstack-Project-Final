"use client"
import React, { useEffect, useState } from 'react'
import { supabase } from '@/utils/supabase/client'
import { Bath, BedDouble, MapPin, Ruler, Trash } from 'lucide-react'
import { useUser } from '@clerk/nextjs'
import Image from 'next/image'
import { Button } from '@/components/ui/button'
import Link from 'next/link'
import { toast } from 'sonner'


function UserListing() {

    const { user } = useUser();
    const [listing, setListing] = useState();

    useEffect(() => {
        user && GetUserListing()
    }, [])


    // console.log('listing : ', listing);

    const GetUserListing = async () => {
        const { data, error } = await supabase
            .from('listing')
            .select('*, listingImages(url, listing_id)')
            .eq('createdBy', user?.primaryEmailAddress.emailAddress)
        // console.log(data);

        if (data) {
            setListing(data);
        }
    }

    const DeleteUserListing = async (id) => {
        console.log('id değeri', id);
        const responseListingImages = await supabase
            .from('listingImages')
            .delete()
            .eq('listing_id', id)

        const responseListing = await supabase
            .from('listing')
            .delete()
            .eq('createdBy', user?.primaryEmailAddress.emailAddress)
            .eq('id', id)


        if (responseListing) {
            console.log('işlem başarılı');
            toast('Advertisement deleted!')
        }


    }

    return (
        <div>
            <h2 className='font-bold text-2xl'>Manage your listing</h2>
            <div className='grid grid-cols-1 md:grid-cols-2 gap-3'>
                {listing && listing.map((item, index) => (
                    <div className='relative p-3 hover:border hover:border-primary rounded-lg cursor-pointer'>
                        <h2 className='bg-primary m-2 rounded-lg text-white absolute top-2 left-2 px-2 text-sm p-1'>{item.active ? 'ACTIVE' : 'IN_REVIEW'}</h2>
                        <Image src={item?.listingImages[0] ?
                            item?.listingImages[0]?.url
                            : '/placeholder.png'}
                            width={800}
                            height={150}
                            className='rounded-lg object-cover h-[170px]'
                        />
                        <div className='flex mt-2 flex-col gap-2'>
                            <h2 className='font-bold text-xl'>${item?.price}</h2>
                            <h2 className='flex gap-2 text-sm text-gray-400'>
                                <MapPin className='h-4 w-4' />
                                {item?.address.split('"')[3]}</h2>
                            <div className='flex gap-2 mt-2 justify-stretch'>
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

                                <h2 className='flex gap-2 text-sm bg-slate-200 
                      rounded-md p-2 w-full text-gray-500 justify-center items-center'>
                                    <Ruler />
                                    {item?.area}
                                </h2>
                            </div>
                            <div className='flex gap-2 justify-between'>
                                <Link href={'/view-listing/' + item.id} className='w-full' >
                                    <Button size='sm' variant='outline' className='w-full'>View</Button>
                                </Link>

                                <Link href={'/edit-new-listing/' + item.id} className='w-full' >
                                    <Button size='sm' className='w-full'>Edit</Button>
                                </Link>

                                <Link href={'/user#/my-listing'} className='w-full' >
                                    <Button onClick={() => DeleteUserListing(item.id)} size='sm' variant="destructive"><Trash /></Button>
                                </Link>

                            </div>
                        </div>
                    </div>
                ))}
            </div>

        </div >
    )
}

export default UserListing