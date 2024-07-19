import React from 'react'
import ListingMapView from '@/app/_components/ListingMapView'

function ForSell() {
  return (
    <div className="p-10">
      <ListingMapView type='Sell' activate={2}/>
    </div>
  )
}

export default ForSell