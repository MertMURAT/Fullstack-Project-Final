import React from 'react'
import ListingMapView from '@/app/_components/ListingMapView'

function ForRent() {
  return (
    <div className="p-10">
      <ListingMapView type='Rent' activate={1} />
    </div>
  )
}

export default ForRent