import GoogleMapSection from '@/app/_components/GoogleMapSection';
import { Button } from '@/components/ui/button'
import { Bath, CarFront, Drill, LandPlot, MapPin, Share } from 'lucide-react'
import React from 'react'
import AgentDetail from './AgentDetail';

function Details({ listingDetail }) {
    console.log('Detay Liste : ', listingDetail);
    return (
        listingDetail && (
            <div className='my-6 flex gap-2 flex-col'>
                <div className='flex justify-between items-center'>
                    <div>
                        <h2 className='font-bold text-3xl'>$ {listingDetail?.price}</h2>
                        <h2 className='text-gray-500 text-lg flex gap-2'>
                            <MapPin />
                            {listingDetail?.address.split('"')[3]}</h2>
                    </div>
                    <Button className='flex gap-2'><Share />  Share</Button>
                </div>
                <hr></hr>
                <div className='mt-4 flex flex-col gap-3'>
                    <h2 className='font-bold text-2xl'>Key Features</h2>
                    <div className='grid grid-cols-2 md:grid-cols-3 gap-4'>
                        <h2 className='flex gap-2 items-center justify-center bg-orange-200 
                        font-bold text-primary'>
                            <LandPlot />
                            {listingDetail.bedroom} Bed
                        </h2>
                        <h2 className='flex gap-2 items-center justify-center bg-orange-200 
                        font-bold text-primary'>
                            <LandPlot />
                            {listingDetail.bedroom} Bed
                        </h2>
                        <h2 className='flex gap-2 items-center bg-orange-200
                        rounded-lg p-3 font-bold justify-center text-primary'>
                            <Drill />
                            Built In {listingDetail?.builtIn}
                        </h2>
                        <h2 className='flex gap-2 items-center justify-center bg-orange-200
                         font-bold text-primary'>
                            <LandPlot />
                            {listingDetail.bedroom} Bed
                        </h2>
                        <h2 className='flex gap-2 items-center justify-center bg-orange-200
                        rounded-lg p-3 font-bold text-primary'>
                            <Bath />
                            {listingDetail.bathroom} Bath
                        </h2>
                        <h2 className='flex gap-2 items-center justify-center bg-orange-200
                         font-bold text-primary'>
                            <CarFront />
                            {listingDetail.parking} Parking
                        </h2>
                    </div>
                    <div className='mt-4'>
                        <h2 className='font-bold text-xl'>
                            What's Special
                        </h2>
                        <p className='text-gray-600'>{listingDetail?.description}</p>
                    </div>
                </div>
                <div>
                    <h2 className='font-bold text-2xl '>Find On Map</h2>
                    <GoogleMapSection
                        coordinates={listingDetail.coordinates}
                        listing={[listingDetail]} />
                </div>
                <div>
                    <h2 className='font-bold text-2xl'>Contact Agent</h2>
                    <AgentDetail listingDetail={listingDetail} />
                </div>
            </div>
        )
    )
}

export default Details
