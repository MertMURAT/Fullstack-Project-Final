
import { Card, CardHeader, CardContent, CardFooter } from "@/components/ui/card"

export function OrderHistoryCards({ orderId, orderAdQuantity, orderPrice, orderDate }) {

  const formattedDate = new Date(orderDate).toLocaleDateString('tr-TR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  });

  return (
    (<Card
      className="bg-gradient-to-b from-[#F0F8FF] to-[#E6F2FF] border border-[#D0E0F0] rounded-md shadow-[0_4px_8px_rgba(0,0,0,0.1)]">
      <CardHeader className="px-6 py-4 border-b border-[#D0E0F0]">
        <div className="flex justify-between items-center w-full">
          <h3 className="text-lg font-bold text-[#0077B6]">Pro İlan Paketi</h3>
          <div
            className="bg-[#0077B6] text-white px-2 py-1 rounded-full text-sm font-medium">{orderId}</div>
        </div>
      </CardHeader>
      <CardContent className="px-6 py-4">
        <div className="flex gap-5 items-center justify-between">
          <div>
            <div className="text-sm text-[#6C7A86]">İlan Yayınlama Hakkı</div>
            <div
              className="text-3xl font-medium text-[#0077B6] drop-shadow-[0_2px_2px_rgba(0,0,0,0.2)]">{orderAdQuantity}</div>
          </div>
          <div>
            <div className="text-sm text-[#6C7A86]">Fiyat</div>
            <div
              className="text-3xl font-medium text-[#0077B6] drop-shadow-[0_2px_2px_rgba(0,0,0,0.2)]">{orderPrice} ₺</div>
          </div>
        </div>
      </CardContent>
      <CardFooter
        className="px-6 py-3 border-t border-[#D0E0F0] text-sm italic text-[#6C7A86] underline-offset-2 underline-[#D0E0F0]">
        {formattedDate} tarihinde Satın alındı
      </CardFooter>
    </Card>)
  );
}
