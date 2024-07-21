"use client"
import React, { useEffect, useState } from 'react'
import Listing from './Listing'
import GoogleMapSection from './GoogleMapSection';
import { useUser } from '@clerk/nextjs';
import { toast } from 'sonner';

function ListingMapView({ type }) {
    const [searchedAddress, setSearchedAddress] = useState();

    const [listing, setListing] = useState([]);
    const [imagesList, setImagesList] = useState({});
    const [area, setArea] = useState(0);
    const [numberOfRooms, setNumberOfRooms] = useState(0);
    const [floorNumber, setFloorNumber] = useState(0);
    const [homeType, setHomeType] = useState();
    const [coordinates, setCoordinates] = useState();
    const { user } = useUser();

    useEffect(() => {

        if(type == 'Sell'){
            getRentAndSellAd();
        }else if(type == 'Rent'){
            getRentAndSellAd();
        }else{
            getLatestAdvertisements();
        }
        
    }, [])

    const getRentAndSellAd = async () => {
        try {
            let uriExt;
            if (type == 'Sell') {
                uriExt = 'sale-ads'
            } else if(type == 'Rent'){
                uriExt = 'rental-ads'
            }

            const response = await fetch(`http://localhost:8080/api/v1/${uriExt}`);
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }

            const result = await response.json();
            console.log('Sale-Rent güncel ilan listesi', result.data);
            setListing(result.data);

            return result;
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
            throw error;
        }
    };

    const getLatestAdvertisements = async () => {
        try {

            const response = await fetch("http://localhost:8080/api/v1/advertisements/latest");
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
  
    // const searchAdvertisements = async (type, area, numberOfRooms, floorNumber, searchTerm, homeType) => {
    //     try {

    //         const response = await fetch(`http://localhost:8080/api/v1/advertisements/search?searchTerm=${searchTerm}&numberOfRooms=${numberOfRooms}&floorNumber=${floorNumber}&area=${area}`, {
    //             method: 'GET',
    //             headers: {
    //                 'Content-Type': 'application/json',
    //             },
    //         });

    //         if (!response.ok) {
    //             const errorText = await response.text();
    //             throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
    //         }

    //         const result = await response.json();
    //         console.log('Search result:', result);
    //         setListing(result.data);
    //         result.data.forEach(item => getAttachmentsAndSetImages(item.id));

    //         return result;
    //     } catch (error) {
    //         console.error('Error:', error);
    //         toast('Server Side Error')
    //         throw error;
    //     }
    // };


    const handleSearchClick = async () => {
        console.log("Searched Address : ", searchedAddress);
        try {
            const searchParams = {
                page: 0,
                size: 10,
                address: searchedAddress?.value?.structured_formatting?.main_text || '',
                area: area,
                numberOfRooms: numberOfRooms,
                floorNumber: floorNumber,
                housingType: homeType || ''
            };

            const response = await fetch('http://localhost:8080/api/v1/advertisements/search', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(searchParams),
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }

            const result = await response.json();
            console.log('Search results:', result.data);
            setListing(result.data);
            toast('Arama başarılıs!');
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
        }
    }

    return (
        <div className='grid grid-cols-1 md:grid-cols-2 gap-8'>
            <div>
                <Listing listing={listing}
                    handleSearchClick={handleSearchClick}
                    searchedAddress={(v) => setSearchedAddress(v)}
                    setArea={setArea}
                    setNumberOfRooms={setNumberOfRooms}
                    setFloorNumber={setFloorNumber}
                    setHomeType={setHomeType}
                    setCoordinates={setCoordinates}
                />
            </div>
            <div className='right-10 h-full 
            md:w-[350px] lg:w-[450px] xl:w-[900px] '>
                <GoogleMapSection
                    listing={listing}
                    coordinates={coordinates} />
            </div>
        </div>
    )
}

export default ListingMapView
