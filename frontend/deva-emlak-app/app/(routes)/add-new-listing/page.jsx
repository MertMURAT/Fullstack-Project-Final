"use client"
import React, { useState } from 'react'
import { useUser } from '@clerk/nextjs'
import GoogleAddressSearch from "@/app/_components/GoogleAddressSearch"
import { Button } from '@/components/ui/button'
import { toast } from 'sonner'
import { Loader } from 'lucide-react'
import { useRouter } from 'next/navigation'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

function AddNewListing({ handleClose }) {
  const [selectedAddress, setSelectedAddress] = useState();
  const [coordinates, setCoordinates] = useState();
  const { user } = useUser();
  const [loader, setLoader] = useState(false);
  const router = useRouter();
  const [adType, setAdType] = useState();

  const initialPostData = async (data) => {
    try {
      let uriExt;
      if (adType == 'Rent') {
        uriExt = 'rental-ads'
      } else if ('Sell') {
        uriExt = 'sale-ads'
      }
      const response = await fetch(`http://localhost:8080/api/v1/${uriExt}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
      }

      const result = await response.json();
      console.log(result);

      setLoader(false);
      console.log("New data added, ", result);
      toast("Yeni ilan oluşturuldu.");
      handleClose();
      router.replace(`/edit-new-listing/${adType}/${result.id}`);

      return result;
    } catch (error) {
      console.error('Error:', error);
      toast('Server Side Error');
      setLoader(false);
      throw error;
    }
  };

  const initData = {
    address: selectedAddress?.value?.structured_formatting?.main_text,
    coordinates: coordinates,
    createdBy: user?.primaryEmailAddress.emailAddress,
  };

  const nextHandler = async () => {
    console.log(selectedAddress, coordinates);
    setLoader(true)
    await initialPostData(initData);
  }

  return (
    <div className="flex justify-center items-center mt-10">
    <div className="w-full max-w-5xl p-10 flex flex-col gap-5 items-center justify-center">
      <h2 className="font-bold text-3xl">Yeni ilan oluştur</h2>
      <div className="w-full p-10 rounded-lg border shadow-md flex flex-col gap-5">
        <div className="flex gap-2 flex-col">
          <h2 className="text-slate-500">Kiralık mı? / Satılık mı?</h2>
          <Select onValueChange={(e) => setAdType(e)} name="advertisementType">
            <SelectTrigger className="w-[180px] font-bold bg-orange-200">
              <SelectValue placeholder="İlan Tipi Seç" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="Sell">Satılık</SelectItem>
              <SelectItem value="Rent">Kiralık</SelectItem>
            </SelectContent>
          </Select>
        </div>
        <div className="flex flex-col gap-2">
          <h2 className="text-gray-500">İlanın yer aldığı adresi giriniz</h2>
          <GoogleAddressSearch
            selectedAddress={(value) => setSelectedAddress(value)}
            setCoordinates={(value) => setCoordinates(value)}
          />
        </div>
        <Button
          disabled={!selectedAddress || !coordinates || loader}
          onClick={nextHandler}
          className="w-full mt-5"
        >
          {loader ? <Loader className="animate-spin" /> : 'Sonraki'}
        </Button>
      </div>
    </div>
  </div>
  )
}

export default AddNewListing