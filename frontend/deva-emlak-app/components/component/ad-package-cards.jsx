import { Card, CardHeader, CardContent, CardFooter } from "@/components/ui/card"

export function AdPackageCards({ quantity, createdDate, expiryDate }) {

  const formattedCreatedDate = new Date(createdDate).toLocaleDateString('tr-TR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  });

  const formattedExpiryDate = new Date(expiryDate).toLocaleDateString('tr-TR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  });

  return (
    (<Card
      className="bg-gradient-to-b from-[#8B5CF6] to-[#A855F7] border border-[#D0E0F0] rounded-md shadow-[0_4px_8px_rgba(0,0,0,0.1)]">
      <CardHeader className="px-6 py-4 border-b border-[#D0E0F0]">
        <div className="flex justify-between items-center w-full">
          <h3 className="text-lg font-bold text-white">Pro Paket</h3>
          {/* <div
            className="bg-white text-[#8B5CF6] px-2 py-1 rounded-full text-sm font-medium">ID: 1</div> */}
        </div>
      </CardHeader>
      <CardContent className="px-6 py-4">
        <div className="flex gap-5 items-center justify-between">
          <div>
            <div className="text-sm text-white">Quantity</div>
            <div
              className="text-3xl font-medium text-white drop-shadow-[0_2px_2px_rgba(0,0,0,0.2)] animate-pulse">
              {quantity}
            </div>
          </div>
        </div>
      </CardContent>
      <CardFooter
        className="px-6 py-3 border-t border-[#D0E0F0] text-sm italic text-white underline-offset-2 underline-[#D0E0F0]">
        <div className="flex justify-between items-center w-full">
          <div>
            <span>Oluşturulma: </span>
            <span>{formattedCreatedDate}</span>
          </div>
          <div>
            <span>Son Kullanım: </span>
            <span>{formattedExpiryDate}</span>
          </div>
        </div>
      </CardFooter>
    </Card>)
  );
}
