"use client"
import { SignOutButton, UserButton, useUser } from '@clerk/nextjs'
import { Plus } from 'lucide-react'
import Image from 'next/image'
import Link from 'next/link'
import { usePathname } from 'next/navigation'
import React, { useEffect, useState } from 'react'
import { Button } from '@/components/ui/button';
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"


function Header() {
    const path = usePathname();
    const { user, isSignedIn } = useUser();
    const [ totalPackageQuantity, setTotalPackageQuantity ] = useState(0);

    useEffect(() => {
        console.log(path);
        getTotalPackageQuantity();
    }, [])

    const getTotalPackageQuantity = async () => {
        try {

            const response = await fetch("http://localhost:8080/api/v1/ad-packages/total-quantity");
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }

            const result = await response.json();
            console.log('total quantity : ', result.data);
            console.log('total quantity result: ', result);
            setTotalPackageQuantity(result.data);

            return result;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    };


    return (
        <div className='p-6 px-10 flex justify-between shadow-sm fixed top-0 w-full z-10 bg-white'>
            <div className='flex gap-12 items-center'>
                <Image src={'/logo.svg'} width={50}
                    height={50} alt='logo' />
                <ul className='hidden md:flex gap-10'>
                    <Link href={'/sell'}>
                        <li className={`'hover:text-primary font-medium text-sm cursor-pointer' 
                            ${path == '/sell' && 'text-primary'}`}>For Sell</li>
                    </Link>
                    <Link href={'/rent'}>
                        <li className={`'hover:text-primary font-medium text-sm cursor-pointer' 
                            ${path == '/rent' && 'text-primary'}`}>For Rent</li>
                    </Link>
                    {/* <li className='hover:text-primary font-medium text-sm cursor-pointer'>Agent Finder</li> */}
                </ul>
            </div>
            <div className='flex gap-5 items-center'>
                <div className='flex'>
                    <Link href={'/pricing'}>
                        <Button className='flex gap-2'><Plus className='h-5 w-5' /></Button>
                    </Link>

                    <Button className='flex gap-2 bg-slate-200 text-black text-lg font-bold'>{totalPackageQuantity}</Button>
                </div>


                <Link href={'/add-new-listing'}>
                    <Button className='flex gap-2'><Plus className='h-5 w-5' /> Post Your Ad</Button>
                </Link>
                {isSignedIn ?
                    // <UserButton />
                    <DropdownMenu>
                        <DropdownMenuTrigger asChild>
                            <Image src={user?.imageUrl}
                                width={40} height={40} alt='user profile'
                                className='rounded-full' />
                        </DropdownMenuTrigger>
                        <DropdownMenuContent>
                            <DropdownMenuLabel>My Account</DropdownMenuLabel>
                            <DropdownMenuSeparator />
                            <DropdownMenuItem>
                                <Link href={'/user'}>Profile</Link>
                            </DropdownMenuItem>
                            <DropdownMenuItem>My Listing</DropdownMenuItem>
                            <DropdownMenuItem><SignOutButton>
                                Logout</SignOutButton>
                            </DropdownMenuItem>
                        </DropdownMenuContent>
                    </DropdownMenu>
                    :
                    <Link href={'/sign-in'}>
                        <Button variant="outline">Login</Button>
                    </Link>
                }
            </div>
        </div>
    )
}

export default Header
