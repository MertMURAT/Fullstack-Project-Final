"use client"
import React, { useEffect, useState } from 'react'
import Listing from './Listing'
import { supabase } from '@/utils/supabase/client'

function ListingMapView({ type }) {

    const [searchedAddress, setSearchedAddress] = useState();

    const [listing, setListing] = useState([]);

    useEffect(() => {
        getLatestListing();
    }, [])

    const getLatestListing = async () => {
        const { data, error } = await supabase
            .from('listing')
            .select(`*,listingImages(
            url,
            listing_id)`)
            .eq('active', true)
            .eq('type', type)
            .order('id', { ascending: false })

        if (data) {
            setListing(data);
        }
        if (error) {
            toast('Server Side Error')
        }
    }

    const handleSearchClick = async () => {
        console.log("Searched Address : ", searchedAddress);
        const searchTerm = searchedAddress?.value?.structured_formatting?.main_text;
        const { data, error } = await supabase
            .from('listing')
            .select(`*,listingImages(
            url,
            listing_id)`)
            .eq('active', true)
            .eq('type', type)
            .like('address', '%' + searchTerm + '%')
            .order('id', { ascending: false })

        if (data) {
            setListing(data);
            console.log('Search data:' ,data);
        }
        if (error) {
            toast('Server Side Error')
        }
    }

    return (
        <div className='grid grid-cols-1 md:grid-cols-2'>
            <div>
                <Listing listing={listing}
                    handleSearchClick={handleSearchClick}
                    searchedAddress={(v) => setSearchedAddress(v)} />
            </div>
            <div>
                Map
            </div>
        </div>
    )
}

export default ListingMapView
