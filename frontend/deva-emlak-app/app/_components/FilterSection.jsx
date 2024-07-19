import React from 'react'
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
import { BedDouble, Bath, CarFront, House, Square, DoorOpen, Building2 } from 'lucide-react'


function FilterSection({ setArea, setNumberOfRooms, setFloorNumber, setHomeType }) {
    return (
        <div className='px-3 py-2 grid grid-cols-2 md:flex gap-2'>
            <Select onValueChange={setArea}>
                <SelectTrigger className="w-[180px]">
                    <SelectValue placeholder="Alan(m2)" />
                </SelectTrigger>
                <SelectContent>
                    <SelectItem value="100">
                        <h2 className='flex gap-2'>
                            <Square className='h-5 w-5 text-primary' />100 m2</h2></SelectItem>
                    <SelectItem value="150">
                        <h2 className='flex gap-2'>
                            <Square className='h-5 w-5 text-primary' />150 m2</h2></SelectItem>
                    <SelectItem value="180">
                        <h2 className='flex gap-2'>
                            <Square className='h-5 w-5 text-primary' />180 m2</h2></SelectItem>
                    <SelectItem value="200">
                        <h2 className='flex gap-2'>
                            <Square className='h-5 w-5 text-primary' />200 m2</h2></SelectItem>
                </SelectContent>
            </Select >

            <Select onValueChange={setNumberOfRooms}>
                <SelectTrigger className="w-[180px]">
                    <SelectValue placeholder="Oda Sayısı" />
                </SelectTrigger>
                <SelectContent>
                    <SelectItem value="2">
                        <h2 className='flex gap-2'>
                            <DoorOpen className='h-5 w-5 text-primary' />2+</h2></SelectItem>
                    <SelectItem value="3">
                        <h2 className='flex gap-2'>
                            <DoorOpen className='h-5 w-5 text-primary' />3+</h2></SelectItem>
                    <SelectItem value="4">
                        <h2 className='flex gap-2'>
                            <DoorOpen className='h-5 w-5 text-primary' />4+</h2></SelectItem>
                    <SelectItem value="5">
                        <h2 className='flex gap-2'>
                            <DoorOpen className='h-5 w-5 text-primary' />5+</h2></SelectItem>
                </SelectContent>
            </Select >

            <Select onValueChange={setFloorNumber}>
                <SelectTrigger className="w-[180px]">
                    <SelectValue placeholder="Kat No" />
                </SelectTrigger>
                <SelectContent>
                    <SelectItem value="2">
                        <h2 className='flex gap-2'>
                            <Building2 className='h-5 w-5 text-primary' />2+</h2></SelectItem>
                    <SelectItem value="5">
                        <h2 className='flex gap-2'>
                            <Building2 className='h-5 w-5 text-primary' />5+</h2></SelectItem>
                    <SelectItem value="10">
                        <h2 className='flex gap-2'>
                            <Building2 className='h-5 w-5 text-primary' />10+</h2></SelectItem>
                    <SelectItem value="20">
                        <h2 className='flex gap-2'>
                            <Building2 className='h-5 w-5 text-primary' />20+</h2></SelectItem>
                </SelectContent>
            </Select >

            <Select onValueChange={(value) => value == 'All' ? setHomeType(null) : setHomeType(value)}>
                <SelectTrigger className="w-[180px]">
                    <SelectValue placeholder="Home Type" />
                </SelectTrigger>
                <SelectContent>
                    <SelectItem value="All">
                        All
                    </SelectItem>
                    <SelectItem value="APARTMENT">
                        Daire
                    </SelectItem>
                    <SelectItem value="VILLA">
                        Villa
                    </SelectItem>
                    <SelectItem value="PRIVATE">
                        Müstaki
                    </SelectItem>
                    <SelectItem value="BUILDING">
                        Bina
                    </SelectItem>
                    <SelectItem value="SUMMERY">
                        Yazlık
                    </SelectItem>
                    <SelectItem value="PREFABRICATED">
                        Prefabrik
                    </SelectItem>
                </SelectContent>
            </Select >
        </div >
    )
}

export default FilterSection
