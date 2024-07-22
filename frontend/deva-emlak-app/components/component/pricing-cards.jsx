"use client"
import Link from "next/link"
import { Button } from "@/components/ui/button"
import { toast } from "sonner"
import { useRouter } from "next/router"

export function PricingCards() {

  const purchasePackage = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/v1/orders', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 'userId': 1 }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`HTTP error! status: ${response.status}, response: ${errorText}`);
      }

      // const result = await response.json();
      // console.log(result);

      console.log("New data added, ", response);
      toast("Ödeme işlemi başarılı");
      return response;

    } catch (error) {
      console.error('Error:', error);
      toast('Server Side Error');
      throw error;
    }
  }

  return (
    (<section
      className="min-h-screen w-full py-12 bg-gradient-to-r from-gray-200 to-gray-400 dark:from-zinc-900 dark:to-zinc-700 flex items-start justify-center">
      <div className="container px-4 md:px-6">
        <div className="text-center mb-8">
          <h2 className="text-3xl font-bold">Sizin için doğru fiyatı bulduk!</h2>
          <p className="text-muted-foreground mt-2">Bütçenize ve ihtiyaçlarınıza uygun paketleri inceleyebilirsiniz.</p>
        </div>
        <div className="grid grid-cols-1 gap-6 mt-8 md:grid-cols-4 md:gap-8">
          
        <div></div>

          <div
            className="flex flex-col p-6 bg-zinc-100 shadow-lg rounded-lg dark:bg-zinc-850 justify-between hover:cursor-pointer hover:bg-gradient-to-r hover:from-pink-500 hover:to-purple-500 transition-colors duration-300">
            <div>
              <h3 className="text-2xl font-bold text-center">Temel</h3>
              <div className="mt-4 text-center text-zinc-600 dark:text-zinc-400">
                <span className="text-4xl font-bold">Ücretsiz</span>
              </div>
              <ul className="mt-4 space-y-2">
                <li className="flex items-center">
                  <span className="bg-green-500 rounded-full mr-2 p-1">
                    <CheckIcon className="text-white text-xs" />
                  </span>
                  3 Adet İlan Yayınlama Hakkı
                </li>
                <li className="flex items-center">
                  <span className="bg-green-500 rounded-full mr-2 p-1">
                    <CheckIcon className="text-white text-xs" />
                  </span>
                  Temel Özellikler
                </li>
              </ul>
            </div>
            <div className="mt-6">
                <Button className="w-full bg-slate-300" variant="outline" >Aktif</Button>
            </div>
          </div>
          <div
            className="relative flex flex-col p-6 bg-zinc-100 shadow-lg rounded-lg dark:bg-zinc-850 justify-between border-4 border-purple-500 hover:cursor-pointer hover:bg-gradient-to-r hover:from-pink-500 hover:to-purple-500 transition-colors duration-300">
            <div
              className="px-3 py-1 text-sm text-white bg-gradient-to-r from-pink-500 to-purple-500 rounded-full inline-block absolute top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
              Popüler
            </div>
            <div>
              <h3 className="text-2xl font-bold text-center">Pro</h3>
              <div className="mt-4 text-center text-zinc-600 dark:text-zinc-400">
                <span className="text-4xl font-bold">100 ₺</span> / ay
              </div>
              <ul className="mt-4 space-y-2">
                <li className="flex items-center">
                  <span className="bg-green-500 rounded-full mr-2 p-1">
                    <CheckIcon className="text-white text-xs" />
                  </span>
                  Avantajlı Fiyat
                </li>
                <li className="flex items-center">
                  <span className="bg-green-500 rounded-full mr-2 p-1">
                    <CheckIcon className="text-white text-xs" />
                  </span>
                  10 Adet İlan Yayınlama Hakkı
                </li>
                <li className="flex items-center">
                  <span className="bg-green-500 rounded-full mr-2 p-1">
                    <CheckIcon className="text-white text-xs" />
                  </span>
                  Premium Özellikler...
                </li>
                <li className="flex items-center">
                  <span className="bg-green-500 rounded-full mr-2 p-1">
                    <CheckIcon className="text-white text-xs" />
                  </span>
                  İşbirliği Araçları
                </li>
              </ul>
            </div>
            <div className="mt-6">
              <Link href="/" className="w-full" prefetch={false}
              onClick={() => purchasePackage()}>
                <Button className="w-full">Satın al</Button>
              </Link>
            </div>
          </div>

        <div></div>

        </div>
      </div>
    </section>)
  );
}

function CheckIcon(props) {
  return (
    (<svg
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
      <path d="M20 6 9 17l-5-5" />
    </svg>)
  );
}


function XIcon(props) {
  return (
    (<svg
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
    </svg>)
  );
}
