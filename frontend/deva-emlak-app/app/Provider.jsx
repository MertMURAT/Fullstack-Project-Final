import React from 'react'
import Header from './_components/Header'

function Provider({ children }) {
  return (
    <div>
      <Header />
      <div className='flex justify-center mt-32'>
        {children}
      </div>
    </div>
  )
}

export default Provider
