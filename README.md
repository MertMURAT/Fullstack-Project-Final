
<div align="center"><h1> :sparkles: DEVA EMLAK :sparkles: </h1> </div>
<div align="center"><h2> 🚥 FULL STACK REAL ESTATE PROJECT 🚥 </h2> </div>


## Technology stacks

## BACK-END

 - [x] Java <img src="https://img.icons8.com/color/48/000000/java-coffee-cup-logo.png" width="40" height="40"/>
 - [x] Spring Boot <img src="https://img.icons8.com/color/48/000000/spring-logo.png" width="40" height="40"/>



##
 **Dependencies**
 
 - Spring Web
 - Spring Data Jpa
 - Data MongoDB
 - Spring for Rabbitmq
 - Lombok
 - Devtools
 - Eureka Server
 - Eureka Discovery Client
 - Gateway
 ##
 ### Databases

 - [X] PostgreSQL <img src="https://img.icons8.com/color/48/000000/postgreesql.png" width="40" height="40"/>
 - [x] MongoDB (Logging) <img src="https://img.icons8.com/color/48/000000/mongodb.png" width="40" height="40"/>
 
### Tools

 - [x] Maven <img src="https://icons-for-free.com/iff/png/256/vscode+icons+type+maven-1324451386617447973.png" width="40" height="40"/> 
 - [x] Postman <img src="https://cdn.iconscout.com/icon/free/png-512/free-postman-3521648-2945092.png?f=webp&w=256" width="40" height="40"/>


## `File Structure` 

### ` 🏗️ N-Layered Architecture`

## 'API Usage`

