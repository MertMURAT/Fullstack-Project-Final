"use client"
import { supabase } from '@/utils/supabase/client'
import React, { useEffect, useState } from 'react'
import { toast } from 'sonner';
import Slider from '../_components/Slider';
import Details from '../_components/Details';

function ViewListing({ params }) {

    const [listingDetail, setListingDetail] = useState();
    const [listingDetailImages, setListingDetailImages] = useState([]);

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

            getAttachmentsAndSetImages(params.id);

            return result;
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
            throw error;
        }
    };

    const getAttachmentsAndSetImages = async (advertisementId) => {
        try {
            const attachments = await getAttachmentsByAdvertisementId(advertisementId);
            const imageUrls = await Promise.all(attachments.map(async (attachment) => {
                const downloadUrl = await downloadFile(attachment.id);
                return downloadUrl;
            }));
            setListingDetailImages(imageUrls);
        } catch (error) {
            console.error('Error:', error);
            toast('Error while fetching images');
        }
    };

    const getAttachmentsByAdvertisementId = async (advertisementId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/attachments/advertisement/${advertisementId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }
            const result = await response.json();
            console.log('getAttachmentsByAdvertisementId', result);
            return result.data;
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
            throw error;
        }
    };

    const downloadFile = async (fileId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/attachments/download/${fileId}`);
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }
            const blob = await response.blob();
            const url = URL.createObjectURL(blob);
            return url;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    };

    return (
        <div className='px-4 md:px-32 lg:px-56 py-3'>
            <Slider imageList={listingDetailImages} />
            <Details listingDetail={listingDetail}/>
        </div>
    )
}

export default ViewListing;
