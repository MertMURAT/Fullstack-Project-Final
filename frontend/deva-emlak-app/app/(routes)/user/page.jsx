"use client"
import { UserButton, UserProfile } from '@clerk/nextjs'
import { Building2, HandCoins } from 'lucide-react'
import React from 'react'
import UserListing from './_components/UserListing'
import UserPackages from './_components/UserPackages'
import OrderHistory from './_components/OrderHistory'

function User() {
  return (
    <div className='my-6 md:px-10 lg:px-32 w-full flex justify-center'>
      <h2 className='font-bold text-2xl py-3'></h2>
      <UserProfile routing="hash">
        <UserButton.UserProfilePage
          label='İlanlarım'
          labelIcon={<Building2 className='h-5 w-5' />}
          url="my-listing"
        >
          <UserListing />
        </UserButton.UserProfilePage>

        <UserButton.UserProfilePage
          label='Paketlerim'
          labelIcon={<HandCoins className='h-5 w-5' />}
          url="my-packages"
        >
          <UserPackages />
        </UserButton.UserProfilePage>
        <UserButton.UserProfilePage
          label='Ödeme Geçmişim'
          labelIcon={<HandCoins className='h-5 w-5' />}
          url="my-order-history"
        >
          <OrderHistory />
        </UserButton.UserProfilePage>

      </UserProfile>
    </div>
  )
}

export default User