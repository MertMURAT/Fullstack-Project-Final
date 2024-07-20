"use client"
import { OrderHistoryCards } from '@/components/component/order-history-cards'
import React, { useState, useEffect } from 'react'

function OrderHistory() {

    const [listing, setListing] = useState([]);

    useEffect(() => {
        getOrders();
    }, [])

    const getOrders = async () => {
        try {

            const response = await fetch("http://localhost:8080/api/v1/orders");
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
            }

            const result = await response.json();
            console.log('orders list', result.data);
            setListing(result.data);

            return result;
        } catch (error) {
            console.error('Error:', error);
            toast('Server Side Error');
            throw error;
        }
    };


    return (
        <div className='flex flex-col gap-3'>
            <h2 className='font-bold text-2xl'>Ödeme Geçmişi</h2>
            <div className='flex flex-col gap-5'>
                {listing && listing.map((item, index) => (
                    <div>
                        <OrderHistoryCards
                            orderId={item.id}
                            orderAdQuantity={item.quantity}
                            orderPrice={item.totalAmount}
                            orderDate={item.createdDate}
                        />
                    </div>
                ))}
            </div>
        </div >
    )
}

export default OrderHistory