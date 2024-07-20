import GoogleMapSection from '@/app/_components/GoogleMapSection';
import { Button } from '@/components/ui/button'
import { Bath, BedSingle, CarFront, Dog, Flower, LandPlot, MapPin, Share, Wallet, Waves } from 'lucide-react'
import React from 'react'
import AgentDetail from './AgentDetail';

function Details({ listingDetail }) {
    console.log('Detay Liste : ', listingDetail);
    return (
        listingDetail && (
            <div className='my-6 flex gap-2 flex-col'>
                <div className='flex justify-between items-center'>
                    <div className='flex flex-col gap-3'>
                        <h2 className='font-bold text-3xl'>$ {listingDetail?.price}</h2>
                        <h2 className='text-gray-500 text-lg flex gap-2'>
                            <MapPin />
                            {listingDetail?.address}</h2>
                    </div>
                    <Button className='flex gap-2'><Share />  Paylaş</Button>
                </div>
                <hr></hr>
                <div className='mt-4 flex flex-col gap-3'>
                    <h2 className='font-bold text-2xl'>Key Features</h2>
                    {listingDetail?.advertisementType == 'SALE' ?
                        <div className='grid grid-cols-2 md:grid-cols-3 gap-4'>
                            <h2 className='flex gap-5 items-center bg-orange-200
                        rounded-lg p-3 font-bold justify-start text-primary'>
                                <CarFront />
                                {listingDetail?.garage} Garaj
                            </h2>
                            <h2 className='flex gap-5 items-center bg-orange-200
                        rounded-lg p-3 font-bold justify-start text-primary'>
                                <Flower />
                                {listingDetail?.garden} Bahçe
                            </h2>
                            <h2 className='flex gap-5 items-center bg-orange-200
                        rounded-lg p-3 font-bold justify-start text-primary'>
                                <Waves />
                                {listingDetail?.swimmingPool} Yüzme Havuzu
                            </h2>

                        </div> :

                        <div className='grid grid-cols-2 md:grid-cols-3 gap-4'>
                            <h2 className='flex  gap-10 items-center bg-orange-200
                                            rounded-lg p-3 font-bold justify-start text-primary'>
                                <Dog />
                                <div className='flex flex-col'>
                                    <h2>Evcil hayvan dahil mi?</h2>
                                    <h2 className='text-black'>{listingDetail?.allowsPets == true ? 'Evet' : 'Hayır'}</h2>
                                </div>
                            </h2>
                            <h2 className='flex gap-5 items-center bg-orange-200
                                            rounded-lg p-3 font-bold justify-start text-primary'>
                                <BedSingle />
                                <div className='flex flex-col'>
                                    <h2>Eşyalı mı?</h2>
                                    <h2 className='text-black'>{listingDetail?.furnished == true ? 'Evet' : 'Hayır'}</h2>
                                </div>
                            </h2>
                            <h2 className='flex gap-5 items-center bg-orange-200
                                            rounded-lg p-3 font-bold justify-start text-primary'>
                                <Wallet />
                                <div className='flex flex-col'>
                                    <h2>Fatura vs. dahil mi?</h2>
                                    <h2 className='text-black'>{listingDetail?.includesUtilities == true ? 'Evet' : 'Hayır'}</h2>
                                </div>
                            </h2>

                        </div>
                    }

                    <div className='mt-4'>
                        <h2 className='font-bold text-xl'>
                            Açıklama
                        </h2>
                        <p className='text-gray-600'>{listingDetail?.description}</p>
                    </div>
                </div>
                <div>
                    <h2 className='font-bold text-2xl '>Haritada Bul</h2>
                    <GoogleMapSection
                        coordinates={listingDetail.coordinates}
                        listing={[listingDetail]} />
                </div>
                <div>
                    <h2 className='font-bold text-2xl'>Kullanıcı ile iletişime geç</h2>
                    <AgentDetail listingDetail={listingDetail} />
                </div>
            </div>
        )
    )
}

export default Details
