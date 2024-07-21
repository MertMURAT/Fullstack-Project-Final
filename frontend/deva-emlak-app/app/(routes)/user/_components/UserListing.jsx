"use client"
import React, { useEffect, useState } from 'react'
import { Bath, BedDouble, MapPin, Ruler, Trash } from 'lucide-react'
import { useUser } from '@clerk/nextjs'
import Image from 'next/image'
import { Button } from '@/components/ui/button'
import Link from 'next/link'
import { toast } from 'sonner'
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"


function UserListing() {

    const { user } = useUser();
    const [listing, setListing] = useState([]);
    const [filteredListing, setFilteredListing] = useState([]);
    const [selectedStatus, setSelectedStatus] = useState('ALL');

    useEffect(() => {
        user && getLatestAdvertisements()
    }, [])

    useEffect(() => {
        if (selectedStatus === 'ALL') {
            setFilteredListing(listing);
        } else {
            const filtered = listing.filter(item => item.advertisementStatus === selectedStatus);
            setFilteredListing(filtered);
        }
    }, [selectedStatus, listing]);

    const getLatestAdvertisements = async () => {
        try {

            const response = await fetch("http://localhost:8080/api/v1/advertisements");
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }

            const result = await response.json();
            console.log('Ana sayfa güncel ilan listesi', result.data);
            setListing(result.data);

            return result;
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
            throw error;
        }
    };

    const updateAdStatus = async (advertisementType, data) => {
        try {
            let uriExt;
            if (advertisementType == 'SALE') {
                uriExt = 'sale-ads'
            } else {
                uriExt = 'rental-ads'
            }

            const response = await fetch(`http://localhost:8080/api/v1/${uriExt}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }

            console.log('status response ', response);
            const result = await response.json();
            console.log('result update status', result);

            return result;
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
            throw error;
        }
    };

    const updatePackageCount = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/v1/ad-packages', {
                method: 'PUT'
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }

            console.log('package count decrease by one', response);
            return response;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    };

    const publishButtonHandler = async (id, advertisementType, adStatus) => {
        const updateDataStatus = {
            id: id,
            status: adStatus
        };
        await updateAdStatus(advertisementType, updateDataStatus);

        if (adStatus === 'ACTIVE') {
            await updatePackageCount();
            toast("İlan yayınlandı.");
        } else {
            toast("İlan pasif duruma getirildi.");
        }
    };

    return (
        <div>
            <h2 className='font-bold text-2xl'>İlan listeni Yönet</h2>
            <div className='my-4'>
                <Select onValueChange={(value) => setSelectedStatus(value)}>
                    <SelectTrigger className="w-[180px]">
                        <SelectValue placeholder="Durum Seç" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectItem value="ALL">Tümü</SelectItem>
                        <SelectItem value="ACTIVE">Aktif</SelectItem>
                        <SelectItem value="PASSIVE">Pasif</SelectItem>
                        <SelectItem value="IN_REVIEW">İncelemede</SelectItem>
                    </SelectContent>
                </Select>
            </div>
            <div className='grid grid-cols-1 md:grid-cols-2 gap-3'>
                {filteredListing.map((item, index) => (
                    <div key={index} className='relative p-3 hover:border hover:border-primary rounded-lg cursor-pointer'>
                        <h2 className={`m-2 rounded-lg text-white absolute top-2 left-2 px-2 text-sm p-1 
                            ${item.advertisementStatus === 'ACTIVE' ? 'bg-green-500' : 'bg-red-500'}`}>
                            {item.advertisementStatus}
                        </h2>
                        <Image src={item?.images[0]?.imageUrl ?
                            item?.images[0].imageUrl
                            : '/placeholder.svg'}
                            width={800}
                            height={150}
                            alt='advertisement-image'
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

                                <Link href={`/edit-new-listing/${item.advertisementType === 'SALE' ? 'Sell' : 'Rent'}/${item.id}`}
                                    className='w-full' >
                                    <Button size='sm' className='w-full'>Edit</Button>
                                </Link>

                                <Link href={'/user#/my-listing'} className='w-full'>
                                    <Button
                                        onClick={() => {
                                            item.advertisementStatus === 'PASSIVE' ?
                                                publishButtonHandler(item.id, item.advertisementType, 'ACTIVE') :
                                                publishButtonHandler(item.id, item.advertisementType, 'PASSIVE')
                                        }}
                                        size='sm'
                                        variant="outline"
                                        className={`w-full ${item.advertisementStatus === 'PASSIVE' ? 'bg-green-500 hover:bg-green-700 text-white' : 'bg-red-500 hover:bg-red-700 text-white'}`}
                                    >
                                        {item.advertisementStatus === 'PASSIVE' ? 'AKTİF' : 'PASİF'}
                                    </Button>
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
