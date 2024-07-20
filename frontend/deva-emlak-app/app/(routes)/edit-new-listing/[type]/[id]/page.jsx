"use client"
import React, { useEffect, useState, useCallback } from 'react'
import { Label } from "@/components/ui/label"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import { Button } from '@/components/ui/button'
import { Formik } from 'formik'
import { usePathname } from 'next/navigation'
import { supabase } from '@/utils/supabase/client'
import { toast } from 'sonner'
import { useRouter } from 'next/navigation'
import { useUser } from '@clerk/nextjs'
import FileUpload from '../../_components/FileUpload'
import { Loader } from 'lucide-react'
import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogTrigger,
} from "@/components/ui/alert-dialog"


function EditListing({ params }) {

    // const params = usePathname();
    const { user } = useUser();
    const router = useRouter();
    const [listing, setListing] = useState([]);
    const [images, setImages] = useState([]);
    const [loading, setLoading] = useState(false);
    const [adType, setAdType] = useState();

    useEffect(() => {

        setAdType(params.type);
        console.log(params.type);
        console.log(adType);
        console.log('listing : ', listing);
    }, [adType, listing]);


    const verifyUserRecord = async () => {

        const responseData = await getAdData();
        setListing(responseData.data);

        // const { data, error } = await supabase
        //     .from('listing')
        //     .select(`*, listingImages(listing_id, url)`)
        //     .eq('createdBy', user?.primaryEmailAddress.emailAddress)
        //     .eq('id', params.id);

        // if (data) {
        //     console.log("Veri :", data[0]);
        //     setListing(data[0]);
        // }

        // if (data && data.length > 0) {
        //     setListing(data[0]);
        // }
        // else {
        //     router.replace('/');
        // }

        // if(data?.length<=0){
        //     router.replace('/')
        // }
    }

    const getAdData = async () => {
        try {
            let uriExt;
            if (adType == 'Sell') {
                uriExt = 'sale-ads'
            } else {
                uriExt = 'rental-ads'
            }

            const response = await fetch(`http://localhost:8080/api/v1/${uriExt}/${params.id}`);
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }

            const result = await response.json();
            console.log('Getirilen veri', result.data);

            return result;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    };

    const updateDataStatus = {
        id: params?.id,
        status: "ACTIVE"
    }

    const updateAd = async (data) => {
        try {
            console.log("ad type : ", adType);
            let uriExt;
            if (adType == 'Sell') {
                uriExt = 'sale-ads'
            } else {
                uriExt = 'rental-ads'
            }
            console.log('uriExt', uriExt);

            const response = await fetch(`http://localhost:8080/api/v1/${uriExt}/${params.id}`, {
                method: 'PUT',
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
            console.log('result', result);

            setLoading(false);
            console.log("New data added, ", result);
            toast("Listing updated");

            return result;
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
            setLoading(false);
            throw error;
        }
    };

    const updateAdStatus = async (data) => {
        try {
            let uriExt;
            if (adType == 'Sale') {
                uriExt = 'sale-ads'
            } else {
                uriExt = 'rental-ads'
            }

            const response = await fetch(`http://localhost:8080/api/v1/${uriExt}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }



            console.log('status response ', response);
            const result = await response.json();
            console.log('result update status', result);

            setLoading(false);
            console.log("İlan yayınlandı!, ", result);
            toast("İlan yayınlandı!");

            return result;
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
            setLoading(false);
            throw error;
        }
    };

    const uploadImage = async (file) => {
        let formImageData = new FormData();
        formImageData.set('file', file);

        try {
            const response = await fetch(`http://localhost:8080/api/v1/attachments/upload/${params.id}`, {
                method: 'POST',
                body: formImageData,
            });

            if (response.ok) {
                const data = await response.json();

                toast('File uploaded successfully');
                console.log('File upload response:', data);
            } else {
                toast('File upload failed');
                console.error('Error uploading file:', response.statusText);
            }
        } catch (error) {
            toast('Error while uploading images');
            console.error('Error uploading file:', error);
        }
    };

    const onSubmitHandler = async (formValue) => {
        setLoading(true);
        console.log('form verileri : ', formValue);

        await updateAd(formValue);

        for (const image of images) {
            console.log(image);
            await uploadImage(image);
        }
        setLoading(false);

        console.log("test");



        // const { data, error } = await supabase
        //     .from('listing')
        //     .update(formValue)
        //     .eq('id', params.id)
        //     .select()

        // if (data) {
        //     // console.log(data);
        //     toast('Listing updated and published')
        //     setLoading(false);
        // } else if (error) {
        //     toast('Error updating listing');
        // }

        // for (const image of images) {
        //     setLoading(true);
        //     const file = image;
        //     const fileName = Date.now().toString();
        //     const fileExt = fileName.split('.').pop();
        //     const { data, error } = await supabase.storage
        //         .from('listingImages')
        //         .upload(`${fileName}`, file, {
        //             contentType: `image/${fileExt}`,
        //             upsert: false
        //         });

        //     if (error) {

        //         setLoading(false);
        //         toast('Error while uploading images');
        //     }


        //     else {
        //         // console.log('data : ', data);
        //         const imageUrl = process.env.NEXT_PUBLIC_IMAGE_URL + fileName;
        //         console.log(imageUrl);
        //         const { data, error } = await supabase
        //             .from('listingImages')
        //             .insert([
        //                 { url: imageUrl, listing_id: params?.id }
        //             ])
        //             .select();

        //         if (error) {
        //             setLoading(false);
        //         }
        //     }
        //     setLoading(false);
        // }
    }

    const updatePackageCount = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/v1/ad-packages', {
                method: 'PUT'  
            });
  
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }
  
            console.log('package count decrease by one', response);
            return response;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    };
  

    const publishButtonHandler = async () => {
        setLoading(true);

        console.log(updateDataStatus);
        await updateAdStatus(updateDataStatus);
        updatePackageCount();
    }

    useEffect(() => {
        // console.log(params.split('/')[2])
        user & verifyUserRecord();
    }, [user])

    // if (!listing) {
    //     return <div>Veriler Yükleniyor...</div>;
    // }

    return (
        <div className='px-10 md:px-36 my-10'>
            <h2 className='font-bold text-2xl'> İlan detaylarını giriniz</h2>

            <Formik
                initialValues={{
                    // profileImage: user?.imageUrl,
                    // fullName: `${user?.firstName} ${user?.lastName}`,
                    // createdBy: user?.primaryEmailAddress.emailAddress,
                    // userId: user?.userId
                }}
                onSubmit={(values) => {
                    console.log("form verisi:", values);
                    console.log("user ıd ", user?.userId);
                    values.createdBy = user?.primaryEmailAddress.emailAddress;
                    values.fullName = `${user?.firstName} ${user?.lastName}`;
                    values.userId = params.id;
                    values.profileImage = user?.imageUrl;
                    onSubmitHandler(values);
                }}
            >
                {({
                    values,
                    handleChange,
                    handleSubmit
                }) => (
                    <form onSubmit={handleSubmit}>
                        <div className='p-5 border rounded-lg shadow-md grid gap-7 mt-6'>
                            <div className='items-center grid grid-cols-1 md:grid-cols-3 gap-10'>
                                <div className='flex gap-2 flex-col'>
                                    <h2 className='text-slate-500'>Kiralık mı? / Satılık mı?</h2>
                                    <h2 className='text-slate-500'>{adType == 'Rent' ? 'Kiralık' : 'Satılık'}</h2>
                                    {/* <RadioGroup
                                        // onValueChange={(v)=>console.log("",v)}
                                        onValueChange={(v) => values.type = v}
                                        defaultValue={listing ? listing?.type : listing?.type}
                                    >
                                        <div className="flex items-center space-x-2">
                                            <RadioGroupItem value="Rent" id="Rent" />
                                            <Label htmlFor="Rent">Rent</Label>
                                        </div>
                                        <div className="flex items-center space-x-2">
                                            <RadioGroupItem value="Sell" id="Sell" />
                                            <Label htmlFor="Sell">Sell</Label>
                                        </div>
                                    </RadioGroup> */}
                                    {/* <Select
                                        onValueChange={(e) => {
                                            values.advertisementType = e
                                            setAdType(e);
                                        }}
                                        name='advertisementType'
                                        defaultValue={listing?.advertisementType}
                                        onChange={handleChange}>
                                        <SelectTrigger className="w-[180px] font-bold bg-orange-200">
                                            <SelectValue placeholder={listing?.advertisementType ? listing?.advertisementType : "İlan Tipi Seç"} />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="Sell">Satılık</SelectItem>
                                            <SelectItem value="Rent">Kiralık</SelectItem>
                                        </SelectContent>
                                    </Select> */}
                                </div>
                                <div className='flex flex-col gap-2'>
                                    <h2 className='text-slate-500'>Ev Tipi</h2>
                                    <Select
                                        // onValueChange={(e)=> console.log(e)}
                                        onValueChange={(e) => values.housingType = e}
                                        name='housingType'
                                        defaultValue={listing?.housingType}
                                        onChange={handleChange}>
                                        <SelectTrigger className="w-[180px]">
                                            <SelectValue placeholder={listing?.housingType ? listing?.housingType : "Ev Tipini Seç"} />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="APARTMENT">Daire</SelectItem>
                                            <SelectItem value="VILLA">Villa</SelectItem>
                                            <SelectItem value="PRIVATE">Müstakil</SelectItem>
                                            <SelectItem value="BUILDING">Bina</SelectItem>
                                            <SelectItem value="SUMMERY">Yazlık</SelectItem>
                                            <SelectItem value="PREFABRICATED">Prefabrik</SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>
                            </div>
                            <div className='grid grid-cols-1 md:grid-cols-2 gap-10'>
                                <div className='flex gap-2 flex-col'>
                                    <h2 className='text-slate-500'>Başlık</h2>
                                    <Input type="text" name="title" placeholder='Ör: Güvenlikli-Site içi-(2+1)-Kelepir-Ön Cephe'
                                        onChange={handleChange} defaultValue={listing?.title} />
                                </div>
                                <div className='flex gap-2 flex-col'>
                                    <h2 className='text-slate-500'>Kimden</h2>
                                    <Input type="text" placeholder="Ör: Sahibinden, Emlakçıdan" name="assignee"
                                        onChange={handleChange} defaultValue={listing?.assignee} />
                                </div>
                            </div>
                            <div className='grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10'>
                                <div className='flex gap-2 flex-col'>
                                    <h2 className='text-slate-500'>Oda Sayısı</h2>
                                    <Input type="number" name="numberOfRooms" placeholder='Ör: 4'
                                        onChange={handleChange} defaultValue={listing?.numberOfRooms} />
                                </div>

                                <div className='flex gap-2 flex-col'>
                                    <h2 className='text-slate-500'>Kat Numarası</h2>
                                    <Input type="number" name="floorNumber" placeholder="Ör: 8"
                                        onChange={handleChange} defaultValue={listing?.floorNumber} />
                                </div>

                                <div className='flex gap-2 flex-col'>
                                    <h2 className='text-slate-500'>Alan (Metrekare)</h2>
                                    <Input type="number" placeholder="Ör: 150" name="area"
                                        onChange={handleChange} defaultValue={listing?.area} />
                                </div>

                            </div>

                            {adType == 'Sell' &&
                                <div className='grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10'>
                                    <div className='flex gap-2 flex-col'>
                                        <h2 className='text-slate-500'>Garaj</h2>
                                        <Input type="number" placeholder='Ör: 2' name="garage"
                                            onChange={handleChange} defaultValue={listing?.garage} />
                                    </div>

                                    <div className='flex gap-2 flex-col'>
                                        <h2 className='text-slate-500'>Bahçe</h2>
                                        <Input type="number" placeholder="Ör: 2" name="garden"
                                            onChange={handleChange} defaultValue={listing?.garden} />
                                    </div>

                                    <div className='flex gap-2 flex-col'>
                                        <h2 className='text-slate-500'>Yüzme Havuzu</h2>
                                        <Input type="number" placeholder="Ör: 2" name="swimmingPool"
                                            onChange={handleChange} defaultValue={listing?.swimmingPool} />
                                    </div>

                                </div>
                            }

                            {adType == 'Rent' && <div className='w-full grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10'>
                                <div className='flex gap-2 flex-col'>
                                    <h2 className='text-slate-500'>Eşyalı mı?</h2>
                                    <Select
                                        onValueChange={(e) => {
                                            values.isFurnished = e
                                        }}
                                        name='isFurnished'
                                        defaultValue={listing?.isFurnished == true? 'true' : 'false'}
                                        onChange={handleChange}>
                                        <SelectTrigger className="w-full font-mono bg-white">
                                            <SelectValue placeholder={listing?.isFurnished ? listing?.isFurnished : "Lütfen Seçiniz"} />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="true">Evet</SelectItem>
                                            <SelectItem value="false">Hayır</SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>


                                <div className='flex gap-2 flex-col'>
                                    <h2 className='text-slate-500'>Aidat ve diğer masraflar dahil mi?</h2>
                                    <Select
                                        onValueChange={(e) => {
                                            values.includesUtilities = e
                                        }}
                                        name='includesUtilities'
                                        defaultValue={listing?.includesUtilities == true? 'true' : 'false'}
                                        onChange={handleChange}>
                                        <SelectTrigger className="w-full font-mono bg-white">
                                            <SelectValue placeholder={listing?.includesUtilities ? listing?.includesUtilities : "Lütfen Seçiniz"} />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="true">Evet</SelectItem>
                                            <SelectItem value="false">Hayır</SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>

                                <div className='flex gap-2 flex-col'>
                                    <h2 className='text-slate-500'>Evcil hayvanlara izin veriliyor mu?</h2>
                                    <Select
                                        onValueChange={(e) => {
                                            values.allowsPets = e
                                        }}
                                        name='allowsPets'
                                        defaultValue={listing?.allowsPets == true? 'true' : 'false'}
                                        onChange={handleChange}>
                                        <SelectTrigger className="w-full font-mono bg-white">
                                            <SelectValue placeholder={listing?.allowsPets ? listing?.allowsPets : "Lütfen Seçiniz"} />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem value="true">Evet</SelectItem>
                                            <SelectItem value="false">Hayır</SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>
                            </div>
                            }

                            <div className='grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10'>
                                <div className='flex gap-2 flex-col'>
                                    <h2 className='text-slate-500'>Fiyatı (₺)</h2>
                                    <Input type="number" placeholder="Ör: 1 750 000" name="price"
                                        onChange={handleChange} defaultValue={listing?.price} />
                                </div>
                                {adType == 'Rent' &&
                                    <div className='flex gap-2 flex-col'>
                                        <h2 className='text-slate-500'>Kira Bedeli (Aylık) (₺)</h2>
                                        <Input type="number" placeholder="Ör: 20000" name="monthlyRent"
                                            onChange={handleChange} defaultValue={listing?.monthlyRent} />
                                    </div>}
                                {adType == 'Rent' &&
                                    <div className='flex gap-2 flex-col'>
                                        <h2 className='text-slate-500'>Depozito miktarı (₺)</h2>
                                        <Input type="number" placeholder="Ör: 12000" name="depositAmount"
                                            onChange={handleChange} defaultValue={listing?.depositAmount} />
                                    </div>}
                            </div>

                            <div>
                                <div className='grid grid-cols-1 gap-2'>
                                    <h2 className='text-slate-500'>Açıklama</h2>
                                    <Textarea placeholder="" name="description"
                                        onChange={handleChange} defaultValue={listing?.description} />
                                </div>
                            </div>

                            <div className='grid grid-cols-1 gap-2'>
                                <h2 className=' font-lg text-slate-500 my-2'>Fotoğraf Yükle</h2>
                                <FileUpload setImages={(value) => setImages(value)}
                                    // imageList={listing?.listingImages} 
                                    imageList={images} 
                                    />
                            </div>

                            <div className='flex gap-7 justify-end'>
                                <Button disabled={loading} variant="outline" className="text-primary border-primary">
                                    {loading ? <Loader className='animate-spin' /> : 'Kaydet'}
                                </Button>

                                <AlertDialog>
                                    <AlertDialogTrigger asChild>
                                        <Button type="button" disabled={loading} className="">
                                            {loading ? <Loader className='animate-spin' /> : 'Yayınla'}</Button>
                                    </AlertDialogTrigger>
                                    <AlertDialogContent>
                                        <AlertDialogHeader>
                                            <AlertDialogTitle>İlanı yayınlamaya hazır mısın?</AlertDialogTitle>
                                            <AlertDialogDescription>
                                                İlanı Yayınlamak istiyor musun?
                                            </AlertDialogDescription>
                                        </AlertDialogHeader>
                                        <AlertDialogFooter>
                                            <AlertDialogCancel>İptal</AlertDialogCancel>
                                            <AlertDialogAction onClick={() => publishButtonHandler()}>
                                                {loading ? <Loader className='animate-spin' /> : 'Devam Et'}
                                            </AlertDialogAction>
                                        </AlertDialogFooter>
                                    </AlertDialogContent>
                                </AlertDialog>

                            </div>
                        </div>
                    </form>)}
            </Formik>

        </div>
    )
}

export default EditListing;
