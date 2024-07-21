"use client"
import { supabase } from '@/utils/supabase/client'
import React, { useEffect, useState } from 'react'
import { toast } from 'sonner';
import Slider from '../_components/Slider';
import Details from '../_components/Details';

function ViewListing({ params }) {

    const [listingDetail, setListingDetail] = useState();

    useEffect(() => {
        getAdvertisementDetail();
    }, []);

    const getAdvertisementDetail = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/advertisements/${params.id}`);
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }

            const result = await response.json();
            console.log('detay sayfası', result.data);
            setListingDetail(result.data);

            return result;
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
            throw error;
        }
    };

    return (
        <div className='px-4 md:px-32 lg:px-56 py-3'>
            <Slider imageList={listingDetail?.images} />
            <Details listingDetail={listingDetail}/>
        </div>
    )
}

export default ViewListing;
