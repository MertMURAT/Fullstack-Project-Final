import { MarkerF, OverlayView } from '@react-google-maps/api'
import React, { useState } from 'react'
import MarkerListingItem from './MarkerListingItem';


function MarkerItem({ item }) {
    const [selectedListing, setSelectedListing] = useState();

    return (
        <div>
            <MarkerF
                onClick={() => setSelectedListing(item)}
                position={item.coordinates}
                icon={{
                    url: '/pin.png',
                    scaledSize: {
                        width: 90,
                        height: 90
                    }
                }}
            >
                {selectedListing && <OverlayView
                    position={selectedListing?.coordinates}
                    mapPaneName={OverlayView.OVERLAY_MOUSE_TARGET}
                >
                    <div>
                        <MarkerListingItem 
                        closeHandler={() => setSelectedListing(null)}
                        item={selectedListing} />
                    </div>
                </OverlayView>}
            </MarkerF>
        </div>
    )
}

export default MarkerItem
