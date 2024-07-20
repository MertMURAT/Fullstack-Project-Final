"use client"
import { AdPackageCards } from '@/components/component/ad-package-cards'
import React, { useState, useEffect } from 'react'

function UserPackages() {

  const [listing, setListing] = useState([]);

    useEffect(() => {
      getAdPackages();
    }, [])

    const getAdPackages = async () => {
        try {

            const response = await fetch("http://localhost:8080/api/v1/ad-packages");
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }

            const result = await response.json();
            console.log('ad packages list', result.data);
            setListing(result.data);

            return result;
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
            throw error;
        }
    };

    
  return (
    <div className='flex flex-col gap-3'>
            <h2 className='font-bold text-2xl'>Ödeme Geçmişi</h2>
            <div className='flex flex-col gap-5'>
                {listing && listing.map((item, index) => (
                    <div>
                        <AdPackageCards
                            quantity={item.quantity}
                            createdDate={item.createdDate}
                            expiryDate={item.expiryDate}
                        />
                    </div>
                ))}
            </div>

        </div >
  )
}

export default UserPackages