### `Advertisement Endpoints`
| Route                  | HTTP      | Description 	                                                                                                      |
|-------------------------|----------|--------------------------------------------------------------------------------------------------------------------|
| /advertisements         | `POST`   | Save advertisement (İlanı kaydeder)                                                                                |
| /advertisements         | `GET`    | Get all advertisements (Tüm ilanları getirir)                                                                      |
| /advertisements/{id}    | `GET`    | Returns advertisement based on id (Belirtilen id'ye göre ilanı döner)                                              |
| /advertisements/latest  | `GET`    | Returns active ads in order, starting from the last added one (En son eklenenden başlayarak aktif ilanları döner)  |

### `Sale Advertisement Endpoints`
| Route                  | HTTP     | Description 	                                                                                                              |
|----------------------|------------|-----------------------------------------------------------------------------------------------------------------------------|
| /sale-ads            |   `POST`   | Save sale advertisement (Satılık ilanını kaydeder)                                                                          |
| /sale-ads            |   `GET`    | Get all sale advertisements (Tüm satılık ilanlarını getirir)                                                                |
| /sale-ads/{id}       |   `GET`    | Returns sale advertisement based on id (Belirtilen id'ye göre satılık ilanını döner)                                        |
| /sale-ads/user/{id}  |   `GET`    | 	Returns active ads in order, starting from the last added one (Belirtilen kullanıcı id'sine göre satılık ilanları döner)  |
| /sale-ads/{id}       |   `PUT`    | Updates sale advertisement based on id (Belirtilen id'ye göre satış ilanını günceller)                                      |
| /sale-ads            |   `PUT`    | Updates sale advertisement status (Satış ilanının durumunu günceller)                                                       |

### `Rental Advertisement Endpoints`
| Route                  | HTTP        | Description 	                                                                                                                                          |
|------------------------|-------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| /rental-ads            |   `POST`    | Save rental advertisement (Kiralık ilanını kaydeder)                                                                                                   |
| /rental-ads            |   `GET`     | Get all rental advertisements (Tüm kiralık ilanlarını getirir)                                                                                         |
| /rental-ads/{id}       |   `GET`     | Returns rental advertisement based on id (Belirtilen id'ye göre kiralık ilanını döner)                                                                 |
| /rental-ads/user/{id}  |   `GET`     | Returns active ads in order, starting from the last added one (Belirtilen kullanıcı id'sine göre kiralık ilanları döner)                               |
| /rental-ads/{id}       |   `PUT`     | Updates rental advertisement based on id (Belirtilen id'ye göre kiralık ilanını günceller)                                                             |
| /rental-ads            |   `PUT`     | 	Updates rental advertisement (Kiralık ilanının durumunu günceller)                                                                                    |

### `Attachment Endpoints`
| Route                            | HTTP       | Description 	                                                                                                       |
|----------------------------------|------------|----------------------------------------------------------------------------------------------------------------------|
| /attachments/upload/{id}         |   `POST`   | Upload a file for a specific advertisement (Belirli bir ilan için dosya yükler)                                      |
| /attachments/advertisement/{id}  |   `GET`    | Get attachments for a specific advertisement based on id (Belirtilen id'ye göre bir ilan için ek dosyaları getirir)  |
| /attachments                     |   `GET`    | Get all attachments (Tüm ek dosyaları getirir)                                                                       |
| /attachments/download/{fileId}   |   `GET`    | Download a specific attachment file based on fileId (Belirtilen fileId'ye göre belirli bir ek dosyayı indirir)       |

## `Order Endpoints`

| Route              | HTTP   | Description                                                   |
|--------------------|--------|---------------------------------------------------------------|
| /api/v1/orders     | `POST` | Save an order (Sipariş kaydet)                                |
| /api/v1/orders     | `GET`  | Get all orders (Tüm siparişleri getir)                        |
| /api/v1/orders/{id}| `GET`  | Get an order by id (Belirtilen id'ye göre sipariş getir)      |

## `Ad Package Endpoints`

| Route                         | HTTP   | Description                                                        |
|-------------------------------|--------|--------------------------------------------------------------------|
| /api/v1/ad-packages           | `PUT`  | Decrease the quantity of the top package by one (İlan paket miktarını bir azalt) |
| /api/v1/ad-packages           | `GET`  | Get all ad packages (Tüm ilan paketlerini getir)                   |
| /api/v1/ad-packages/total-quantity | `GET`  | Get the total quantity of all ad packages (Tüm ilan paketlerinin toplam miktarını getir) |

## `User Endpoints`

| Route              | HTTP   | Description                                                   |
|--------------------|--------|---------------------------------------------------------------|
| /api/v1/users      | `POST` | Save a user (Kullanıcı kaydet)                                |
| /api/v1/users      | `GET`  | Get all users (Tüm kullanıcıları getir)                       |
| /api/v1/users/{id} | `GET`  | Get a user by id (Belirtilen id'ye göre kullanıcı getir)      |

      

## FRONT-END

 - [X] JAVASCRIPT <img src="https://www.svgrepo.com/show/303206/javascript-logo.svg" width="40" height="40"/>
 - [X] HTML <img src="https://www.svgrepo.com/show/353884/html-5.svg" width="40" height="40"/>
 - [X] TAILWIND CSS <img src="https://www.svgrepo.com/show/354431/tailwindcss-icon.svg" width="40" height="40"/>
 - [x] React JS <img src="https://img.icons8.com/color/48/000000/react-native.png" width="40" height="40"/>
 - [X] NEXT JS <img src="https://cdn.sanity.io/images/34ent8ly/production/436c0b088c5629d69b965fab38989e03c48222da-824x824.png" width="40" height="40"/>
 - [X] SHADCN UI <img src="https://avatars.githubusercontent.com/u/139895814?s=200&v=4" width="40" height="40"/>
 - [X] LUCIDE UI <img src="https://avatars.githubusercontent.com/u/66879934?s=280&v=4" width="40" height="40"/>
 - [x] Ant Design (UI) <img src="https://gw.alipayobjects.com/zos/rmsportal/KDpgvguMpGfqaHPjicRK.svg" width="40" height="40"/>



## THIRD-PARTY

 - [x] CLERK <img src="https://imgix.cosmicjs.com/9d8bc340-e63d-11ee-b074-b5c8fe3ef189-clerk.webp?w=1200&auto=format" width="40" height="40"/>
 - [X] GOOGLE PLACE API <img src="https://cdn-icons-png.freepik.com/256/16509/16509523.png?semt=ais_hybrid" width="40" height="40"/>



## `Aplication Screenshots` 



## `Build and Run Project`
> Requires **JDK 21** to run the project, **Git** to pull from remote repository, and **Docker** to run docker-compose

Follow the steps to build and run the project:
- Clone the repository from Git
```bash
  https://github.com/MertMURAT/Fullstack-Project-Final.git
```

- Run the project with Docker
```shell
  docker-compose up -d && docker-compose down 
```
After all services are up and running with `Docker`:
- `ApiGatewayServiceApplication(Back-end)` interface can be accessed from port **8080**
- `MongoDB Express` interface can be accessed from port **8081**
- `RabbitMQ` interface can be accessed from port **15672**
- `NEXT JS (front-end)` interface can be accessed from port **3000**

### `Front-end environment local variables`
| Route            | value    | Description 	                     |
|------------------|----------|------------------------------------|
| GOOGLE PLACE API | private  | NEXT_PUBLIC_GOOGLE_PLACE_API_KEY   |
| CLERK            | private  | NEXT_PUBLIC_CLERK_PUBLISHABLE_KEY  |
| CLERK            | private  | CLERK_SECRET_KEY                   |
| CLERK            | /sign-in |NEXT_PUBLIC_CLERK_SIGN_IN_URL       |
| CLERK            | /sign-up |NEXT_PUBLIC_CLERK_SIGN_UP_URL       |
| CLERK            |    /     |NEXT_PUBLIC_CLERK_AFTER_SIGN_IN_URL |
| CLERK            |    /     |NEXT_PUBLIC_CLERK_AFTER_SIGN_UP_URL |



