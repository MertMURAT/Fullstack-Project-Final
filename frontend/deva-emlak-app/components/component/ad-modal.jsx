"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Label } from "@/components/ui/label"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import AddNewListing from "@/app/(routes)/add-new-listing/page"

export function AdModal() {
  const [isModalOpen, setIsModalOpen] = useState(false)

  const handleSubmit = (e) => {
    e.preventDefault()
    setIsModalOpen(false)
  }

  const handleClose = () => {
    setIsModalOpen(false)
  }

  return (
    <div>
      <Button onClick={() => setIsModalOpen(true)}>İlan Oluştur</Button>
      {isModalOpen && (
        <div className="fixed  inset-0 z-50 flex justify-center items-center bg-black/50">
          <div className="bg-white rounded-lg shadow-lg p-8 w-full max-w-3xl relative modal-container">
            <div className="absolute top-2 right-2">
              <Button variant="ghost" onClick={handleClose}>
                <XIcon className="w-4 h-4" />
              </Button>
            </div>
            <form onSubmit={handleSubmit} className="modal-content">
              <div className="flex justify-center w-full h-full">
                <AddNewListing handleClose={handleClose} />
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

function XIcon(props) {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round">
      <path d="M18 6 6 18" />
      <path d="m6 6 12 12" />
    </svg>
  );
}
